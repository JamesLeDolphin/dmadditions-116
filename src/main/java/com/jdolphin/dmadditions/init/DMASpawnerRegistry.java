package com.jdolphin.dmadditions.init;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
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
	}

	public static void initDalekSpawns() {
		DMADalekRegistry.addSpawn(DMADalekRegistry.DALEK_SANTA, Biomes.MOUNTAINS, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.SNOWY_TUNDRA,
			Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_BEACH);
		DMADalekRegistry.addSpawn(DMADalekRegistry.CANDYCANE, new RegistryKey[]{Biomes.MOUNTAINS, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA_MOUNTAINS});

	}

	public static boolean canSpawnAmbient(EntityType<?> type, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
		return true;
	}

	private static void addSpawn(RegistryKey<Biome> biome, EntityType<?> type, int weight, int min, int max, EntityClassification entityType) {
		if (!spawns.containsKey(biome.location())) {
			spawns.put(biome.location(), new DMASpawnerRegistry.SpawnInfo());
		}

		DMASpawnerRegistry.SpawnInfo info = spawns.get(biome.location());
		info.addSpawn(type, weight, min, max, entityType);
	}

	private static void addSpawnToAllBiomes(EntityType<?> type, int weight, int min, int max, EntityClassification entityType) {
		Iterator var5 = ForgeRegistries.BIOMES.getEntries().iterator();

		while(var5.hasNext()) {
			Map.Entry<RegistryKey<Biome>, Biome> rl = (Map.Entry)var5.next();
			addSpawn((RegistryKey)rl.getKey(), type, weight, min, max, entityType);
		}

	}

	private static void removeSpawn(EntityType<?> type, RegistryKey<Biome>... biome) {
		for(int i = 0; i < biome.length; ++i) {
			RegistryKey<Biome> bi = biome[i];
			if (spawns.containsKey(bi.location())) {
				DMASpawnerRegistry.SpawnInfo info = spawns.get(bi.location());
				info.removeSpawn(type);
			}
		}

	}

	public static class SpawnInfo {
		private List<DMASpawnerRegistry.SpawnInfo.Spawn> spawners = new ArrayList();

		public SpawnInfo() {
		}

		public void addSpawn(EntityType<?> type, int weight, int min, int max, EntityClassification entType) {
			this.spawners.add(new DMASpawnerRegistry.SpawnInfo.Spawn(new MobSpawnInfo.Spawners(type, weight, min, max), entType));
		}

		public void removeSpawn(EntityType<?> type) {
			for(int i = 0; i < this.spawners.size(); ++i) {
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
