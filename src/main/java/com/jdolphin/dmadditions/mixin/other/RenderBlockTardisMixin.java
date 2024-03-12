package com.jdolphin.dmadditions.mixin.other;

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
import com.swdteam.model.javajson.JSONModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.swdteam.client.render.tileentity.RenderBlockTardis.MODEL_TARDIS;

@Mixin(RenderBlockTardis.class)
public abstract class RenderBlockTardisMixin {

	@Shadow(remap = false)
	private void renderTardis(IVertexBuilder ivertexbuilder, Tardis tardisData, TardisData data, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, TardisTileEntity tardis, float partialTicks, int combinedLightIn, int combinedOverlayIn, float tardisDematPulse) {
	}

	@Unique
	private static ResourceLocation FORCEFIELD = Helper.createAdditionsRL("textures/forcefield.png");

	@Unique private static CubeModel cube;

	@Inject(at = @At("HEAD"), method = "render(Lcom/swdteam/common/tileentity/DMTileEntityBase;FLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;II)V", cancellable = true, remap = false)
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
			if (cube == null) {
				cube = new CubeModel();
			}
			cube.renderToBuffer(matrixStack, iRenderTypeBuffer.getBuffer(RenderType.itemEntityTranslucentCull(FORCEFIELD)),
				combinedLightIn, combinedOverlayIn, 1.0f, 1.0f, 1.0f, 0.5f);
			if (base instanceof TardisTileEntity) {
				if ((double)tardis.pulses > 0.0126415478 && tardis.pulses < 1.0F) {
					ivertexbuilder =
						iRenderTypeBuffer.getBuffer(RenderType.entityTranslucent(JSONModel.ModelInformation.generateAlphaMap(MODEL_TARDIS.getModelData().getTexture())));
					this.renderTardis(ivertexbuilder, tardisData, data, matrixStack, iRenderTypeBuffer, tardis, partialTicks,
						combinedLightIn, combinedOverlayIn, 1.0F);

					if (MODEL_TARDIS.getModelData().getAlphaMap() != null) {
						IVertexBuilder ivertexbuilder3 = iRenderTypeBuffer.getBuffer(RenderType.entityTranslucent(MODEL_TARDIS.getModelData().getAlphaMap()));
						this.renderTardis(ivertexbuilder3, tardisData, data, matrixStack, iRenderTypeBuffer, tardis,
							partialTicks, combinedLightIn, combinedOverlayIn, tardis.pulses);
					}
				}

			}
		}
	}
}
