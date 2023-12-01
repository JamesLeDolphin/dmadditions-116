package com.jdolphin.dmadditions.init;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.google.common.base.Supplier;
import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.entity.BessieEntity;
import com.jdolphin.dmadditions.entity.ChristmasTreeEntity;
import com.jdolphin.dmadditions.entity.FlyingSharkEntity;
import com.jdolphin.dmadditions.entity.JamesLeDolphinEntity;
import com.jdolphin.dmadditions.entity.PilotFishEntity;
import com.jdolphin.dmadditions.entity.RacnossEntity;
import com.jdolphin.dmadditions.entity.ShoppingCartEntity;
import com.jdolphin.dmadditions.entity.SnowmanEntity;
import com.jdolphin.dmadditions.entity.TorchwoodSuvEntity;
import com.jdolphin.dmadditions.entity.WoodenCybermanEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DMAEntities {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, DmAdditions.MODID);

	public static EntityType<?> getEntityTypeFromString(String s) {
		Optional<EntityType<?>> ty = EntityType.byString("dmadditions:" + s);
		return (EntityType)ty.get();
	}

	public static final RegistryObject<EntityType<JamesLeDolphinEntity>> JAMESLEDOLPHIN = ENTITY_TYPES.register("jamesledolphin",
			() -> EntityType.Builder.of(JamesLeDolphinEntity::new, EntityClassification.WATER_CREATURE).sized(0.9F, 0.6F)
				.build((new ResourceLocation(DmAdditions.MODID, "jamesledolphin")).toString()));

	public static RegistryObject<EntityType<PilotFishEntity>> PILOT_FISH = ENTITY_TYPES.register("pilot_fish",
			() -> EntityType.Builder.of(PilotFishEntity::new, EntityClassification.MONSTER).sized(0.6f, 1.9f)
				.build((new ResourceLocation(DmAdditions.MODID, "pilot_fish")).toString()));

	public static RegistryObject<EntityType<SnowmanEntity>> SNOWMAN = ENTITY_TYPES.register("snowman",
			() -> EntityType.Builder.of(SnowmanEntity::new, EntityClassification.MONSTER)
				.build((new ResourceLocation(DmAdditions.MODID, "snowman")).toString()));

	public static RegistryObject<EntityType<ChristmasTreeEntity>> CHRISTMAS_TREE = ENTITY_TYPES.register("christmas_tree",
				() -> EntityType.Builder.of(ChristmasTreeEntity::new, EntityClassification.MONSTER)
					.build((new ResourceLocation(DmAdditions.MODID, "christmas_tree")).toString()));

	public static RegistryObject<EntityType<WoodenCybermanEntity>> WOODEN_CYBERMAN = ENTITY_TYPES.register("wooden_cyberman",
				() -> EntityType.Builder.of(WoodenCybermanEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.9F)
					.build((new ResourceLocation(DmAdditions.MODID, "wooden_cyberman")).toString()));

	public static RegistryObject<EntityType<BessieEntity>> BESSIE = ENTITY_TYPES.register("bessie",
				() -> EntityType.Builder.of(BessieEntity::new, EntityClassification.MISC).sized(0.9F, 0.6F)
					.build((new ResourceLocation(DmAdditions.MODID, "bessie")).toString()));

	public static RegistryObject<EntityType<TorchwoodSuvEntity>> TW_SUV = ENTITY_TYPES.register("torchwood_suv",
				() -> EntityType.Builder.of(TorchwoodSuvEntity::new, EntityClassification.MISC).sized(3F, 2F)
					.build((new ResourceLocation(DmAdditions.MODID, "torchwood_suv")).toString()));

	public static RegistryObject<EntityType<?>> FLYING_SHARK = registerAdventEntity(3, "flying_shark",
				() -> EntityType.Builder.of(FlyingSharkEntity::new, EntityClassification.CREATURE)
					.sized(2F, 1F)
					.setTrackingRange(80)
					.setUpdateInterval(3)
					.setShouldReceiveVelocityUpdates(true)
					.build(new ResourceLocation(DmAdditions.MODID, "flying_shark").toString()));

	public static RegistryObject<EntityType<?>> RACNOSS = registerAdventEntity(25, "racnoss", // TODO: set the correct date
			() -> EntityType.Builder.of(RacnossEntity::new, EntityClassification.MONSTER).sized(3F, 2F)
				.build((new ResourceLocation(DmAdditions.MODID, "racnoss")).toString()));

	public static RegistryObject<EntityType<?>> SHOPPING_CART = registerAdventEntity(21, "shopping_cart",
			() -> EntityType.Builder.of(ShoppingCartEntity::new, EntityClassification.MISC).sized(0.98F, 0.7F).clientTrackingRange(8)
			.build((new ResourceLocation(DmAdditions.MODID, "shopping_cart")).toString()));

	@Nullable 
	protected static RegistryObject<EntityType<?>> registerAdventEntity(int date, String name, Supplier<EntityType<?>> supplier){
		if(AdventUnlock.unlockAt(date)){
			return ENTITY_TYPES.register(name, supplier);
		}

		return null;
	}
}
