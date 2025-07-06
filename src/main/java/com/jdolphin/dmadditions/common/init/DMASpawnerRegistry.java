package com.jdolphin.dmadditions.common.init;

import com.jdolphin.dmadditions.common.config.DMACommonConfig;
import com.swdteam.common.entity.dalek.IDalek;
import com.swdteam.common.init.DMBiomes;
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
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DMASpawnerRegistry {
	public static Map<ResourceLocation, DMASpawnerRegistry.SpawnInfo> spawns = new HashMap<>();

	public DMASpawnerRegistry() {
	}

	public static void init() {
		EntitySpawnPlacementRegistry.register(DMAEntities.SNOWMAN.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.WORLD_SURFACE, MonsterEntity::checkAnyLightMonsterSpawnRules);
		addSpawn(Biomes.SNOWY_TUNDRA, DMAEntities.SNOWMAN.get(), 2, 1, 3, EntityClassification.MONSTER);
		addSpawn(Biomes.SNOWY_MOUNTAINS, DMAEntities.SNOWMAN.get(), 2, 1, 3, EntityClassification.MONSTER);
		addSpawn(Biomes.SNOWY_TAIGA, DMAEntities.SNOWMAN.get(), 2, 1, 3, EntityClassification.MONSTER);

		EntitySpawnPlacementRegistry.register(DMAEntities.KANTROFARRI.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMobSpawnRules);
		addSpawn(Biomes.SNOWY_TUNDRA, DMAEntities.KANTROFARRI.get(), 2, 3, 5, EntityClassification.MONSTER);

		EntitySpawnPlacementRegistry.register(DMAEntities.HEROBRINE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkAnyLightMonsterSpawnRules);
		addSpawn(DMBiomes.CLASSIC, DMAEntities.HEROBRINE.get(), 1, 1, 1, EntityClassification.CREATURE);

		addSpawn(Biomes.SNOWY_TUNDRA, DMAEntities.ICE_WARRIOR.get(), 2, 1, 2, EntityClassification.MONSTER);

		addSpawn(Biomes.PLAINS, DMAEntities.ZYGON.get(), 2, 1, 2, EntityClassification.MONSTER);
	}

	public static void initDalekSpawns() {
		addDalekSpawn(DMADaleks.DALEK_SANTA, Biomes.MOUNTAINS, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.SNOWY_TUNDRA,
			Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_BEACH);
		addDalekSpawn(DMADaleks.CANDYCANE, Biomes.MOUNTAINS, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA_MOUNTAINS);
		addDalekSpawn(DMADaleks.STORM, Biomes.DARK_FOREST_HILLS, Biomes.DARK_FOREST, Biomes.BADLANDS, Biomes.BADLANDS_PLATEAU, Biomes.ERODED_BADLANDS,
			Biomes.MODIFIED_BADLANDS_PLATEAU, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.WOODED_BADLANDS_PLATEAU);
		addDalekSpawn(DMADaleks.GLASS, Biomes.MOUNTAINS, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.SNOWY_TUNDRA,
			Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_BEACH);

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

	@SuppressWarnings("unused")
	private static void addSpawnToAllBiomes(EntityType<?> type, int weight, int min, int max, EntityClassification entityType) {
		for (Entry<RegistryKey<Biome>, Biome> rl : ForgeRegistries.BIOMES.getEntries()) {
			if (rl.getKey() == Biomes.THE_VOID) continue;
			addSpawn(rl.getKey(), type, weight, min, max, entityType);
		}

	}

	@SuppressWarnings("unused")
	@SafeVarargs
	private static void removeSpawn(EntityType<?> type, RegistryKey<Biome>... biome) {
		for (RegistryKey<Biome> bi : biome) {
			if (spawns.containsKey(bi.location())) {
				SpawnInfo info = spawns.get(bi.location());
				info.removeSpawn(type);
			}
		}

	}

	public static class SpawnInfo {
		private final List<DMASpawnerRegistry.SpawnInfo.Spawn> spawners = new ArrayList<>();

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
