package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DMAdditions;
import com.jdolphin.dmadditions.entity.*;
import com.jdolphin.dmadditions.entity.cyber.*;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DMAEntities {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, DMAdditions.MODID);

	public static RegistryObject<EntityType<TimeLordEntity>> TIMELORD = registerHumanoidEntity("timelord", TimeLordEntity::new, EntityClassification.AMBIENT);

	public static RegistryObject<EntityType<SnowmanEntity>> SNOWMAN = registerEntity("snowman", SnowmanEntity::new, EntityClassification.MONSTER);

	public static RegistryObject<EntityType<WoodenCybermanEntity>> WOODEN_CYBERMAN = registerHumanoidEntity("wooden_cyberman",
		WoodenCybermanEntity::new, EntityClassification.MONSTER);

	public static RegistryObject<EntityType<DMACybermanEntity>> WARRIOR_CYBERMAN = registerHumanoidEntity("warrior_cyberman",
		((type, world) ->
			new DMACybermanEntity(type, world, DMAItems.CYBER_WARRIOR_SPAWNER.get(), null, DMACybermanEntity.CybermanType.CYBERWARRIOR)), EntityClassification.MONSTER);

	public static RegistryObject<EntityType<DMACybermanEntity>> TOMB_CYBERMAN = registerHumanoidEntity("tomb_cyberman",
		((type, world) ->
			new DMACybermanEntity(type, world, DMAItems.CYBER_WARRIOR_SPAWNER.get(), null, DMACybermanEntity.CybermanType.TOMB)), EntityClassification.MONSTER);

	public static RegistryObject<EntityType<DMACybermanEntity>> TOMB_CYBER_CONTROLLER = registerHumanoidEntity("tomb_cyber_controller",
		((type, world) ->
			new DMACybermanEntity(type, world, DMAItems.TOMB_CYBER_CONTROLLER.get(),
				DMASoundEvents.TOMB_CONTROLLER_AMBIENT.get(), DMACybermanEntity.CybermanType.TOMB_CONTROLLER)), EntityClassification.MONSTER);

	public static RegistryObject<EntityType<DMACybermanEntity>> INVASION_CYBERMAN = registerHumanoidEntity("invasion_cyberman",
		((type, world) ->
			new DMACybermanEntity(type, world, DMAItems.INVASION_CYBERMAN.get(), DMASoundEvents.INVASION_AMBIENT.get(), DMACybermanEntity.CybermanType.INVASION)), EntityClassification.MONSTER);

	public static RegistryObject<EntityType<MondasCybermanEntity>> MONDAS_CYBERMAN = registerHumanoidEntity("mondas_cyberman",
		MondasCybermanEntity::new, EntityClassification.MONSTER);

	public static RegistryObject<EntityType<MondasianEntity>> MONDASIAN = registerHumanoidEntity("mondasian",
		MondasianEntity::new, EntityClassification.MONSTER);

	public static RegistryObject<EntityType<VehicleEntity>> BESSIE = registerEntity("bessie", VehicleEntity::new, EntityClassification.MISC, 0.9f, 0.6f);

	public static RegistryObject<EntityType<VehicleEntity>> DAVROS_CHAIR = registerEntity("davros_chair", VehicleEntity::new, EntityClassification.MISC, 0.9f, 0.6f);

	public static RegistryObject<EntityType<VehicleEntity>> TW_SUV = registerEntity("torchwood_suv", VehicleEntity::new,
		EntityClassification.MISC, 3, 2);

	public static RegistryObject<EntityType<RacnossEntity>> RACNOSS = registerEntity("racnoss",
		RacnossEntity::new, EntityClassification.MONSTER, 3f, 2f);

	public static RegistryObject<EntityType<CyberCowEntity>> CYBERCOW = registerEntity("cybercow",
		CyberCowEntity::new, EntityClassification.MONSTER, 0.9f, 1.4f);

	public static RegistryObject<EntityType<ShoppingCartEntity>> SHOPPING_CART = registerEntity("shopping_cart",
		ShoppingCartEntity::new, EntityClassification.MISC, 0.98f, 0.7f);

	public static RegistryObject<EntityType<WhispermanEntity>> WHISPERMAN = registerHumanoidEntity("whisperman",
		WhispermanEntity::new, EntityClassification.MONSTER);

	public static RegistryObject<EntityType<KantrofarriEntity>> KANTROFARRI = registerEntity("kantrofarri",
		KantrofarriEntity::new, EntityClassification.MONSTER, 1.0f, 0.4f);

	public static RegistryObject<EntityType<DalekMutantEntity>> DALEK_MUTANT = registerEntity("dalek_mutant",
		DalekMutantEntity::new, EntityClassification.MONSTER, 1.0f, 0.4f);

	public static RegistryObject<EntityType<HerobrineEntity>> HEROBRINE = ENTITY_TYPES.register("herobrine", () -> EntityType.Builder.of(HerobrineEntity::new, EntityClassification.MONSTER)
		.clientTrackingRange(64)
		.setTrackingRange(64)
		.build(Helper.createAdditionsRL("herobrine").toString()));

	public static RegistryObject<EntityType<CyberPiglinEntity>> CYBER_PIGLIN =
		registerHumanoidEntity("cyber_piglin", CyberPiglinEntity::new, EntityClassification.MONSTER);

	public static RegistryObject<EntityType<NetheriteCybermanEntity>> NETHERITE_CYBERMAN = registerHumanoidEntity( "netherite_cyberman",
		NetheriteCybermanEntity::new, EntityClassification.MONSTER);

	//public static RegistryObject<EntityType<TardisControl>> CONTROL = registerAdventEntity( 24, "control", TardisControl::new, EntityClassification.MISC, 0.2f, 0.2f);

	public static RegistryObject<EntityType<FlyingSharkEntity>> FLYING_SHARK = ENTITY_TYPES.register("flying_shark",
		() -> EntityType.Builder.of(FlyingSharkEntity::new, EntityClassification.CREATURE)
			.sized(2F, 1F)
			.setTrackingRange(80)
			.setUpdateInterval(3)
			.setShouldReceiveVelocityUpdates(true)
			.build(Helper.createAdditionsRL("flying_shark").toString()));

	public static final RegistryObject<EntityType<ClockworkDroidEntity>> CLOCKWORK_DROID =
		registerHumanoidEntity("clockwork_droid",
			ClockworkDroidEntity::new, EntityClassification.MONSTER);

	public static final RegistryObject<EntityType<ZygonEntity>> ZYGON =
		registerHumanoidEntity("zygon", ZygonEntity::new, EntityClassification.MONSTER);

	public static final RegistryObject<EntityType<EmptyVillagerEntity>> EMPTY_VILLAGER =
		registerHumanoidEntity("empty_villager",
			EmptyVillagerEntity::new, EntityClassification.MONSTER);

	public static final RegistryObject<EntityType<EmptyChildEntity>> EMPTY_CHILD =
		registerHumanoidEntity("empty_child", EmptyChildEntity::new, EntityClassification.MONSTER);

	public static final RegistryObject<EntityType<IceWarriorEntity>> ICE_WARRIOR =
		registerHumanoidEntity("ice_warrior", IceWarriorEntity::new, EntityClassification.MONSTER);

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
}
