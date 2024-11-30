package com.jdolphin.dmadditions.mixin.client;

import com.jdolphin.dmadditions.block.tardis.ITardisDMAActions;
import com.jdolphin.dmadditions.client.model.CubeModel;
import com.jdolphin.dmadditions.util.Helper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.client.render.tileentity.RenderBlockTardis;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tileentity.DMTileEntityBase;
import com.swdteam.common.tileentity.TardisTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.swdteam.client.render.tileentity.RenderBlockTardis.MODEL_TARDIS;
import static com.swdteam.client.render.tileentity.RenderBlockTardis.renderTardis;

@Mixin(RenderBlockTardis.class)
public abstract class RenderBlockTardisMixin {

	@Unique
	private static ResourceLocation FORCEFIELD = Helper.createAdditionsRL("textures/forcefield.png");

	@Unique
	private static CubeModel cube;

	@Inject(at = @At("TAIL"), method = "render(Lcom/swdteam/common/tileentity/DMTileEntityBase;FLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;II)V", cancellable = true, remap = false)
	public void render(DMTileEntityBase base, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer,
					   int combinedLightIn, int combinedOverlayIn, CallbackInfo info) {
		TardisTileEntity tardis = (TardisTileEntity) base;
		TardisData data = ClientTardisCache.getTardisData(tardis.globalID);
		Tardis tardisData = data.getTardisExterior();
		IVertexBuilder ivertexbuilder;
		if (((ITardisDMAActions) base).isInvisible()) {
			info.cancel();
		}
		if (((ITardisDMAActions) base).isForcefieldActive()) {

			if (base instanceof TardisTileEntity) {
				if (cube == null) {
					cube = new CubeModel();
				}
				cube.renderToBuffer(matrixStack, iRenderTypeBuffer.getBuffer(RenderType.entityTranslucentCull(FORCEFIELD)),
					combinedLightIn, combinedOverlayIn, 1.0f, 1.0f, 1.0f, 0.5f);
				ivertexbuilder = iRenderTypeBuffer.getBuffer(RenderType.entityTranslucent(MODEL_TARDIS.getModelData().getTexture()));
				renderTardis(ivertexbuilder, tardisData, data, matrixStack, iRenderTypeBuffer, tardis, partialTicks, combinedLightIn, combinedOverlayIn, tardis.pulses, true);
			}
		}
	}
}
