package com.jdolphin.dmadditions.client.render.tileentity;

import com.jdolphin.dmadditions.client.model.tileentity.ConsoleModel;
import com.jdolphin.dmadditions.tileentity.ConsoleTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;

public class ConsoleRenderer extends TileEntityRenderer<ConsoleTileEntity> {
	private static ConsoleModel model;
	public ConsoleRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
		super(tileEntityRendererDispatcher);
		model = new ConsoleModel();
	}

	@Override
	public void render(ConsoleTileEntity tile, float v, MatrixStack stack, IRenderTypeBuffer buffer, int i, int j) {
		stack.pushPose();
		stack.mulPose(new Quaternion(0, 1, 0, 1));

		stack.popPose();

		//model.renderToBuffer(stack, buffer.getBuffer(model.renderType(Helper.createAdditionsRL("textures/block/console.png"))), i, j, 1.0f, 1.0f, 1.0f, 1.0f);
	}
}
