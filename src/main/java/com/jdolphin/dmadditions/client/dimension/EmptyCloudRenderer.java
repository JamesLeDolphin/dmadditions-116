package com.jdolphin.dmadditions.client.dimension;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.client.ICloudRenderHandler;

public class EmptyCloudRenderer implements ICloudRenderHandler {
	public static EmptyCloudRenderer INSTANCE = new EmptyCloudRenderer();
	@Override
	public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc, double viewEntityX, double viewEntityY, double viewEntityZ) {

	}
}
