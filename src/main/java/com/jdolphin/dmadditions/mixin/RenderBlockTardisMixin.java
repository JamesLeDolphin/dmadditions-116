package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.block.tardis.ITardisInvis;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.render.tileentity.RenderBlockTardis;
import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderBlockTardis.class)
public abstract class RenderBlockTardisMixin {

	@Inject(at = @At("HEAD"), method = "render(Lcom/swdteam/common/tileentity/DMTileEntityBase;FLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;II)V", cancellable = true, remap = false)
	public void render(DMTileEntityBase dmTileEntityBase, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer,
					   int combinedLightIn, int combinedOverlayIn, CallbackInfo info) {
		if (((ITardisInvis) dmTileEntityBase).isInvisible()) {
			info.cancel();
		}
	}

}
