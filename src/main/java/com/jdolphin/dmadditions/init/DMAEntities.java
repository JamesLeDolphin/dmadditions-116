package com.jdolphin.dmadditions.init;

import com.google.common.base.Supplier;
import com.jdolphin.dmadditions.DMAdditions;
import com.jdolphin.dmadditions.advent.TimedUnlock;
import com.jdolphin.dmadditions.entity.*;
import com.jdolphin.dmadditions.entity.timelord.TimeLordEntity;
import com.jdolphin.dmadditions.entity.cyber.*;
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
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, DMAdditions.MODID);

	public static EntityType<?> getEntityTypeFromString(String s) {
		Optional<EntityType<?>> ty = EntityType.byString("dmadditions:" + s);
		return ty.get();
	}

	public static final RegistryObject<EntityType<JamesLeDolphinEntity>> JAMESLEDOLPHIN = registerEntity("jamesledolphin",
		JamesLeDolphinEntity::new, EntityClassification.WATER_CREATURE, 0.9f, 0.6f);

	public static RegistryObject<EntityType<TimeLordEntity>> TIMELORD = registerHumanoidEntity("timelord", TimeLordEntity::new, EntityClassification.AMBIENT);

	public static RegistryObject<EntityType<SnowmanEntity>> SNOWMAN = registerEntity("snowman", SnowmanEntity::new, EntityClassification.MONSTER);

	public static RegistryObject<EntityType<WoodenCybermanEntity>> WOODEN_CYBERMAN = registerHumanoidEntity("wooden_cyberman",
		WoodenCybermanEntity::new, EntityClassification.MONSTER);

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

	public static RegistryObject<EntityType<CyberPiglinEntity>> CYBER_PIGLIN = registerAdventEntity(4, "cyber_piglin", () ->
		EntityType.Builder.of(CyberPiglinEntity::new, EntityClassification.MONSTER)
			.sized(0.5f, 1.8f)
			.build(Helper.createAdditionsRL("cyber_piglin").toString()));

	public static RegistryObject<EntityType<NetheriteCybermanEntity>> NETHERITE_CYBERMAN = registerAdventEntity(4, "netherite_cyberman", () ->
		EntityType.Builder.of(NetheriteCybermanEntity::new, EntityClassification.MONSTER)
			.sized(0.5f, 1.8f)
			.build(Helper.createAdditionsRL("netherite_cyberman").toString()));

	//public static RegistryObject<EntityType<TardisControl>> CONTROL = registerAdventEntity( 24, "control", TardisControl::new, EntityClassification.MISC, 0.2f, 0.2f);

	public static RegistryObject<EntityType<FlyingSharkEntity>> FLYING_SHARK = ENTITY_TYPES.register("flying_shark",
		() -> EntityType.Builder.of(FlyingSharkEntity::new, EntityClassification.CREATURE)
			.sized(2F, 1F)
			.setTrackingRange(80)
			.setUpdateInterval(3)
			.setShouldReceiveVelocityUpdates(true)
			.build(Helper.createAdditionsRL("flying_shark").toString()));

	public static final RegistryObject<EntityType<ClockworkDroidEntity>> CLOCKWORK_DROID =
		registerAdventEntity(6, "clockwork_droid", () ->
			EntityType.Builder.<ClockworkDroidEntity>of(ClockworkDroidEntity::new, EntityClassification.MONSTER)
				.sized(0.5f, 1.8f)
				.build(Helper.createAdditionsRL("clockwork_droid").toString())
		);

	public static final RegistryObject<EntityType<ZygonEntity>> ZYGON =
		registerAdventEntity(1, "zygon", () ->
			EntityType.Builder.<ZygonEntity>of(ZygonEntity::new, EntityClassification.MONSTER)
				.sized(0.5f, 1.95f)
				.clientTrackingRange(10)
				.build(Helper.createAdditionsRL("zygon").toString()));

	public static final RegistryObject<EntityType<EmptyVillagerEntity>> EMPTY_VILLAGER =
		registerAdventEntity(2, "empty_villager", () ->
			EntityType.Builder.<EmptyVillagerEntity>of(EmptyVillagerEntity::new, EntityClassification.MONSTER)
				.sized(0.5f, 1.8f)
				.build(Helper.createAdditionsRL("empty_villager").toString()));

	public static final RegistryObject<EntityType<EmptyChildEntity>> EMPTY_CHILD =
		registerAdventEntity(2, "empty_child", () ->
			EntityType.Builder.<EmptyChildEntity>of(EmptyChildEntity::new, EntityClassification.MONSTER)
				.sized(0.5f, 1.5f)
				.build(Helper.createAdditionsRL("empty_child").toString())
		);

	public static final RegistryObject<EntityType<IceWarriorEntity>> ICE_WARRIOR =
		registerAdventEntity(5, "ice_warrior", () ->
			EntityType.Builder.<IceWarriorEntity>of(IceWarriorEntity::new, EntityClassification.MONSTER)
				.sized(0.5f, 1.8f)
				.build(Helper.createAdditionsRL("ice_warrior").toString()));

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
	private static <T extends Entity> RegistryObject<EntityType<T>> registerAdventEntity(int date, String name,  EntityType.IFactory<T> entityClass,
																						 EntityClassification classification, float width, float height) {
		if (TimedUnlock.advent(date)) {
			return registerEntity(name, entityClass, classification, width, height);
		}
		else return null;
	}

	@Nullable
	protected static <T extends Entity> RegistryObject<EntityType<T>> registerAdventEntity(int date, String name, Supplier<EntityType<T>> supplier) {
		if (TimedUnlock.advent(date)) {
			return ENTITY_TYPES.register(name, supplier);
		}
		return null;
	}
}
