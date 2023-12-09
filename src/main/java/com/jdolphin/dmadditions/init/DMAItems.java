package com.jdolphin.dmadditions.init;

import java.util.function.Supplier;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.client.model.armor.ChristmasHatModel;
import com.jdolphin.dmadditions.client.model.armor.MattsPinkThongModel;
import com.jdolphin.dmadditions.client.model.armor.WeddingDressModel;
import com.jdolphin.dmadditions.item.*;
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
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class DMAItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DmAdditions.MODID);

	protected static RegistryObject<Item> registerAdventItem(int day, String name, Supplier<Item> supplier) {
		if (!AdventUnlock.unlockAt(day)) return null;

		return ITEMS.register(name, supplier);
	}

	//public static RegistryObject<Item> SANTA_BAUBLE = registerAdventItem(7, "santa_bauble", () -> new BaubleBlockItem(DMABlocks.SANTA_BAUBLE_BLOCK));
	public static RegistryObject<Item> BLUE_BAUBLE = registerAdventItem(7, "blue_bauble", () -> new BaubleBlockItem(DMABlocks.BLUE_BAUBLE_BLOCK));
	public static RegistryObject<Item> GOLD_BAUBLE = registerAdventItem(7, "gold_bauble", () -> new BaubleBlockItem(DMABlocks.GOLD_BAUBLE_BLOCK));
	public static RegistryObject<Item> GREEN_BAUBLE = registerAdventItem(7, "green_bauble", () -> new BaubleBlockItem(DMABlocks.GREEN_BAUBLE_BLOCK));
	public static RegistryObject<Item> RED_BAUBLE = registerAdventItem(7, "red_bauble", () -> new BaubleBlockItem(DMABlocks.RED_BAUBLE_BLOCK));
	public static RegistryObject<Item> CHRISTMAS_CRACKER = registerAdventItem(10, "christmas_cracker", () -> new ChristmasCrackerBlockItem(DMABlocks.CHRISTMAS_CRACKER));

	public static RegistryObject<Item> HANDLES = registerAdventItem(1, "handles",
			() -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item>	BLUE_CANDY_CANE = ITEMS.register("blue_candy_cane",
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


	public static RegistryObject<Item> SNOWMAN_SPAWNER = ITEMS.register("snowman_spawner",
		() -> new DMASpawnerItem<>("snowman", new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> UNIT_GUN = ITEMS.register("unit_gun",
			() -> new GunItem(DMItemTiers.DALEK_GUNSTICK, 0.1F, DMAProjectiles.BULLET, null, DMASoundEvents.PISTOL_SHOOT,
				(new Item.Properties().durability(500)).tab(ItemGroup.TAB_COMBAT)));

	public static RegistryObject<Item> TORCHWOOD_PISTOL = ITEMS.register("torchwood_pistol",
			() -> new GunItem(DMItemTiers.DALEK_GUNSTICK, 0.1F, DMAProjectiles.METALLIC_GOLD_LASER, null, DMASoundEvents.PISTOL_SHOOT,
				(new Item.Properties().durability(1000)).tab(ItemGroup.TAB_COMBAT)));


	public static RegistryObject<Item> SANTA_HAT = ITEMS.register("santa_hat",
			() -> new ClothesItem(EquipmentSlotType.HEAD));

	public static RegistryObject<Item> CHRISTMAS_HAT = registerAdventItem(10,"christmas_hat", ChristmasHatItem::new);

	public static RegistryObject<Item> JOKE = registerAdventItem(10, "joke", JokeItem::new);
	public static RegistryObject<Item> WEDDING_DRESS = registerAdventItem(9, "wedding_dress",
		() -> new ArmorItem(DMAArmorMaterial.WEDDING_DRESS, EquipmentSlotType.LEGS, new Item.Properties().tab(DMTabs.DM_CLOTHES)) {
			@Override
			@OnlyIn(Dist.CLIENT)
			public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
				return (A) new WeddingDressModel(1f);
			}

			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
				return new ResourceLocation(DmAdditions.MODID, "textures/models/armor/wedding_dress.png").toString();
			}

			@Override
			public boolean canEquip(ItemStack stack, EquipmentSlotType armorType, Entity entity) {
				if(armorType == EquipmentSlotType.LEGS) return true;
				return super.canEquip(stack, armorType, entity);
			}
		}
	);
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

		public static RegistryObject<Item> WOODEN_CYBERMAN_SPAWNER = ITEMS.register("wooden_cyberman_spawner",
			() -> new DMASpawnerItem<>("wooden_cyberman", new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> PILOT_FISH_SPAWNER = ITEMS.register("pilot_fish_spawner",
		() -> new DMASpawnerItem<>("pilot_fish", new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> PILOT_FISH_TRUMPET = ITEMS.register("pilot_fish_trumpet",
			() -> new GunItem(DMItemTiers.DALEK_CANNON, 2.0F,
							  DMProjectiles.EXPLOSIVE_LASER, DMSoundEvents.ENTITY_DALEK_CANNON_CHARGE,
							  DMSoundEvents.ENTITY_DALEK_CANNON_SHOOT, (new Item.Properties()).tab(ItemGroup.TAB_COMBAT)));

	public static RegistryObject<Item> FLYING_SHARK_SPAWNER = registerAdventItem(3, "flying_shark_spawner", () -> new ForgeSpawnEggItem(DMAEntities.FLYING_SHARK::get, 0x004A5B, 0xffffff, new Item.Properties().tab(ItemGroup.TAB_MISC))); // TODO once someone makes a spawner texture, replace this with a DMASpawnerItem instead of a ForgeSpawnEggItem

	public static RegistryObject<Item> RACNOSS_SPAWNER = registerAdventItem(11, "racnoss_spawner", () -> new ForgeSpawnEggItem(DMAEntities.RACNOSS::get, 0xa61911, 0x0, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> CHRISTMAS_CREEPER_SPAWNER = registerAdventItem(12, "christmas_creeper_spawner", 
			() -> new ForgeSpawnEggItem(DMAEntities.CHRISTMAS_CREEPER::get, 0xda70b, 0x0, new Item.Properties().tab(ItemGroup.TAB_MISC)));

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
}
