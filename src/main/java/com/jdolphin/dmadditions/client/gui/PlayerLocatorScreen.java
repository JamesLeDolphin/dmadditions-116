package com.jdolphin.dmadditions.client.gui;

import com.jdolphin.dmadditions.init.DMAPackets;
import com.jdolphin.dmadditions.network.SBLocatePlayerPacket;
import com.jdolphin.dmadditions.util.GuiHelper;
import com.jdolphin.dmadditions.util.Helper;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;

public class PlayerLocatorScreen extends Screen {
	private TextFieldWidget playerInput;
	public PlayerLocatorScreen() {
		super(new TranslationTextComponent("dmadditions.gui.player_locator"));
	}


	@Override
	protected void init() {
		this.playerInput = new TextFieldWidget(this.font,
			this.width / 2 - 64, this.height / 2 - 64, 128, 24,
			new TranslationTextComponent("chat.editBox"));
		this.addButton(new Button(this.width / 2 - 136, this.height / 2 + 32, 128, 20,
			new TranslationTextComponent("dmadditions.button.player_locator.select"), (button) -> {
			this.setCoords();
			this.onClose();
		}));
		this.addButton(new Button(this.width / 2 + 8, this.height / 2 + 32, 128, 20,
			new TranslationTextComponent("dmadditions.button.cancel"), (button) -> {
			this.onClose();
		}));

		this.playerInput.setMaxLength(256);
		this.playerInput.setBordered(true);

		this.addWidget(this.playerInput);
	}


	@Override
	public void render(MatrixStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
		this.renderBackground(pPoseStack);
		GuiHelper.drawWhiteCenteredString(pPoseStack, new TranslationTextComponent("dmadditions.gui.player_locator"),
			this.width / 2, 30);

		GuiHelper.renderWidgets(pPoseStack, pMouseX, pMouseY, pPartialTick, playerInput);

		Style style = GuiHelper.getStyle(this, pMouseX, pMouseY);
		if (style != null && style.getHoverEvent() != null) {
			this.renderComponentHoverEffect(pPoseStack, style, pMouseX, pMouseY);
		}
		super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
	}

	public void setCoords() {
		@SuppressWarnings("resource")
		SBLocatePlayerPacket packet = new SBLocatePlayerPacket(this.playerInput.getValue(), Helper.vec3ToBlockPos(Minecraft.getInstance().player.position()));
		DMAPackets.INSTANCE.sendToServer(packet);
	}

	@Override
	public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
		switch (pKeyCode) {
			case GLFW.GLFW_KEY_ENTER:
			case GLFW.GLFW_KEY_KP_ENTER:
				if (this.getFocused() instanceof Button)
					return super.keyPressed(pKeyCode, pScanCode, pModifiers);
				this.setCoords();
				this.onClose();
				break;
		}
		return super.keyPressed(pKeyCode, pScanCode, pModifiers);
	}
}
