package com.jdolphin.dmadditions.init;

import com.google.common.base.Supplier;
import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.entity.*;
import com.jdolphin.dmadditions.entity.control.TardisControl;
import com.jdolphin.dmadditions.entity.cyber.MondasCybermanEntity;
import com.jdolphin.dmadditions.entity.cyber.WoodenCybermanEntity;
import com.jdolphin.dmadditions.entity.timelord.TimeLordEntity;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DMAEntities {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, DmAdditions.MODID);

	public static EntityType<?> getEntityTypeFromString(String s) {
		Optional<EntityType<?>> ty = EntityType.byString("dmadditions:" + s);
		return ty.get();
	}

	public static final RegistryObject<EntityType<JamesLeDolphinEntity>> JAMESLEDOLPHIN = registerEntity("jamesledolphin",
		JamesLeDolphinEntity::new, EntityClassification.WATER_CREATURE, 0.9f, 0.6f);

	public static RegistryObject<EntityType<PilotFishEntity>> PILOT_FISH = registerHumanoidEntity("pilot_fish", PilotFishEntity::new, EntityClassification.MONSTER);

	public static RegistryObject<EntityType<SnowmanEntity>> SNOWMAN = registerEntity("snowman", SnowmanEntity::new, EntityClassification.MONSTER);

	public static RegistryObject<EntityType<ChristmasTreeEntity>> CHRISTMAS_TREE = registerEntity("christmas_tree",
		ChristmasTreeEntity::new, EntityClassification.MONSTER);

	public static RegistryObject<EntityType<WoodenCybermanEntity>> WOODEN_CYBERMAN = registerHumanoidEntity("wooden_cyberman",
		WoodenCybermanEntity::new, EntityClassification.MONSTER);

	public static RegistryObject<EntityType<MondasCybermanEntity>> MONDAS_CYBERMAN = registerHumanoidEntity("mondas_cyberman",
		MondasCybermanEntity::new, EntityClassification.MONSTER);

	public static RegistryObject<EntityType<BessieEntity>> BESSIE = registerEntity("bessie", BessieEntity::new, EntityClassification.MISC, 0.9f, 0.6f);

	public static RegistryObject<EntityType<TorchwoodSuvEntity>> TW_SUV = registerEntity("torchwood_suv", TorchwoodSuvEntity::new,
		EntityClassification.MISC, 3, 2);

	public static RegistryObject<EntityType<RacnossEntity>> RACNOSS = registerEntity("racnoss",
		RacnossEntity::new, EntityClassification.MONSTER, 3f, 2f);

	public static RegistryObject<EntityType<ShoppingCartEntity>> SHOPPING_CART = registerEntity("shopping_cart",
		ShoppingCartEntity::new, EntityClassification.MISC, 0.98f, 0.7f);

	public static RegistryObject<EntityType<TankEntity>> TORCHWOOD_TANK = registerEntity("torchwood_tank", TankEntity::new, EntityClassification.MISC,
		1, 1);

	public static RegistryObject<EntityType<ChristmasCreeperEntity>> CHRISTMAS_CREEPER = registerEntity("christmas_creeper",
		ChristmasCreeperEntity::new, EntityClassification.MONSTER, 0.6f, 1.7f);

	public static RegistryObject<EntityType<WhispermanEntity>> WHISPERMAN = registerHumanoidEntity("whisperman",
		WhispermanEntity::new, EntityClassification.MONSTER);

	public static RegistryObject<EntityType<KantrofarriEntity>> KANTROFARRI = registerEntity("kantrofarri",
		KantrofarriEntity::new, EntityClassification.MONSTER, 1.0f, 0.4f);

	public static RegistryObject<EntityType<JimEntity>> JIM = registerHumanoidEntity("jim", JimEntity::new, EntityClassification.CREATURE);


	public static RegistryObject<EntityType<HerobrineEntity>> HEROBRINE = ENTITY_TYPES.register("herobrine", () -> EntityType.Builder.of(HerobrineEntity::new, EntityClassification.MONSTER)
		.clientTrackingRange(64)
		.setTrackingRange(64)
		.build(Helper.createAdditionsRL("herobrine").toString()));

	public static RegistryObject<EntityType<TardisControl>> CONTROL = registerEntity("control", TardisControl::new, EntityClassification.MISC, 0.2f, 0.2f);

	public static RegistryObject<EntityType<FlyingSharkEntity>> FLYING_SHARK = ENTITY_TYPES.register("flying_shark",
		() -> EntityType.Builder.of(FlyingSharkEntity::new, EntityClassification.CREATURE)
			.sized(2F, 1F)
			.setTrackingRange(80)
			.setUpdateInterval(3)
			.setShouldReceiveVelocityUpdates(true)
			.build(Helper.createAdditionsRL("flying_shark").toString()));



	private static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.IFactory<T> entityClass,
																				   EntityClassification classification, float width, float height) {
		return ENTITY_TYPES.register(name, () -> EntityType.Builder.of(entityClass, classification).sized(width, height)
			.clientTrackingRange(8).build(Helper.createAdditionsRL(name).toString()));
	}

	private static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.IFactory<T> entityClass,
																				   EntityClassification classification) {
		return ENTITY_TYPES.register(name, () -> EntityType.Builder.of(entityClass, classification)
			.clientTrackingRange(8).build(Helper.createAdditionsRL(name).toString()));
	}

	private static <T extends Entity> RegistryObject<EntityType<T>> registerHumanoidEntity(String name, EntityType.IFactory<T> entityClass,
																				   EntityClassification classification) {
		return registerEntity(name, entityClass, classification, 0.6f, 1.8f);
	}

	@Nullable 
	protected static <T extends Entity> RegistryObject<EntityType<T>> registerAdventEntity(int date, String name, Supplier<EntityType<T>> supplier){
		if(AdventUnlock.unlockAt(date)){
			return ENTITY_TYPES.register(name, supplier);
		}

		return null;
	}
}
