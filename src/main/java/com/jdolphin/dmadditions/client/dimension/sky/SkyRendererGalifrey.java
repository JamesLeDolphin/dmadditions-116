package com.jdolphin.dmadditions.client.dimension.sky;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.client.ISkyRenderHandler;

public class SkyRendererGalifrey implements ISkyRenderHandler {

	public static SkyRendererGalifrey INSTANCE = new SkyRendererGalifrey();

	@Override
	public void render(int ticks, float partialTicks, MatrixStack stack, ClientWorld world, Minecraft mc) {
		// Check if the player is in the custom dimension
		Minecraft minecraft = Minecraft.getInstance();

		// Obtain WorldRenderer instance
		WorldRenderer worldRenderer = minecraft.levelRenderer;

		// Original rendering code for sky
		RenderSystem.disableAlphaTest();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		worldRenderer.renderSky(stack, partialTicks); // Call renderSky method
		RenderSystem.depthMask(true);
		RenderSystem.enableAlphaTest();
	}
}
