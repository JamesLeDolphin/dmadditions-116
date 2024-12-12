package com.jdolphin.dmadditions.client.render.tileentity;


import com.jdolphin.dmadditions.util.Helper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.common.block.tardis.DimensionSelectorPanelBlock;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.tileentity.tardis.DimensionSelectorTileEntity;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class DimensionSelectorPanelRenderer extends TileEntityRenderer<DimensionSelectorTileEntity> implements IModelPartReloader {
	public static JSONModel SCREEN_MODEL;

	public DimensionSelectorPanelRenderer(TileEntityRendererDispatcher dispatcher) {
		super(dispatcher);
		ModelReloaderRegistry.register(this);
	}

	@Override
	public JSONModel getModel() {
		return SCREEN_MODEL;
	}

	@SuppressWarnings("deprecation")
	public void render(DimensionSelectorTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
		if (SCREEN_MODEL != null && tileEntity.getLevel().dimension().equals(DMDimensions.TARDIS)) {
			ResourceLocation rl = tileEntity.getTexturePath();
			if (rl == null) {
				rl = SCREEN_MODEL.getModelData().getTexture();
			}

			float rotation = tileEntity.getBlockState().getValue(DimensionSelectorPanelBlock.FACING).toYRot();
			AttachFace face = tileEntity.getBlockState().getValue(BlockStateProperties.ATTACH_FACE);

			IVertexBuilder vertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.entityCutout(rl));
			matrixStack.pushPose();

			matrixStack.translate(0.5D, 0.0D, 0.5D);
			matrixStack.mulPose(Vector3f.YN.rotationDegrees(rotation));
			matrixStack.translate(0.0D, 0.12600000202655792D, -0.3100000023841858D);
			matrixStack.scale(0.4F, 0.5F, 0.4F);

			switch (face) {
				case CEILING:
					matrixStack.translate(0, 1.49, 0);

				case FLOOR:
				default:
					matrixStack.mulPose(Vector3f.XN.rotationDegrees(90.0F));
					matrixStack.mulPose(Vector3f.ZN.rotationDegrees(180.0F));
					break;

				case WALL:
					matrixStack.mulPose(Vector3f.ZN.rotationDegrees(180.0F));
					matrixStack.translate(0, -1.55d, -0.127);
					break;
			}

			SCREEN_MODEL.getModelData().getModel().renderToBuffer(matrixStack, vertexBuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
			matrixStack.popPose();
		}

	}

	public void init() {
		SCREEN_MODEL = ModelLoader.loadModel(Helper.createDMRL("models/mesh/plane_16.json"));
	}
}
