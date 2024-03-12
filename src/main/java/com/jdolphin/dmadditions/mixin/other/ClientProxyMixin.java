package com.jdolphin.dmadditions.mixin.other;

import com.jdolphin.dmadditions.client.init.DMATileRenderRegistry;
import com.swdteam.main.proxy.ClientProxy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientProxy.class)
public class ClientProxyMixin {
	@Inject(method = "registerReloadable", at = @At("HEAD"), remap = false)
	private static void registerReloadable(CallbackInfo ci){
		DMATileRenderRegistry.init();
	}
}
