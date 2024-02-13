package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.block.tardis.ITardisDMAActions;
import com.jdolphin.dmadditions.client.model.CubeModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.render.tileentity.RenderBlockTardis;
import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderBlockTardis.class)
public abstract class RenderBlockTardisMixin {

	@Unique
	private static ResourceLocation FORCEFIELD = new ResourceLocation(DmAdditions.MODID, "textures/forcefield.png");

	@Unique private static CubeModel cube;

	@Inject(at = @At("HEAD"), method = "render(Lcom/swdteam/common/tileentity/DMTileEntityBase;FLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;II)V", cancellable = true, remap = false)
	public void render(DMTileEntityBase base, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer,
					   int combinedLightIn, int combinedOverlayIn, CallbackInfo info) {
		if (((ITardisDMAActions) base).isInvisible()) {
			info.cancel();
		}
		if (((ITardisDMAActions) base).isForcefieldActive()) {
			if (cube == null) {cube = new CubeModel();}
            cube.renderToBuffer(matrixStack, iRenderTypeBuffer.getBuffer(RenderType.itemEntityTranslucentCull(FORCEFIELD)),
                    combinedLightIn, combinedOverlayIn, 1.0f, 1.0f, 1.0f, 0.5f);
        }
	}

}
