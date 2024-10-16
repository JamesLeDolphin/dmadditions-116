package com.jdolphin.dmadditions.mixin.other;

import com.jdolphin.dmadditions.config.DMACommonConfig;
import com.jdolphin.dmadditions.init.DMASpawnerRegistry;
import com.swdteam.common.init.DMSpawnerRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

import static com.swdteam.common.init.DMSpawnerRegistry.spawns;


@Mixin(DMSpawnerRegistry.class)
public class SpawnRegistryMixin {
	@Inject(method = "initDalekSpawns()V", at = @At("HEAD"), remap = false)
	private static void initDalekSpawns(CallbackInfo ci) {
		DMASpawnerRegistry.initDalekSpawns();
	}

	@Inject(method = "addSpawnToAllBiomes(Lnet/minecraft/entity/EntityType;IIILnet/minecraft/entity/EntityClassification;)V",
		at = @At("HEAD"), remap = false, cancellable = true)
	private static void addSpawnToAllBiomes(EntityType<?> type, int weight, int min, int max, EntityClassification entityType, CallbackInfo ci) {
		if (!DMACommonConfig.disable_spawns.get()) {

			for (Map.Entry<RegistryKey<Biome>, Biome> registryKeyBiomeEntry : ForgeRegistries.BIOMES.getEntries()) {
				addSpawn(registryKeyBiomeEntry.getKey(), type, weight, min, max, entityType, ci);
			}
		}
	}

	@Inject(method = "addSpawn(Lnet/minecraft/util/RegistryKey;Lnet/minecraft/entity/EntityType;IIILnet/minecraft/entity/EntityClassification;)V",
		at = @At("HEAD"), remap = false)
	private static void addSpawn(RegistryKey<Biome> biome, EntityType<?> type, int weight, int min, int max, EntityClassification entityType, CallbackInfo ci) {
		if (!DMACommonConfig.disable_spawns.get()) {

			if (!spawns.containsKey(biome.location())) {
				spawns.put(biome.location(), new DMSpawnerRegistry.SpawnInfo());
			}

			DMSpawnerRegistry.SpawnInfo info = spawns.get(biome.location());
			info.addSpawn(type, weight, min, max, entityType);
		}
	}
}