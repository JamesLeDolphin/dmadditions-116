package com.jdolphin.dmadditions.client.gui.overlay;

import com.jdolphin.dmadditions.init.DMACapabilities;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.client.overlay.Overlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class PreRegenOverlay extends Overlay {

	@OnlyIn(Dist.CLIENT)
	public void render(MatrixStack stack) {
		super.render(stack);
		PlayerEntity player = Minecraft.getInstance().player;
		if (player != null) {
			player.getCapability(DMACapabilities.REGEN_CAPABILITY).ifPresent(cap -> {
				if (cap.isPreRegen()) {
					renderOverlay(stack);
				}
				else return;
			});
		}
	}

	private void renderOverlay(MatrixStack stack) {
		stack.pushPose();
		RenderSystem.enableBlend();
		AbstractGui.fill(stack, 0, 0, screenWidth, screenHeight, new Color(255, 140, 0, 70).getRGB());
		RenderSystem.disableBlend();
		stack.popPose();
	}
}
