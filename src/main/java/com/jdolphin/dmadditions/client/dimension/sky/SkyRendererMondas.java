package com.jdolphin.dmadditions.client.dimension.sky;

import com.jdolphin.dmadditions.init.DMADimensions;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.client.IRenderHandler;

public class SkyRendererMondas implements IRenderHandler {

	public static SkyRendererMondas INSTANCE = new SkyRendererMondas();
	public void render(float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, int overlay) {
		// Check if the player is in the custom dimension
		Minecraft minecraft = Minecraft.getInstance();
		ClientWorld world = minecraft.level;
		if (world != null && world.dimension().location().equals(DMADimensions.MONDAS)) {
			return; // Skip rendering clouds
		}

		// Obtain WorldRenderer instance
		WorldRenderer worldRenderer = minecraft.levelRenderer;

		// Original rendering code for sky
		RenderSystem.disableAlphaTest();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		worldRenderer.renderSky(matrixStack, partialTicks); // Call renderSky method
		RenderSystem.depthMask(true);
		RenderSystem.enableAlphaTest();
	}

	@Override
	public void render(int i, float v, ClientWorld clientWorld, Minecraft minecraft) {
		// Unused method, leave it empty
	}
}
