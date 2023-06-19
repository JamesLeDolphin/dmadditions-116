package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.client.init.DMAEntityRenderRegistry;
import com.swdteam.client.init.DMEntityRenderRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DMEntityRenderRegistry.class)
public class DMEntityRenderRegistryMixin {
	@Inject(method = "registryEntityRenders", at = @At("TAIL"), remap = false)
	private static void registerEntityRenders(CallbackInfo ci){
		DMAEntityRenderRegistry.registryEntityRenders();
	}
}
