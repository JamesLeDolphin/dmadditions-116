package com.jdolphin.dmadditions.client.render.tileentity;

import com.jdolphin.dmadditions.client.model.tileentity.ClassicConsoleModel;
import com.jdolphin.dmadditions.common.block.tardis.console.ClassicConsoleBlock;
import com.jdolphin.dmadditions.common.tileentity.console.ClassicConsoleTileEntity;
import com.jdolphin.dmadditions.common.util.Helper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;

public class ClassicConsoleRenderer extends TileEntityRenderer<ClassicConsoleTileEntity> {
	private final ClassicConsoleModel model;
	private final JSONModel screenModel;

	public ClassicConsoleRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
		super(tileEntityRendererDispatcher);
		model = new ClassicConsoleModel();
		screenModel = ModelLoader.loadModel(Helper.createDMRL("models/mesh/plane_16.json"));
	}

	public static ResourceLocation getTextureLocation() {
		return Helper.createAdditionsRL("textures/block/classic_console.png");
	}

	private void renderDimIcon(ClassicConsoleTileEntity tile, MatrixStack stack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		BlockState state = tile.getBlockState();
		Direction direction = state.getValue(ClassicConsoleBlock.FACING);

		ResourceLocation dimImg = tile.getDimIcon();
		if (dimImg == null) {
			dimImg = screenModel.getModelData().getTexture();
		}
		IVertexBuilder vertexBuilder = buffer.getBuffer(RenderType.entityCutout(dimImg));

		stack.pushPose();
		stack.translate(0.5, 1, 0.5);
		stack.mulPose(Vector3f.YN.rotationDegrees(direction.toYRot() + 60));
		stack.translate(0, -0.2, 1);
		stack.scale(0.25f, 0.25f, 0.25f);
		stack.mulPose(Vector3f.XN.rotationDegrees(70));
		stack.translate(-0.73f, 0.25, 0.4);

		screenModel.getModelData().getModel().renderToBuffer(stack, vertexBuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);

		stack.popPose();
	}

	public void renderTimeRotor(ClassicConsoleTileEntity tile, float delta, MatrixStack stack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		BlockPos pos = tile.getBlockPos();
		TardisData data = ClientTardisCache.getTardisData(pos);
		if (data != null) {
			if (data.isInFlight()) {
				stack.pushPose();
				stack.translate(0.5, 1.25, 0.5);

				float f = (tile.getLevel().getGameTime() + delta) % 1000;
				float v = (float) (Math.cos((f / 45) * Math.PI * 2) * 0.2);
				stack.translate(0, v, 0);
				stack.mulPose(Vector3f.YP.rotationDegrees(f));

				model.renderRotor(stack, buffer.getBuffer(model.renderType(getTextureLocation())), combinedLight, combinedOverlay, 1, 1, 1, 1);
				stack.popPose();
			} else {
				stack.pushPose();
				stack.translate(0.5, 1.25, 0.5);
				model.renderRotor(stack, buffer.getBuffer(model.renderType(getTextureLocation())), combinedLight, combinedOverlay, 1, 1, 1, 1);
				stack.popPose();
			}
		}
	}

	@Override
	public void render(ClassicConsoleTileEntity tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		BlockState state = tile.getBlockState();
		Direction direction = state.getValue(ClassicConsoleBlock.FACING);

		renderDimIcon(tile, stack, buffer, combinedLight, combinedOverlay);
		renderTimeRotor(tile, partialTicks, stack, buffer, combinedLight, combinedOverlay);
		stack.pushPose();

		stack.mulPose(Vector3f.ZN.rotationDegrees(180));
		stack.translate(-0.5, -1.51, 0.5);
		stack.mulPose(Vector3f.YP.rotationDegrees(direction.toYRot()));
		model.renderToBuffer(stack, buffer.getBuffer(model.renderType(getTextureLocation())), combinedLight, combinedOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
		stack.popPose();
	}
}
