package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.init.DMADalekRegistry;
import com.swdteam.common.init.DMDalekRegistry;
import com.swdteam.common.init.DMSpawnerRegistry;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DMSpawnerRegistry.class)
public class SpawnRegistryMixin {
	@Inject(method = "initDalekSpawns()V", at = @At("HEAD"), remap = false)
	private static void initDalekSpawns(CallbackInfo ci) {
		DMADalekRegistry.addSpawn(DMADalekRegistry.DALEK_SANTA, Biomes.MOUNTAINS, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.SNOWY_TUNDRA,
			Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_BEACH);
		DMADalekRegistry.addSpawn(DMADalekRegistry.CANDYCANE, new RegistryKey[]{Biomes.MOUNTAINS, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA_MOUNTAINS});
	}
}
