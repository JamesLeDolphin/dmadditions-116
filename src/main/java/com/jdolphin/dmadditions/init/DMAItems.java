package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.RegistryHandler;
import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.client.model.armor.MattsPinkThongModel;
import com.jdolphin.dmadditions.item.BaubleBlockItem;
import com.jdolphin.dmadditions.item.LaserScrewdriverItem;
import com.jdolphin.dmadditions.item.TardisRemoteKeyItem;
import com.jdolphin.dmadditions.item.handles.HandlesItem;
import com.swdteam.common.init.DMItemTiers;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTabs;
import com.swdteam.common.item.ClothesItem;
import com.swdteam.common.item.DiscItem;
import com.swdteam.common.item.FoodItem;
import com.swdteam.common.item.GunItem;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

import static com.jdolphin.dmadditions.RegistryHandler.DMARegistries.ITEMS;
import static com.swdteam.common.init.DMItems.addSpawnItem;


public class DMAItems {

	public static RegistryObject<Item> DINO_NUGGETS;
	public static RegistryObject<Item> DINO_NUGGETS_CUSTARD;
	public static RegistryObject<Item> PISTOL;
	public static RegistryObject<Item> UNIT_GUN;
	public static RegistryObject<Item> TORCHWOOD_PISTOL;
	public static RegistryObject<Item> LASER_SCREWDRIVER;
	public static RegistryObject<Item> TARDIS_GOLD_KEY;

	public static RegistryObject<Item> BLUE_CANDY_CANE;
	public static RegistryObject<Item> GREEN_CANDY_CANE;
	public static RegistryObject<Item> RED_CANDY_CANE;
	public static RegistryObject<Item> ORANGE_CANDY_CANE;
	public static RegistryObject<Item> PURPLE_CANDY_CANE;
	public static RegistryObject<Item> PINK_CANDY_CANE;
	public static RegistryObject<Item> YELLOW_CANDY_CANE;


	public static final RegistryObject<Item> SNOWMAN_SPAWNER;

	public static final RegistryObject<Item> MUSIC_DISC_PFD;

	public static final RegistryObject<Item> SANTA_HAT;
	public static final RegistryObject<Item> MATTS_PINK_THONG;

	public static final RegistryObject<Item> SPACE_SUIT_BOOTS;
	public static final RegistryObject<Item> SPACE_SUIT_LEGGINGS;
	public static final RegistryObject<Item> SPACE_SUIT_CHESTPLATE;
	public static final RegistryObject<Item> SPACE_SUIT_HELMET;


	public static RegistryObject<Item> BULLET_ITEM;
	public static RegistryObject<Item> RPG;


	public static final RegistryObject<Item> WOODEN_CYBERMAN_SPAWNER;
	public static final RegistryObject<Item> PILOT_FISH_SPAWNER;
	public static final RegistryObject<Item> PILOT_FISH_TRUMPET;

	public static final RegistryObject<Item> BESSIE;
	public static final RegistryObject<Item> TW_SUV;
	public static final RegistryObject<Item> BAUBLE;

	public static final RegistryObject<Item> HANDLES_ITEM;
	protected static RegistryObject<Item> registerAdventItem(int day, String name, Supplier<Item> supplier) {
		if (!AdventUnlock.unlockAt(day)) return null;

		return com.swdteam.common.RegistryHandler.ITEMS.register(name, supplier);
	}

	protected static RegistryObject<Item> registerDMAAdventItem(int day, String name, Supplier<Item> supplier) {
		if (!AdventUnlock.unlockAt(day)) return null;

		return ITEMS.register(name, supplier);
	}

	protected static RegistryObject<Item> addAdventSpawnItem(int day, String key) {
		if (!AdventUnlock.unlockAt(day)) return null;

		return addSpawnItem(key);
	}

	public static void registerItems(RegistryEvent.Register<Item> event) {

		// Register HandlesItem
		HANDLES_ITEM.ifPresent(event.getRegistry()::register);
	}

	public static final RegistryObject<Item> MISSINGO = ITEMS.register("missingo",
		() -> new Item(new Item.Properties().fireResistant()));

	static {
		BAUBLE = registerAdventItem(1, "bauble", () -> new BaubleBlockItem(new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));
		HANDLES_ITEM = registerAdventItem(2, "handles_item",
			() -> new HandlesItem(new Item.Properties().tab(ItemGroup.TAB_MISC)));

		BLUE_CANDY_CANE = com.swdteam.common.RegistryHandler.ITEMS.register("blue_candy_cane",
			() -> new FoodItem((new Item.Properties()).food(DMAFoods.CANDY_CANE).tab(ItemGroup.TAB_FOOD)));

		RED_CANDY_CANE = com.swdteam.common.RegistryHandler.ITEMS.register("red_candy_cane",
			() -> new FoodItem((new Item.Properties()).food(DMAFoods.CANDY_CANE).tab(ItemGroup.TAB_FOOD)));

		GREEN_CANDY_CANE = com.swdteam.common.RegistryHandler.ITEMS.register("green_candy_cane",
			() -> new FoodItem((new Item.Properties()).food(DMAFoods.CANDY_CANE).tab(ItemGroup.TAB_FOOD)));

		ORANGE_CANDY_CANE = com.swdteam.common.RegistryHandler.ITEMS.register("orange_candy_cane",
			() -> new FoodItem((new Item.Properties()).food(DMAFoods.CANDY_CANE).tab(ItemGroup.TAB_FOOD)));

		PURPLE_CANDY_CANE = com.swdteam.common.RegistryHandler.ITEMS.register("purple_candy_cane",

			() -> new FoodItem((new Item.Properties()).food(DMAFoods.CANDY_CANE).tab(ItemGroup.TAB_FOOD)));
		PINK_CANDY_CANE = com.swdteam.common.RegistryHandler.ITEMS.register("pink_candy_cane",

			() -> new FoodItem((new Item.Properties()).food(DMAFoods.CANDY_CANE).tab(ItemGroup.TAB_FOOD)));
		YELLOW_CANDY_CANE = com.swdteam.common.RegistryHandler.ITEMS.register("yellow_candy_cane",
			() -> new FoodItem((new Item.Properties()).food(DMAFoods.CANDY_CANE).tab(ItemGroup.TAB_FOOD)));


		SNOWMAN_SPAWNER = addSpawnItem("snowman");

		UNIT_GUN = com.swdteam.common.RegistryHandler.ITEMS.register("unit_gun",
			() -> new GunItem(DMItemTiers.DALEK_GUNSTICK, 0.1F, DMAProjectiles.BULLET, DMSoundEvents.ITEM_GUN_CLICK, DMASoundEvents.PISTOL_SHOOT,
				(new Item.Properties().durability(500)).tab(ItemGroup.TAB_COMBAT)));

		TORCHWOOD_PISTOL = com.swdteam.common.RegistryHandler.ITEMS.register("torchwood_pistol",
			() -> new GunItem(DMItemTiers.DALEK_GUNSTICK, 0.1F, DMAProjectiles.METALLIC_GOLD_LASER, DMSoundEvents.ITEM_GUN_CLICK, DMASoundEvents.PISTOL_SHOOT,
				(new Item.Properties().durability(1000)).tab(ItemGroup.TAB_COMBAT)));


		SANTA_HAT = com.swdteam.common.RegistryHandler.ITEMS.register("santa_hat",
			() -> new ClothesItem(EquipmentSlotType.HEAD));

		MATTS_PINK_THONG = ITEMS.register( "matts_pink_thong",
			() -> new ArmorItem(DMAArmorMaterial.MATTS_PINK_THONG, EquipmentSlotType.LEGS, new Item.Properties()) {

				@Override
				@OnlyIn(Dist.CLIENT)
				public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
					return (A) new MattsPinkThongModel(1f);
				}

				@Override
				public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
					return new ResourceLocation(DmAdditions.MODID, "textures/models/armor/matts_pink_thong.png").toString();
				}

				@Override
				public boolean canEquip(ItemStack stack, EquipmentSlotType armorType, Entity entity) {
					if (armorType.equals(EquipmentSlotType.HEAD) || armorType.equals(EquipmentSlotType.LEGS))
						return true;

					return super.canEquip(stack, armorType, entity);
				}
			});

		SPACE_SUIT_HELMET = RegistryHandler.DMARegistries.ITEMS.register( "space_suit_helmet",
			() -> new ArmorItem(DMAArmorMaterial.SPACE_SUIT, EquipmentSlotType.HEAD, new Item.Properties()));

		SPACE_SUIT_CHESTPLATE = RegistryHandler.DMARegistries.ITEMS.register( "space_suit_chestplate",
			() -> new ArmorItem(DMAArmorMaterial.SPACE_SUIT, EquipmentSlotType.CHEST, new Item.Properties()));

		SPACE_SUIT_LEGGINGS = RegistryHandler.DMARegistries.ITEMS.register( "space_suit_leggings",
			() -> new ArmorItem(DMAArmorMaterial.SPACE_SUIT, EquipmentSlotType.LEGS, new Item.Properties()));

		SPACE_SUIT_BOOTS = RegistryHandler.DMARegistries.ITEMS.register( "space_suit_boots",
			() -> new ArmorItem(DMAArmorMaterial.SPACE_SUIT, EquipmentSlotType.FEET, new Item.Properties()));


		LASER_SCREWDRIVER = com.swdteam.common.RegistryHandler.ITEMS.register( "laser_screwdriver",
			() -> new LaserScrewdriverItem(ItemGroup.TAB_TOOLS, 100, DMAProjectiles.METALLIC_GOLD_LASER));

		MUSIC_DISC_PFD = com.swdteam.common.RegistryHandler.ITEMS.register( "music_disc_pfd",
			() -> new DiscItem(5, DMASoundEvents.MUSIC_DISC_PFD, (new Item.Properties()).rarity(Rarity.RARE).tab(ItemGroup.TAB_MISC)));

		WOODEN_CYBERMAN_SPAWNER = addSpawnItem("wooden_cyberman");
		PILOT_FISH_SPAWNER = addSpawnItem("pilot_fish");
		PILOT_FISH_TRUMPET = com.swdteam.common.RegistryHandler.ITEMS.register("pilot_fish_trumpet",
			() -> new GunItem(DMItemTiers.DALEK_CANNON, 2.0F,
				DMProjectiles.EXPLOSIVE_LASER, DMSoundEvents.ENTITY_DALEK_CANNON_CHARGE,
				DMSoundEvents.ENTITY_DALEK_CANNON_SHOOT, (new Item.Properties()).tab(ItemGroup.TAB_COMBAT)));

		BESSIE = ITEMS.register("bessie", () -> new ForgeSpawnEggItem(DMAEntities.BESSIE::get,
			0, 0, new Item.Properties().tab(ItemGroup.TAB_MISC)));

		TW_SUV = ITEMS.register("torchwood_suv", () -> new ForgeSpawnEggItem(DMAEntities.TW_SUV::get,
			0, 0, new Item.Properties().tab(ItemGroup.TAB_MISC)));

		RPG = ITEMS.register("rpg",
			() -> new GunItem(DMItemTiers.DALEK_CANNON, 0.1F, DMAProjectiles.EXPLOSIVE_LASER, DMSoundEvents.ENTITY_DALEK_CANNON_CHARGE,
				DMSoundEvents.ENTITY_DALEK_CANNON_SHOOT, (new Item.Properties().durability(1000)).tab(ItemGroup.TAB_COMBAT)));

		PISTOL = com.swdteam.common.RegistryHandler.ITEMS.register("pistol", ()
			-> new GunItem(DMItemTiers.DALEK_GUNSTICK, 0.15F, DMAProjectiles.PURPLE_LASER, null,
			DMASoundEvents.PISTOL_SHOOT, (new Item.Properties().durability(100)).tab(ItemGroup.TAB_COMBAT)));

		TARDIS_GOLD_KEY = com.swdteam.common.RegistryHandler.ITEMS.register("tardis_gold_key",
			() -> new TardisRemoteKeyItem((new Item.Properties()).durability(32).tab(DMTabs.DM_TARDIS), ""));

		DINO_NUGGETS = com.swdteam.common.RegistryHandler.ITEMS.register("dino_nuggets",
			() -> new FoodItem((new Item.Properties()).food(DMAFoods.DINO_NUGGETS).tab(ItemGroup.TAB_FOOD)));

		DINO_NUGGETS_CUSTARD = com.swdteam.common.RegistryHandler.ITEMS.register("dino_nuggets_custard",
			() -> new FoodItem((new Item.Properties()).food(DMAFoods.DINO_NUGGETS_CUSTARD).tab(ItemGroup.TAB_FOOD)));

//		STEEL_BUCKET = ITEMS.register("steel_bucket",
//			() -> new BucketItem(() -> DMAFluids.STEEL_FLUID.get(),
//				new Item.Properties().stacksTo(1)));
	}

}