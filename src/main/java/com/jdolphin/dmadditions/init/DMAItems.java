package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.client.model.armor.MattsPinkThongModel;
import com.jdolphin.dmadditions.client.model.armor.ScarfModel;
import com.jdolphin.dmadditions.client.model.armor.WeddingDressModel;
import com.jdolphin.dmadditions.item.*;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.common.init.*;
import com.swdteam.common.item.ClothesItem;
import com.swdteam.common.item.DiscItem;
import com.swdteam.common.item.FoodItem;
import com.swdteam.common.item.gun.SingleShotGunItem;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

@SuppressWarnings({"unchecked", "unused"})
public class DMAItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DmAdditions.MODID);

	protected static RegistryObject<Item> addAdventItem(int day, String name, Supplier<Item> supplier) {
		if (!AdventUnlock.unlockAt(day)) return null;

		return ITEMS.register(name, supplier);
	}

	//public static RegistryObject<Item> SANTA_BAUBLE = ITEMS.register(7, "santa_bauble", () -> new BaubleBlockItem(DMABlocks.SANTA_BAUBLE_BLOCK));
	public static RegistryObject<Item> BLUE_BAUBLE = ITEMS.register("blue_bauble", () -> new BaubleBlockItem(DMABlocks.BLUE_BAUBLE_BLOCK));
	public static RegistryObject<Item> GOLD_BAUBLE = ITEMS.register("gold_bauble", () -> new BaubleBlockItem(DMABlocks.GOLD_BAUBLE_BLOCK));
	public static RegistryObject<Item> GREEN_BAUBLE = ITEMS.register("green_bauble", () -> new BaubleBlockItem(DMABlocks.GREEN_BAUBLE_BLOCK));
	public static RegistryObject<Item> RED_BAUBLE = ITEMS.register("red_bauble", () -> new BaubleBlockItem(DMABlocks.RED_BAUBLE_BLOCK));
	public static RegistryObject<Item> CHRISTMAS_CRACKER = ITEMS.register("christmas_cracker", () -> new ChristmasCrackerBlockItem(DMABlocks.CHRISTMAS_CRACKER));

	public static RegistryObject<Item> BIO_DAMPNER = ITEMS.register("bio_dampner",
		() -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> ELIXIR_OF_LIFE = ITEMS.register("elixir_of_life",
		() -> new ElixirOfLifeItem(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1)));

	public static RegistryObject<Item> FOB_WATCH = ITEMS.register("fob_watch",
		() -> new FobWatchItem(new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> HANDLES = ITEMS.register("handles",
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
			() -> new SingleShotGunItem(DMItemTiers.DALEK_GUNSTICK, 0.1F, 5, DMAProjectiles.BULLET, DMSoundEvents.ITEM_GUN_CLICK, DMASoundEvents.PISTOL_SHOOT,
				(new Item.Properties().durability(500)).tab(ItemGroup.TAB_COMBAT), DMItems.BULLET, DMItems.GOLD_BULLET, DMItems.DARK_STAR_BULLET));

	public static RegistryObject<Item> TORCHWOOD_PISTOL = ITEMS.register("torchwood_pistol",
			() -> new SingleShotGunItem(DMItemTiers.DALEK_GUNSTICK, 0.1F, 5, DMAProjectiles.METALLIC_GOLD_LASER, DMSoundEvents.ITEM_GUN_CLICK, DMASoundEvents.PISTOL_SHOOT,
				(new Item.Properties().durability(1000)).tab(ItemGroup.TAB_COMBAT), DMItems.BULLET, DMItems.GOLD_BULLET, DMItems.DARK_STAR_BULLET));


	public static RegistryObject<Item> SANTA_HAT = ITEMS.register("santa_hat",
			() -> new ClothesItem(EquipmentSlotType.HEAD));

	public static RegistryObject<Item> CHRISTMAS_HAT = ITEMS.register("christmas_hat", ChristmasHatItem::new);

	public static RegistryObject<Item> JOKE = ITEMS.register("joke", JokeItem::new);

	public static RegistryObject<Item> TOP_HAT = ITEMS.register("top_hat", () -> new ClothesItem(EquipmentSlotType.HEAD));

	public static RegistryObject<Item> WEDDING_DRESS = ITEMS.register("wedding_dress",
		() -> new ArmorItem(DMAArmorMaterial.WEDDING_DRESS, EquipmentSlotType.LEGS, new Item.Properties().tab(DMTabs.DM_CLOTHES)) {
			@Override
			@OnlyIn(Dist.CLIENT)
			public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
				return (A) new WeddingDressModel(1f);
			}

			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
				return Helper.createAdditionsRL("textures/models/armor/wedding_dress.png").toString();
			}

			@Override
			public boolean canEquip(ItemStack stack, EquipmentSlotType armorType, Entity entity) {
				if(armorType == EquipmentSlotType.LEGS) return true;
				return super.canEquip(stack, armorType, entity);
			}
		}
	);

	public static RegistryObject<Item> SCARF_RED = ITEMS.register("scarf_red",
		() -> new ArmorItem(DMAArmorMaterial.SCARF, EquipmentSlotType.CHEST, new Item.Properties().tab(DMTabs.DM_CLOTHES)) {
			@Override
			@OnlyIn(Dist.CLIENT)
			public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
				return (A) new ScarfModel(1f);
			}

			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
				return Helper.createAdditionsRL("textures/models/armor/scarf_red.png").toString();
			}

			@Override
			public boolean canEquip(ItemStack stack, EquipmentSlotType armorType, Entity entity) {
				if(armorType == EquipmentSlotType.CHEST) return true;
				return super.canEquip(stack, armorType, entity);
			}
		}
	);

	public static RegistryObject<Item> SCARF_BROWN = ITEMS.register("scarf_brown",
		() -> new ArmorItem(DMAArmorMaterial.SCARF, EquipmentSlotType.CHEST, new Item.Properties().tab(DMTabs.DM_CLOTHES)) {
			@Override
			@OnlyIn(Dist.CLIENT)
			public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
				return (A) new ScarfModel(1f);
			}

			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
				return Helper.createAdditionsRL("textures/models/armor/scarf_brown.png").toString();
			}

			@Override
			public boolean canEquip(ItemStack stack, EquipmentSlotType armorType, Entity entity) {
				if(armorType == EquipmentSlotType.CHEST) return true;
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
					return Helper.createAdditionsRL("textures/models/armor/matts_pink_thong.png").toString();
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

	public static RegistryObject<Item> MONDAS_CYBERMAN_SPAWNER = ITEMS.register("mondas_cyberman_spawner",
		() -> new DMASpawnerItem<>("mondas_cyberman", new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> PILOT_FISH_SPAWNER = ITEMS.register("pilot_fish_spawner",
		() -> new DMASpawnerItem<>("pilot_fish", new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> PILOT_FISH_TRUMPET = ITEMS.register("pilot_fish_trumpet",
			() -> new SingleShotGunItem(DMItemTiers.DALEK_CANNON, 2.0F, 3.0f,
							  DMProjectiles.EXPLOSIVE_LASER, DMSoundEvents.ENTITY_DALEK_CANNON_CHARGE,
							  DMSoundEvents.ENTITY_DALEK_CANNON_SHOOT, (new Item.Properties()).tab(ItemGroup.TAB_COMBAT)));

	public static RegistryObject<Item> FLYING_SHARK_SPAWNER = ITEMS.register("flying_shark_spawner", () -> new ForgeSpawnEggItem(DMAEntities.FLYING_SHARK::get, 0x004A5B, 0xffffff, new Item.Properties().tab(ItemGroup.TAB_MISC))); // TODO once someone makes a spawner texture, replace this with a DMASpawnerItem instead of a ForgeSpawnEggItem

	public static RegistryObject<Item> RACNOSS_SPAWNER = ITEMS.register("racnoss_spawner", () -> new ForgeSpawnEggItem(DMAEntities.RACNOSS::get, 0xa61911, 0x0, new Item.Properties().tab(ItemGroup.TAB_MISC))); //TODO texture

	public static RegistryObject<Item> SYCORAX_STAFF = ITEMS.register("sycorax_staff", () -> new SycoraxStaffItem(new Item.Properties().tab(ItemGroup.TAB_COMBAT).stacksTo(1)));

	public static RegistryObject<Item> CHRISTMAS_CREEPER_SPAWNER = ITEMS.register("christmas_creeper_spawner",
			() -> new ForgeSpawnEggItem(DMAEntities.CHRISTMAS_CREEPER::get, 0x0da70b, 0x0, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> JIM_SPAWNER = ITEMS.register("jim_spawner",
			() -> new ForgeSpawnEggItem(DMAEntities.JIM::get, 0x864b0f, 0x624c37, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> WHISPERMAN_SPAWNER = ITEMS.register("whisperman_spawner",
			() -> new ForgeSpawnEggItem(DMAEntities.WHISPERMAN::get, 0x8d8a87, 0x0, new Item.Properties().tab(ItemGroup.TAB_MISC))); //TODO texture
	public static RegistryObject<Item> KANTROFARRI_SPAWNER = ITEMS.register("kantrofarri_spawner",
		() -> new ForgeSpawnEggItem(DMAEntities.KANTROFARRI::get, 0x657fad, 0xd9cacd, new Item.Properties().tab(ItemGroup.TAB_MISC)));
	public static RegistryObject<Item> KANTROFARRI = ITEMS.register("kantrofarri",
		() -> new FoodItem(new Item.Properties().food(DMAFoods.KANTROFARRI).tab(ItemGroup.TAB_FOOD)));
	public static RegistryObject<Item> KANTROFARRI_COOKED = ITEMS.register("kantrofarri_cooked",
		() -> new FoodItem(new Item.Properties().food(DMAFoods.KANTROFARRI_COOKED).tab(ItemGroup.TAB_FOOD)));
	public static RegistryObject<Item> SHOPPING_CART = ITEMS.register("shopping_cart",
		() -> new DMASpawnerItem<>("shopping_cart", new Item.Properties().tab(ItemGroup.TAB_TRANSPORTATION))); // TODO: texture

	public static RegistryObject<Item> TORCHWOOD_TANK = ITEMS.register("torchwood_tank",
		() -> new DMASpawnerItem<>("torchwood_tank", new Item.Properties().tab(ItemGroup.TAB_TRANSPORTATION))); // TODO: texture

	public static RegistryObject<Item> BESSIE = ITEMS.register("bessie", () -> new ForgeSpawnEggItem(DMAEntities.BESSIE::get,
			0, 0, new Item.Properties().tab(ItemGroup.TAB_TRANSPORTATION)));

	public static RegistryObject<Item> TW_SUV = ITEMS.register("torchwood_suv", () -> new ForgeSpawnEggItem(DMAEntities.TW_SUV::get,
			0, 0, new Item.Properties().tab(ItemGroup.TAB_TRANSPORTATION)));

	public static RegistryObject<Item> RPG = ITEMS.register("rpg",
			() -> new SingleShotGunItem(DMItemTiers.DALEK_CANNON, 0.1F, 5, DMAProjectiles.EXPLOSIVE_LASER, DMSoundEvents.ENTITY_DALEK_CANNON_CHARGE,
				DMSoundEvents.ENTITY_DALEK_CANNON_SHOOT, (new Item.Properties().durability(1000)).tab(ItemGroup.TAB_COMBAT)));

	public static RegistryObject<Item> PISTOL =  ITEMS.register("pistol", ()
			-> new SingleShotGunItem(DMItemTiers.DALEK_GUNSTICK, 0.15F, 5, DMAProjectiles.PURPLE_LASER, DMSoundEvents.ITEM_GUN_CLICK,
			DMASoundEvents.PISTOL_SHOOT, (new Item.Properties().durability(100)).tab(ItemGroup.TAB_COMBAT)));

	public static RegistryObject<Item> TARDIS_GOLD_KEY =  ITEMS.register("tardis_gold_key",
			() -> new TardisRemoteKeyItem((new Item.Properties()).durability(32).tab(DMTabs.DM_TARDIS), ""));

	public static RegistryObject<Item> DINO_NUGGETS =  ITEMS.register("dino_nuggets",
			() -> new FoodItem((new Item.Properties()).food(DMAFoods.DINO_NUGGETS).tab(ItemGroup.TAB_FOOD)));

	public static RegistryObject<Item> DINO_NUGGETS_CUSTARD =  ITEMS.register("dino_nuggets_custard",
			() -> new FoodItem((new Item.Properties()).food(DMAFoods.DINO_NUGGETS_CUSTARD).tab(ItemGroup.TAB_FOOD)));
}
