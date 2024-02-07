package com.jdolphin.dmadditions.init;

import com.google.common.base.Supplier;
import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

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

	public static RegistryObject<EntityType<FlyingSharkEntity>> FLYING_SHARK = ENTITY_TYPES.register("flying_shark",
				() -> EntityType.Builder.of(FlyingSharkEntity::new, EntityClassification.CREATURE)
					.sized(2F, 1F)
					.setTrackingRange(80)
					.setUpdateInterval(3)
					.setShouldReceiveVelocityUpdates(true)
					.build(new ResourceLocation(DmAdditions.MODID, "flying_shark").toString()));

// 	public static RegistryObject<EntityType<IceGovernessEntity>> ICE_GOVERNESS = registerAdventEntity(18, "ice_governess",
// 			() -> EntityType.Builder.of(IceGovernessEntity::new, EntityClassification.MONSTER)
// 			.sized(0.6f, 1.8f)
// 			.build(new ResourceLocation(DmAdditions.MODID, "ice_governess").toString()));

	public static RegistryObject<EntityType<RacnossEntity>> RACNOSS = ENTITY_TYPES.register("racnoss",
			() -> EntityType.Builder.of(RacnossEntity::new, EntityClassification.MONSTER).sized(3F, 2F)
				.build((new ResourceLocation(DmAdditions.MODID, "racnoss")).toString()));

	public static RegistryObject<EntityType<ShoppingCartEntity>> SHOPPING_CART = ENTITY_TYPES.register("shopping_cart",
			() -> EntityType.Builder.of(ShoppingCartEntity::new, EntityClassification.MISC).sized(0.98F, 0.7F).clientTrackingRange(8)
				.build((new ResourceLocation(DmAdditions.MODID, "shopping_cart")).toString()));

	public static RegistryObject<EntityType<TankEntity>> TORCHWOOD_TANK = ENTITY_TYPES.register("torchwood_tank",
			() -> EntityType.Builder.of(TankEntity::new, EntityClassification.MISC).sized(1f, 1f).clientTrackingRange(8)
				.build((new ResourceLocation(DmAdditions.MODID, "torchwood_tank")).toString()));

	public static RegistryObject<EntityType<ChristmasCreeperEntity>> CHRISTMAS_CREEPER = ENTITY_TYPES.register("christmas_creeper",
			() -> EntityType.Builder.of(ChristmasCreeperEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.7F).clientTrackingRange(8)
				.build((new ResourceLocation(DmAdditions.MODID, "christmas_creeper").toString())));

	public static RegistryObject<EntityType<WhispermanEntity>> WHISPERMAN = ENTITY_TYPES.register("whisperman",
			() -> EntityType.Builder.of(WhispermanEntity::new, EntityClassification.MONSTER).sized(0.6f, 1.8f)
				.build((new ResourceLocation(DmAdditions.MODID, "whisperman").toString())));

	public static RegistryObject<EntityType<KantrofarriEntity>> KANTROFARRI = ENTITY_TYPES.register("kantrofarri",
			() -> EntityType.Builder.of(KantrofarriEntity::new, EntityClassification.MONSTER).sized(1f, 0.4f)
				.build((new ResourceLocation(DmAdditions.MODID, "kantrofarri").toString())));

	public static RegistryObject<EntityType<JimEntity>> JIM = ENTITY_TYPES.register("jim",
			() -> EntityType.Builder.of(JimEntity::new, EntityClassification.CREATURE).sized(0.6f, 1.8f)
				.build((new ResourceLocation(DmAdditions.MODID, "jim").toString())));

	@Nullable 
	protected static <T extends Entity> RegistryObject<EntityType<T>> registerAdventEntity(int date, String name, Supplier<EntityType<T>> supplier){
		if(AdventUnlock.unlockAt(date)){
			return ENTITY_TYPES.register(name, supplier);
		}

		return null;
	}
}
