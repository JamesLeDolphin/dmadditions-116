package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.entity.PilotFishEntity;
import com.jdolphin.dmadditions.entity.WoodenCybermanEntity;
import com.swdteam.common.RegistryHandler;
import com.swdteam.main.DalekMod;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;

import java.util.Optional;

public class DMAEntities {
	public static final RegistryObject<EntityType<WoodenCybermanEntity>> WOODEN_CYBERMAN_ENTITY;

	public static void init() {
	}

	public static void registerEntityWorldSpawn(EntityType<?> entity, EntityClassification classification, int weight, int groupMin, int groupMax, Biome... biomes) {
		Biome[] var6 = biomes;
		int var7 = biomes.length;

		for (int var8 = 0; var8 < var7; ++var8) {
			Biome biome = var6[var8];
			if (biome != null && biome.getBiomeCategory() != Biome.Category.OCEAN) {
			}
		}

	}

	public static EntityType<?> getEntityTypeFromString(String s) {
		Optional<EntityType<?>> ty = EntityType.byString("dalekmod:" + s);
		return (EntityType) ty.get();
	}

	public static final RegistryObject<EntityType<PilotFishEntity>> PILOT_FISH;

	static {

		WOODEN_CYBERMAN_ENTITY = RegistryHandler.ENTITY_TYPES.register("wooden_cyberman", () -> {
			return EntityType.Builder.of(WoodenCybermanEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.9F)
				.build((new ResourceLocation("dalekmod", "wooden_cyberman")).toString());
		});

		PILOT_FISH = RegistryHandler.ENTITY_TYPES.register("pilot_fish",
			() -> EntityType.Builder.of(PilotFishEntity::new, EntityClassification.MONSTER).sized(0.6f, 1.9f)
				.build((new ResourceLocation(DalekMod.MODID, "pilot_fish")).toString()));
	}
}
