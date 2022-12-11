package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.RegistryHandler.DMARegistries;
import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.entity.*;
import com.swdteam.common.RegistryHandler;
import com.swdteam.main.DalekMod;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class DMAEntities {
	public static RegistryObject<EntityType<WoodenCybermanEntity>> WOODEN_CYBERMAN;
	public static RegistryObject<EntityType<PilotFishEntity>> PILOT_FISH;
	public static RegistryObject<EntityType<SnowmanEntity>> SNOWMAN;
	public static RegistryObject<EntityType<ChristmasTreeEntity>> CHRISTMAS_TREE;

	public static final RegistryObject<EntityType<JamesLeDolphinEntity>> JAMESLEDOLPHIN;

	static {
		JAMESLEDOLPHIN = DMARegistries.ENTITY_TYPES.register("jamesledolphin",
			() -> EntityType.Builder.of(JamesLeDolphinEntity::new, EntityClassification.WATER_CREATURE).sized(0.9F, 0.6F)
				.build((new ResourceLocation(DmAdditions.MODID, "jamesledolphin")).toString()));

		PILOT_FISH = RegistryHandler.ENTITY_TYPES.register("pilot_fish",
			() -> EntityType.Builder.of(PilotFishEntity::new, EntityClassification.MONSTER).sized(0.6f, 1.9f)
				.build((new ResourceLocation(DalekMod.MODID, "pilot_fish")).toString()));

		SNOWMAN = RegistryHandler.ENTITY_TYPES.register("snowman",
			() -> EntityType.Builder.of(SnowmanEntity::new, EntityClassification.MONSTER)
				.build((new ResourceLocation(DalekMod.MODID, "snowman")).toString())
		);

		if (AdventUnlock.unlockAt(13)) {
			CHRISTMAS_TREE = RegistryHandler.ENTITY_TYPES.register("christmas_tree",
				() -> EntityType.Builder.of(ChristmasTreeEntity::new, EntityClassification.MONSTER)
					.build((new ResourceLocation(DalekMod.MODID, "christmas_tree")).toString()));
		}

		if (AdventUnlock.unlockAt(15)) {
			WOODEN_CYBERMAN = RegistryHandler.ENTITY_TYPES.register("wooden_cyberman",
				() -> EntityType.Builder.of(WoodenCybermanEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.9F)
					.build((new ResourceLocation(DalekMod.MODID, "wooden_cyberman")).toString()));
		}
	}
}
