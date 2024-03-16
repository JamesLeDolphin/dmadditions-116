package com.jdolphin.dmadditions.client.render.tileentity;

import com.jdolphin.dmadditions.tileentity.ConsoleTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class ConsoleRenderer extends TileEntityRenderer<ConsoleTileEntity> {
	public ConsoleRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
		super(tileEntityRendererDispatcher);
	}

	@Override
	public void render(ConsoleTileEntity tile, float v, MatrixStack stack, IRenderTypeBuffer buffer, int i, int j) {

	}
}
