package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.config.DMACommonConfig;
import com.swdteam.common.entity.dalek.IDalek;
import com.swdteam.common.init.DMDalekRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class DMASpawnerRegistry {
	public static Map<ResourceLocation, DMASpawnerRegistry.SpawnInfo> spawns = new HashMap();

	public DMASpawnerRegistry() {
	}

	public static void init() {
			if (DMAEntities.SNOWMAN != null) {
				EntitySpawnPlacementRegistry.register(DMAEntities.SNOWMAN.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.WORLD_SURFACE, MonsterEntity::checkAnyLightMonsterSpawnRules);
				addSpawn(Biomes.SNOWY_TUNDRA, DMAEntities.SNOWMAN.get(), 2, 1, 3, EntityClassification.MONSTER);
				addSpawn(Biomes.SNOWY_MOUNTAINS, DMAEntities.SNOWMAN.get(), 2, 1, 3, EntityClassification.MONSTER);
				addSpawn(Biomes.SNOWY_TAIGA, DMAEntities.SNOWMAN.get(), 2, 1, 3, EntityClassification.MONSTER);
			}

		if (DMAEntities.PILOT_FISH != null) {
			EntitySpawnPlacementRegistry.register(DMAEntities.PILOT_FISH.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.WORLD_SURFACE, MonsterEntity::checkAnyLightMonsterSpawnRules);
			addSpawn(Biomes.DESERT, DMAEntities.PILOT_FISH.get(), 2, 1, 3, EntityClassification.MONSTER);
			addSpawn(Biomes.PLAINS, DMAEntities.PILOT_FISH.get(), 2, 1, 3, EntityClassification.MONSTER);
			addSpawn(Biomes.SNOWY_MOUNTAINS, DMAEntities.PILOT_FISH.get(), 2, 1, 3, EntityClassification.MONSTER);
			addSpawn(Biomes.SNOWY_TAIGA, DMAEntities.PILOT_FISH.get(), 2, 1, 3, EntityClassification.MONSTER);
			addSpawn(Biomes.SNOWY_TUNDRA, DMAEntities.PILOT_FISH.get(), 2, 1, 3, EntityClassification.MONSTER);
			addSpawn(Biomes.TAIGA, DMAEntities.PILOT_FISH.get(), 2, 1, 3, EntityClassification.MONSTER);

			if (DMAEntities.GLASS_DALEK != null) {
				EntitySpawnPlacementRegistry.register(DMAEntities.GLASS_DALEK.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.WORLD_SURFACE, MonsterEntity::checkAnyLightMonsterSpawnRules);
				addSpawn(Biomes.MOUNTAINS, DMAEntities.GLASS_DALEK.get(), 2, 1, 3, EntityClassification.MONSTER);
				addSpawn(Biomes.SNOWY_MOUNTAINS, DMAEntities.GLASS_DALEK.get(), 2, 1, 3, EntityClassification.MONSTER);
				addSpawn(Biomes.SNOWY_TAIGA_MOUNTAINS, DMAEntities.GLASS_DALEK.get(), 2, 1, 3, EntityClassification.MONSTER);
				addSpawn(Biomes.SNOWY_TUNDRA, DMAEntities.GLASS_DALEK.get(), 2, 1, 3, EntityClassification.MONSTER);
				addSpawn(Biomes.SNOWY_TAIGA, DMAEntities.GLASS_DALEK.get(), 2, 1, 3, EntityClassification.MONSTER);
				addSpawn(Biomes.SNOWY_TAIGA_HILLS, DMAEntities.GLASS_DALEK.get(), 2, 1, 3, EntityClassification.MONSTER);
				addSpawn(Biomes.SNOWY_BEACH, DMAEntities.GLASS_DALEK.get(), 2, 1, 3, EntityClassification.MONSTER);
			}
		}

	}

	public static void initDalekSpawns() {
			addDalekSpawn(DMADalekRegistry.DALEK_SANTA, Biomes.MOUNTAINS, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.SNOWY_TUNDRA,
				Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_BEACH);
			addDalekSpawn(DMADalekRegistry.CANDYCANE, Biomes.MOUNTAINS, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA_MOUNTAINS);
			addDalekSpawn(DMADalekRegistry.PFD, Biomes.MOUNTAINS, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA_MOUNTAINS);
			addDalekSpawn(DMADalekRegistry.STORM, Biomes.DARK_FOREST_HILLS, Biomes.DARK_FOREST, Biomes.BADLANDS, Biomes.BADLANDS_PLATEAU, Biomes.ERODED_BADLANDS,
				Biomes.MODIFIED_BADLANDS_PLATEAU, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.WOODED_BADLANDS_PLATEAU);

	}

	@SafeVarargs
	public static void addDalekSpawn(IDalek dalek, RegistryKey<Biome>... biomes) {
		if (dalek == null) return;
		if (!DMACommonConfig.disable_spawns.get()) {
			DMDalekRegistry.addSpawn(dalek, biomes);
		}
	}

	private static void addSpawn(RegistryKey<Biome> biome, EntityType<?> type, int weight, int min, int max, EntityClassification entityType) {
		if (!DMACommonConfig.disable_spawns.get()) {
			if (!spawns.containsKey(biome.location())) {
				spawns.put(biome.location(), new DMASpawnerRegistry.SpawnInfo());
			}

			DMASpawnerRegistry.SpawnInfo info = spawns.get(biome.location());
			info.addSpawn(type, weight, min, max, entityType);
		}
	}

	private static void addSpawnToAllBiomes(EntityType<?> type, int weight, int min, int max, EntityClassification entityType) {
		Iterator var5 = ForgeRegistries.BIOMES.getEntries().iterator();

		while (var5.hasNext()) {
			Map.Entry<RegistryKey<Biome>, Biome> rl = (Map.Entry) var5.next();
			addSpawn(rl.getKey(), type, weight, min, max, entityType);
		}

	}

	private static void removeSpawn(EntityType<?> type, RegistryKey<Biome>... biome) {
		for (int i = 0; i < biome.length; ++i) {
			RegistryKey<Biome> bi = biome[i];
			if (spawns.containsKey(bi.location())) {
				DMASpawnerRegistry.SpawnInfo info = spawns.get(bi.location());
				info.removeSpawn(type);
			}
		}

	}

	public static class SpawnInfo {
		private final List<DMASpawnerRegistry.SpawnInfo.Spawn> spawners = new ArrayList();

		public SpawnInfo() {
		}

		public void addSpawn(EntityType<?> type, int weight, int min, int max, EntityClassification entType) {
			this.spawners.add(new DMASpawnerRegistry.SpawnInfo.Spawn(new MobSpawnInfo.Spawners(type, weight, min, max), entType));
		}

		public void removeSpawn(EntityType<?> type) {
			for (int i = 0; i < this.spawners.size(); ++i) {
				DMASpawnerRegistry.SpawnInfo.Spawn spawn = this.spawners.get(i);
				if (spawn.spawner.type == type) {
					this.spawners.remove(i);
					break;
				}
			}

		}

		public List<DMASpawnerRegistry.SpawnInfo.Spawn> getSpawners() {
			return this.spawners;
		}

		public static class Spawn {
			public MobSpawnInfo.Spawners spawner;
			public EntityClassification entityType;

			public Spawn(MobSpawnInfo.Spawners spawner, EntityClassification type) {
				this.spawner = spawner;
				this.entityType = type;
			}
		}
	}
}
