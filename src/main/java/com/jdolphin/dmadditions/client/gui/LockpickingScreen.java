package com.jdolphin.dmadditions.client.gui;

import com.jdolphin.dmadditions.common.init.DMAPackets;
import com.jdolphin.dmadditions.common.network.SBLockpickPacket;
import com.jdolphin.dmadditions.common.util.Helper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;

public class LockpickingScreen extends Screen {
	private static final ResourceLocation LOCKPICK = Helper.createAdditionsRL("textures/gui/lockpick.png");
	private static final ResourceLocation LOCK = Helper.createAdditionsRL("textures/gui/lock.png");

	private float pinAngle = 90f;
	private final int correctAngle = (int) (Math.random() * 180);

	private float lockRotation = 0f;
	private int breakingTimer = 0;
	private boolean applyingTorque = false;
	private boolean unlocked = false;

	private float shakeOffsetX = 0f;
	private float shakeOffsetY = 0f;

	private final BlockPos pos;
	private int ticker = Helper.seconds(2);

	public LockpickingScreen(BlockPos pos) {
		super(new TranslationTextComponent("dmadditions.gui.lockpicking"));
		this.pos = pos;
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	public void tick() {
		super.tick();
		if (ticker == 0) this.onClose();

		if (unlocked) ticker--;

		if (applyingTorque && !unlocked) {
			int maxRot = 90;
			float angleDiff = Math.abs(pinAngle - correctAngle);
			float accuracy = Math.min(angleDiff / maxRot, 1);
			float maxRotationLimit = maxRot - accuracy * 100;

			if (lockRotation < maxRotationLimit) {
				lockRotation += 5f;
				breakingTimer = 0;
				resetShake();
			} else {
				breakingTimer++;

				shakeOffsetX = (float) ((Math.random() - 0.5) * 4);
				shakeOffsetY = (float) ((Math.random() - 0.5) * 4);

				if (breakingTimer > 20 + (accuracy * 60)) {
					breakPick();
				}
			}

			if (angleDiff < 5.0f && lockRotation >= 85f) {
				unlock();
			}

		} else {
			breakingTimer = 0;
			resetShake();

			if (lockRotation > 0f && !unlocked) {
				lockRotation -= 2f;
			}
		}
	}

	private void resetShake() {
		shakeOffsetX = 0f;
		shakeOffsetY = 0f;
	}

	private void unlock() {
		unlocked = true;
		SBLockpickPacket packet = new SBLockpickPacket(true, this.pos);
		DMAPackets.send(packet);
	}

	private void breakPick() {
		SBLockpickPacket packet = new SBLockpickPacket(false, this.pos);
		DMAPackets.send(packet);
		this.onClose();
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		if (unlocked) {
			drawCenteredString(matrixStack, this.font, "Unlocked!", this.width / 2, 80, 0x00FF00);
		}
		int centerX = this.width / 2 + (int) shakeOffsetX;
		int centerY = this.height / 2 + (int) shakeOffsetY;

		renderTexture(matrixStack, centerX, centerY, lockRotation, 72, LOCK, true);
		renderTexture(matrixStack, centerX, centerY, pinAngle + 225, 32, LOCKPICK, false);

		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
			pinAngle += (float) (dragX * 0.5f);
			pinAngle = Math.max(0, Math.min(180, pinAngle));
			return true;
		}
		return false;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_2 && !unlocked) {
			applyingTorque = true;
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_2) {
			applyingTorque = false;
		}
		return super.mouseReleased(mouseX, mouseY, button);
	}

	private void renderTexture(MatrixStack matrixStack, int centerX, int centerY, float angleDeg, int size, ResourceLocation texture, boolean center) {
		Minecraft.getInstance().getTextureManager().bind(texture);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		matrixStack.pushPose();

		matrixStack.translate(centerX, centerY, 0);
		matrixStack.mulPose(Vector3f.ZP.rotationDegrees(angleDeg));
		if (center) {
			matrixStack.translate(-size / 2f, -size / 2f, 0);
		}
		// Draw the image
		blit(matrixStack, 0, 0, 0, 0, size, size, size, size);

		matrixStack.popPose();
		RenderSystem.disableBlend();
	}
}

