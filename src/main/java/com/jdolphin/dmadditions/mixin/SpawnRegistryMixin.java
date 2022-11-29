package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.init.DMASpawnerRegistry;
import com.swdteam.common.init.DMSpawnerRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DMSpawnerRegistry.class)
public class SpawnRegistryMixin {
	@Inject(method = "initDalekSpawns()V", at = @At("HEAD"), remap = false)
	private static void initDalekSpawns(CallbackInfo ci) {
		DMASpawnerRegistry.initDalekSpawns();
	}
}
