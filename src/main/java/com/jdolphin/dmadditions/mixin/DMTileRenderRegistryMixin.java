package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.client.init.DMATileRenderRegistry;
import com.swdteam.client.init.DMTileEntityRenderRegistry;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(DMTileEntityRenderRegistry.class)
public class DMTileRenderRegistryMixin {
	@Inject(method = "registerModel(Lnet/minecraft/tileentity/TileEntityType;Ljava/util/function/Function;)V", at = @At("HEAD"), cancellable = true, remap = false)
	private static <T extends TileEntity> void registerModel(TileEntityType<T> tileEntityType, Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<? super T>> rendererFactory, CallbackInfo ci) {
		if (DMATileRenderRegistry.MIXIN_RENDERERS.contains(tileEntityType)) {
			LogManager.getLogger(DmAdditions.MODID)
				.info(String.format("Cancelling tile entity renderer: %s", tileEntityType.getRegistryName()));

			ci.cancel();
		}
	}
}
