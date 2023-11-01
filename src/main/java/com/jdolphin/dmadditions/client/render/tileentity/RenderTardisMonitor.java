package com.jdolphin.dmadditions.client.render.tileentity;

import com.jdolphin.dmadditions.block.tardis.BetterScannerBlock;
import com.jdolphin.dmadditions.tileentity.BetterScannerTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.client.render.ScannerPages;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.main.DalekMod;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;

public class RenderTardisMonitor extends TileEntityRenderer<BetterScannerTileEntity> implements IModelPartReloader {
	public static JSONModel MODEL_SCANNER;
	public RenderTardisMonitor(TileEntityRendererDispatcher p_i226006_1_) {
		super(p_i226006_1_);
		ModelReloaderRegistry.register(this);
	}
	@Override
	public JSONModel getModel() {
		return MODEL_SCANNER;
	}

	public void render(BetterScannerTileEntity te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
		JSONModel model = getModel();
		BlockState blockState = te.getBlockState();
		BetterScannerBlock block = ((BetterScannerBlock) blockState.getBlock());
		Vector3d screenTranslate = block.getScreenTranslate();
		Vector3f screenRotate = block.getScreenRotate();
		Vector3f screenScale = block.getScreenScale();

		Direction direction = blockState.getValue(HorizontalBlock.FACING);

		if (model != null) {
			matrixStack.pushPose();
			matrixStack.translate(0.5D, 1.5D, 0.5D);

			matrixStack.mulPose(new Quaternion(0.0F, 0.0F, 180.0F, true));

			matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F + direction.toYRot()));


			BlockPos lightpos = te.getBlockPos().offset(0, 1, 0);
			int light = LightTexture.pack(te.getLevel().getBrightness(LightType.BLOCK, lightpos), te.getLevel().getBrightness(LightType.SKY, lightpos));

			model.getModelData().getModel().renderToBuffer(matrixStack, iRenderTypeBuffer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			matrixStack.translate(0.0D, 0.6000000238418579D, -0.5099999904632568D);

			matrixStack.translate(screenTranslate.x, screenTranslate.y, screenTranslate.z);
			matrixStack.scale(screenScale.x(), screenScale.y(), screenScale.z());
			matrixStack.mulPose(Vector3f.XP.rotationDegrees(screenRotate.x()));
			matrixStack.mulPose(Vector3f.YP.rotationDegrees(screenRotate.y()));
			matrixStack.mulPose(Vector3f.ZP.rotationDegrees(screenRotate.z()));


			matrixStack.pushPose();
			FontRenderer font = this.renderer.getFont();
			matrixStack.scale(0.0065F, 0.0065F, 0.0065F);
			te.renderCallUpdate();
			if (te.getTardisData() != null) {
				TardisData data = te.getTardisData();
				ScannerPages.PAGES[te.getScreen()].render(matrixStack, font, data);
			}

			matrixStack.popPose();
			matrixStack.popPose();
		}

	}
	//TODO: Fix this thingymabob
	public void init() {
			MODEL_SCANNER = ModelLoader.loadModel(new ResourceLocation(DalekMod.MODID,
				String.format("models/tileentity/%s.json"/*, registryName.getPath()*/)));
	}
}