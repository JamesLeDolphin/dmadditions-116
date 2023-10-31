package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DmAdditions;
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
import com.swdteam.common.item.*;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;


public class DMAItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DmAdditions.MODID);
	protected static RegistryObject<Item> registerAdventItem(int day, String name, Supplier<Item> supplier) {
		if (!AdventUnlock.unlockAt(day)) return null;

		return ITEMS.register(name, supplier);
	}

	protected static RegistryObject<Item> registerDMAAdventItem(int day, String name, Supplier<Item> supplier) {
		if (!AdventUnlock.unlockAt(day)) return null;

		return ITEMS.register(name, supplier);
	}

	protected static RegistryObject<Item> addAdventSpawnItem(int day, String key) {
		if (!AdventUnlock.unlockAt(day)) return null;

		return addSpawnItem(key);
	}

	public static final RegistryObject<Item> MISSINGO = ITEMS.register("missingo",
		() -> new Item(new Item.Properties().fireResistant()));


	public static RegistryObject<Item> BAUBLE = registerAdventItem(1, "bauble", () -> new BaubleBlockItem(new Item.Properties().tab(ItemGroup.TAB_DECORATIONS),
		DMABlocks.RED_BAUBLE_BLOCK.get()));
	public static RegistryObject<Item> HANDLES_ITEM = registerAdventItem(2, "handles_item",
			() -> new HandlesItem(new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item>	BLUE_CANDY_CANE = com.swdteam.common.RegistryHandler.ITEMS.register("blue_candy_cane",
			() -> new FoodItem((new Item.Properties()).food(DMAFoods.CANDY_CANE).tab(ItemGroup.TAB_FOOD)));

	public static RegistryObject<Item> RED_CANDY_CANE = ITEMS.register("red_candy_cane",
			() -> new FoodItem((new Item.Properties()).food(DMAFoods.CANDY_CANE).tab(ItemGroup.TAB_FOOD)));

	public static RegistryObject<Item> GREEN_CANDY_CANE = ITEMS.register("green_candy_cane",
			() -> new FoodItem((new Item.Properties()).food(DMAFoods.CANDY_CANE).tab(ItemGroup.TAB_FOOD)));

	public static RegistryObject<Item> ORANGE_CANDY_CANE = ITEMS.register("orange_candy_cane",
			() -> new FoodItem((new Item.Properties()).food(DMAFoods.CANDY_CANE).tab(ItemGroup.TAB_FOOD)));

	public static RegistryObject<Item> PURPLE_CANDY_CANE = ITEMS.register("purple_candy_cane",

			() -> new FoodItem((new Item.Properties()).food(DMAFoods.CANDY_CANE).tab(ItemGroup.TAB_FOOD)));
	public static RegistryObject<Item> PINK_CANDY_CANE = ITEMS.register("pink_candy_cane",

			() -> new FoodItem((new Item.Properties()).food(DMAFoods.CANDY_CANE).tab(ItemGroup.TAB_FOOD)));
	public static RegistryObject<Item> YELLOW_CANDY_CANE = ITEMS.register("yellow_candy_cane",
			() -> new FoodItem((new Item.Properties()).food(DMAFoods.CANDY_CANE).tab(ItemGroup.TAB_FOOD)));


	public static RegistryObject<Item> SNOWMAN_SPAWNER = addSpawnItem("snowman");

	public static RegistryObject<Item> UNIT_GUN = ITEMS.register("unit_gun",
			() -> new GunItem(DMItemTiers.DALEK_GUNSTICK, 0.1F, DMAProjectiles.BULLET, null, DMASoundEvents.PISTOL_SHOOT,
				(new Item.Properties().durability(500)).tab(ItemGroup.TAB_COMBAT)));

	public static RegistryObject<Item> TORCHWOOD_PISTOL = ITEMS.register("torchwood_pistol",
			() -> new GunItem(DMItemTiers.DALEK_GUNSTICK, 0.1F, DMAProjectiles.METALLIC_GOLD_LASER, null, DMASoundEvents.PISTOL_SHOOT,
				(new Item.Properties().durability(1000)).tab(ItemGroup.TAB_COMBAT)));


	public static RegistryObject<Item> SANTA_HAT = ITEMS.register("santa_hat",
			() -> new ClothesItem(EquipmentSlotType.HEAD));

	public static RegistryObject<Item> MATTS_PINK_THONG = ITEMS.register("matts_pink_thong",
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

	public static RegistryObject<Item> SPACE_SUIT_HELMET = ITEMS.register("space_suit_helmet",
			() -> new ArmorItem(DMAArmorMaterial.SPACE_SUIT, EquipmentSlotType.HEAD, new Item.Properties()));

	public static RegistryObject<Item> SPACE_SUIT_CHESTPLATE = ITEMS.register("space_suit_chestplate",
			() -> new ArmorItem(DMAArmorMaterial.SPACE_SUIT, EquipmentSlotType.CHEST, new Item.Properties()));

	public static RegistryObject<Item> SPACE_SUIT_LEGGINGS = ITEMS.register("space_suit_leggings",
			() -> new ArmorItem(DMAArmorMaterial.SPACE_SUIT, EquipmentSlotType.LEGS, new Item.Properties()));

	public static RegistryObject<Item> SPACE_SUIT_BOOTS = ITEMS.register("space_suit_boots",
			() -> new ArmorItem(DMAArmorMaterial.SPACE_SUIT, EquipmentSlotType.FEET, new Item.Properties()));


	public static RegistryObject<Item> LASER_SCREWDRIVER = ITEMS.register("laser_screwdriver",
			() -> new LaserScrewdriverItem(ItemGroup.TAB_TOOLS, 100, DMAProjectiles.METALLIC_GOLD_LASER));

	public static RegistryObject<Item> MUSIC_DISC_PFD = ITEMS.register("music_disc_pfd",
			() -> new DiscItem(5, DMASoundEvents.MUSIC_DISC_PFD, (new Item.Properties()).rarity(Rarity.RARE).tab(ItemGroup.TAB_MISC)));

		public static RegistryObject<Item> WOODEN_CYBERMAN_SPAWNER = ITEMS.register("pilot_fish",
			() -> new ForgeSpawnEggItem(DMAEntities.PILOT_FISH::get,
			0, 0, new Item.Properties().tab(ItemGroup.TAB_MISC)));

		public static RegistryObject<Item> PILOT_FISH_SPAWNER = addSpawnItem("pilot_fish");
		public static RegistryObject<Item> PILOT_FISH_TRUMPET = ITEMS.register("pilot_fish_trumpet",
			() -> new GunItem(DMItemTiers.DALEK_CANNON, 2.0F,
				DMProjectiles.EXPLOSIVE_LASER, DMSoundEvents.ENTITY_DALEK_CANNON_CHARGE,
				DMSoundEvents.ENTITY_DALEK_CANNON_SHOOT, (new Item.Properties()).tab(ItemGroup.TAB_COMBAT)));

	public static RegistryObject<Item> BESSIE = ITEMS.register("bessie", () -> new ForgeSpawnEggItem(DMAEntities.BESSIE::get,
			0, 0, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> TW_SUV = ITEMS.register("torchwood_suv", () -> new ForgeSpawnEggItem(DMAEntities.TW_SUV::get,
			0, 0, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> RPG = ITEMS.register("rpg",
			() -> new GunItem(DMItemTiers.DALEK_CANNON, 0.1F, DMAProjectiles.EXPLOSIVE_LASER, DMSoundEvents.ENTITY_DALEK_CANNON_CHARGE,
				DMSoundEvents.ENTITY_DALEK_CANNON_SHOOT, (new Item.Properties().durability(1000)).tab(ItemGroup.TAB_COMBAT)));

	public static RegistryObject<Item> PISTOL =  ITEMS.register("pistol", ()
			-> new GunItem(DMItemTiers.DALEK_GUNSTICK, 0.15F, DMAProjectiles.PURPLE_LASER, null,
			DMASoundEvents.PISTOL_SHOOT, (new Item.Properties().durability(100)).tab(ItemGroup.TAB_COMBAT)));

	public static RegistryObject<Item> TARDIS_GOLD_KEY =  ITEMS.register("tardis_gold_key",
			() -> new TardisRemoteKeyItem((new Item.Properties()).durability(32).tab(DMTabs.DM_TARDIS), ""));

	public static RegistryObject<Item> DINO_NUGGETS =  ITEMS.register("dino_nuggets",
			() -> new FoodItem((new Item.Properties()).food(DMAFoods.DINO_NUGGETS).tab(ItemGroup.TAB_FOOD)));

	public static RegistryObject<Item> DINO_NUGGETS_CUSTARD =  ITEMS.register("dino_nuggets_custard",
			() -> new FoodItem((new Item.Properties()).food(DMAFoods.DINO_NUGGETS_CUSTARD).tab(ItemGroup.TAB_FOOD)));


	public static <T extends Entity> RegistryObject<Item> addSpawnItem(String key, String type) {
		RegistryObject<Item> item =  ITEMS.register(type + "_spawner", () -> {
			return new SpawnerItem(key, type);
		});
		return item;
	}

	public static <T extends Entity> RegistryObject<Item> addSpawnItem(String key) {
		return addSpawnItem(key, key);
	}
}