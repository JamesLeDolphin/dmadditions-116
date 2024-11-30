package com.jdolphin.dmadditions.mixin.common;

import com.jdolphin.dmadditions.init.DMADaleks;
import com.swdteam.common.entity.dalek.IDalek;
import com.swdteam.common.init.DMDalekRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(value = DMDalekRegistry.class, remap = false)
abstract class DalekRegistryMixin {
	@Shadow
	private static List<String> dalekList;

	@Shadow
	private static Map<String, IDalek> daleks;

	@Inject(method = "init()V", at = @At("TAIL"), remap = false)
	private static void init(CallbackInfo ci) {
		DMADaleks.init(dalekList, daleks);
	}
}
