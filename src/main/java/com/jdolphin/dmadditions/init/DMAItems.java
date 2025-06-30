package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DMAdditions;
import com.jdolphin.dmadditions.client.model.armor.MattsPinkThongModel;
import com.jdolphin.dmadditions.client.model.armor.WeddingDressModel;
import com.jdolphin.dmadditions.entity.dalek.types.DMADalekType;
import com.jdolphin.dmadditions.item.*;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.common.entity.dalek.DalekType;
import com.swdteam.common.init.*;
import com.swdteam.common.item.*;
import com.swdteam.common.item.gun.SingleShotGunItem;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.List;

@SuppressWarnings({"unchecked", "unused"})
public class DMAItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DMAdditions.MODID);

	public static final RegistryObject<Item> DOCS_FLUTE = ITEMS.register("docs_flute", () -> new FluteItem(new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static final RegistryObject<Item> SONIC_BLASTER = ITEMS.register("sonic_blaster",
		() -> new SonicBlasterItem(new Item.Properties().tab(ItemGroup.TAB_COMBAT)));

	public static RegistryObject<Item> BLUE_BAUBLE = ITEMS.register("blue_bauble",
		() -> new BaubleBlockItem(DMABlocks.BLUE_BAUBLE_BLOCK));
	public static RegistryObject<Item> GOLD_BAUBLE = ITEMS.register("gold_bauble",
		() -> new BaubleBlockItem(DMABlocks.GOLD_BAUBLE_BLOCK));
	public static RegistryObject<Item> GREEN_BAUBLE = ITEMS.register("green_bauble",
		() -> new BaubleBlockItem(DMABlocks.GREEN_BAUBLE_BLOCK));
	public static RegistryObject<Item> RED_BAUBLE = ITEMS.register("red_bauble",
		() -> new BaubleBlockItem(DMABlocks.RED_BAUBLE_BLOCK));

	public static RegistryObject<Item> BIO_DAMPNER = ITEMS.register("bio_dampner",
		() -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));

	//public static RegistryObject<Item> ELIXIR_OF_LIFE = ITEMS.register("elixir_of_life",
	//	() -> new ElixirOfLifeItem(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1)));

	//public static RegistryObject<Item> FOB_WATCH = ITEMS.register("fob_watch",
	//	() -> new FobWatchItem(new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> MONDASIAN_SPAWNER = ITEMS.register("mondasian_spawner",
		() -> new DMASpawnerItem<>(DMAEntities.MONDASIAN, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> MONDAS_DATA_CARD = ITEMS.register("mondas_data_card", () ->
		new DimensionDataCard((new Item.Properties()).tab(ItemGroup.TAB_MISC).stacksTo(1).tab(DMTabs.DM_TARDIS), "dmadditions:mondas", TextFormatting.AQUA));

	public static RegistryObject<Item> GALLIFREY_DATA_CARD = ITEMS.register("gallifrey_data_card", () ->
		new DimensionDataCard((new Item.Properties()).tab(ItemGroup.TAB_MISC).stacksTo(1).tab(DMTabs.DM_TARDIS), "dmadditions:gallifrey", TextFormatting.DARK_RED));

	public static RegistryObject<Item> SPECIMEN_JAR = ITEMS.register("specimen_jar",
		() -> new SpecimenJarBlockItem(new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<BulletItem> LASER_CHARGE = ITEMS.register("laser_charge",
		() -> new BulletItem(new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> EARTHSHOCK_GUN = ITEMS.register("earthshock_gun",
		() -> new SingleShotGunItem(DMItemTiers.STEEL, 1, 7,
			DMProjectiles.BLUE_LASER, DMSoundEvents.ENTITY_DALEK_GUNSTICK_CHARGE, DMASoundEvents.EARTHSHOCK_GUN_SHOOT, new Item.Properties().tab(ItemGroup.TAB_COMBAT).durability(150)));

	public static RegistryObject<Item> HANDLES = ITEMS.register("handles",
		() -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1)));

	public static RegistryObject<Item> NETHERITE_CYBERMAN_SPAWNER = ITEMS.register("netherite_cyberman_spawner",
		() -> new DMASpawnerItem<>(DMAEntities.NETHERITE_CYBERMAN, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> CYBER_WARRIOR_SPAWNER = ITEMS.register("cyber_warrior_spawner",
		() -> new DMASpawnerItem<>(DMAEntities.WARRIOR_CYBERMAN, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> TOMB_CYBERMAN = ITEMS.register("tomb_cyberman_spawner",
		() -> new DMASpawnerItem<>(DMAEntities.TOMB_CYBERMAN, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> TOMB_CYBER_CONTROLLER= ITEMS.register("tomb_cyber_controller_spawner",
		() -> new DMASpawnerItem<>(DMAEntities.TOMB_CYBER_CONTROLLER, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> INVASION_CYBERMAN = ITEMS.register("invasion_cyberman_spawner",
		() -> new DMASpawnerItem<>(DMAEntities.INVASION_CYBERMAN, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> EMPTY_CHILD_SPAWNER = ITEMS.register("empty_child_spawner",
		() -> new DMASpawnerItem<>(DMAEntities.EMPTY_CHILD, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> EMPTY_VILLAGER_SPAWNER = ITEMS.register("empty_villager_spawner",
		() -> new DMASpawnerItem<>(DMAEntities.EMPTY_VILLAGER, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> TIMELORD_SPAWNER = ITEMS.register("timelord_spawner",
		() -> new DMASpawnerItem<>(DMAEntities.TIMELORD, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> SNOWMAN_SPAWNER = ITEMS.register("snowman_spawner",
		() -> new DMASpawnerItem<>(DMAEntities.SNOWMAN, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> CYBERCOW_SPAWNER = ITEMS.register("cybercow_spawner",
		() -> new DMASpawnerItem<>(DMAEntities.CYBERCOW, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> UNIT_GUN = ITEMS.register("unit_gun",
		() -> new SingleShotGunItem(DMItemTiers.DALEK_GUNSTICK, 0.1F, 5, DMAProjectiles.BULLET, DMSoundEvents.ITEM_GUN_CLICK, DMASoundEvents.PISTOL_SHOOT,
			(new Item.Properties().durability(500)).tab(ItemGroup.TAB_COMBAT), DMItems.BULLET, DMItems.GOLD_BULLET, DMItems.DARK_STAR_BULLET));

	public static RegistryObject<Item> TORCHWOOD_PISTOL = ITEMS.register("torchwood_pistol",
		() -> new SingleShotGunItem(DMItemTiers.DALEK_GUNSTICK, 0.1F, 5, DMAProjectiles.METALLIC_GOLD_LASER, DMSoundEvents.ITEM_GUN_CLICK, DMASoundEvents.PISTOL_SHOOT,
			(new Item.Properties().durability(1000)).tab(ItemGroup.TAB_COMBAT), DMItems.BULLET, DMItems.GOLD_BULLET, DMItems.DARK_STAR_BULLET));

	public static RegistryObject<Item> FRYING_PAN = ITEMS.register("frying_pan",
		() -> new FryingPanItem(2, -2.6f, ItemTier.NETHERITE, new HashSet<>(), new Item.Properties().tab(ItemGroup.TAB_COMBAT).rarity(Rarity.UNCOMMON)));

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
				if (armorType == EquipmentSlotType.LEGS) return true;
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

	public static RegistryObject<Item> LASER_SCREWDRIVER = ITEMS.register("laser_screwdriver",
		() -> new LaserScrewdriverItem(ItemGroup.TAB_TOOLS, 100, DMAProjectiles.METALLIC_GOLD_LASER));

	public static RegistryObject<Item> MUSIC_DISC_PFD = ITEMS.register("music_disc_pfd",
		() -> new DiscItem(5, DMASoundEvents.MUSIC_DISC_PFD, (new Item.Properties()).rarity(Rarity.RARE).tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> WOODEN_CYBERMAN_SPAWNER = ITEMS.register("wooden_cyberman_spawner",
		() -> new DMASpawnerItem<>(DMAEntities.WOODEN_CYBERMAN, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> MONDAS_CYBERMAN_SPAWNER = ITEMS.register("mondas_cyberman_spawner",
		() -> new DMASpawnerItem<>(DMAEntities.MONDAS_CYBERMAN, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> PILOT_FISH_TRUMPET = ITEMS.register("pilot_fish_trumpet",
		() -> new SingleShotGunItem(DMItemTiers.DALEK_CANNON, 2.0F, 3.0f,
			DMProjectiles.EXPLOSIVE_LASER, DMSoundEvents.ENTITY_DALEK_CANNON_CHARGE,
			DMSoundEvents.ENTITY_DALEK_CANNON_SHOOT, (new Item.Properties()).tab(ItemGroup.TAB_COMBAT)));

	public static RegistryObject<Item> FLYING_SHARK_SPAWNER = ITEMS.register("flying_shark_spawner",
		() -> new ForgeSpawnEggItem(DMAEntities.FLYING_SHARK, 0x004A5B, 0xffffff, new Item.Properties().tab(ItemGroup.TAB_MISC))); // TODO once someone makes a spawner texture, replace this with a DMASpawnerItem instead of a ForgeSpawnEggItem

	public static RegistryObject<Item> RACNOSS_SPAWNER = ITEMS.register("racnoss_spawner",
		() -> new ForgeSpawnEggItem(DMAEntities.RACNOSS, 0xa61911, 0x0, new Item.Properties().tab(ItemGroup.TAB_MISC))); //TODO texture

	public static RegistryObject<Item> SYCORAX_STAFF = ITEMS.register("sycorax_staff", () -> new SycoraxStaffItem(new Item.Properties().tab(ItemGroup.TAB_COMBAT).stacksTo(1)));

	public static RegistryObject<Item> WHISPERMAN_SPAWNER = ITEMS.register("whisperman_spawner",
		() -> new ForgeSpawnEggItem(DMAEntities.WHISPERMAN, 0x8d8a87, 0x0, new Item.Properties().tab(ItemGroup.TAB_MISC))); //TODO texture
	public static RegistryObject<Item> KANTROFARRI_SPAWNER = ITEMS.register("kantrofarri_spawner",
		() -> new ForgeSpawnEggItem(DMAEntities.KANTROFARRI, 0x657fad, 0xd9cacd, new Item.Properties().tab(ItemGroup.TAB_MISC)));
	public static RegistryObject<Item> KANTROFARRI = ITEMS.register("kantrofarri",
		() -> new FoodItem(new Item.Properties().food(DMAFoods.KANTROFARRI).tab(ItemGroup.TAB_FOOD)));

	public static RegistryObject<Item> KANTROFARRI_COOKED = ITEMS.register("kantrofarri_cooked",
		() -> new FoodItem(new Item.Properties().food(DMAFoods.KANTROFARRI_COOKED).tab(ItemGroup.TAB_FOOD)));

	public static RegistryObject<Item> SHOPPING_CART = ITEMS.register("shopping_cart",
		() -> new ShoppingCartItem(new Item.Properties().tab(ItemGroup.TAB_TRANSPORTATION))); // TODO: texture

	public static RegistryObject<Item> CYBER_PIGLIN_SPAWNER = ITEMS.register("cyber_piglin_spawner",
		() -> new DMASpawnerItem<>(DMAEntities.CYBER_PIGLIN, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> ZYGON_SPAWNER = ITEMS.register("zygon_spawner",
		() -> new DMASpawnerItem<>(DMAEntities.ZYGON, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> CLOWCKWORK_SPAWNER = ITEMS.register("clockwork_droid_spawner",
		() -> new DMASpawnerItem<>(DMAEntities.CLOCKWORK_DROID, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> ICE_WARRIOR_SPAWNER = ITEMS.register("ice_warrior_spawner",
		() -> new DMASpawnerItem<>(DMAEntities.ICE_WARRIOR, new Item.Properties().tab(ItemGroup.TAB_MISC)));

	public static RegistryObject<Item> DAVROS_CHAIR = ITEMS.register("davros_chair",
		() -> new DMASpawnerItem<>(DMAEntities.DAVROS_CHAIR, new Item.Properties().tab(ItemGroup.TAB_TRANSPORTATION)));

	public static RegistryObject<Item> BESSIE = ITEMS.register("bessie", () -> new ForgeSpawnEggItem(DMAEntities.BESSIE,
		0, 0, new Item.Properties().tab(ItemGroup.TAB_TRANSPORTATION)));

	public static RegistryObject<Item> TW_SUV = ITEMS.register("torchwood_suv", () -> new ForgeSpawnEggItem(DMAEntities.TW_SUV,
		0, 0, new Item.Properties().tab(ItemGroup.TAB_TRANSPORTATION)));

	public static RegistryObject<Item> RPG = ITEMS.register("rpg",
		() -> new SingleShotGunItem(DMItemTiers.DALEK_CANNON, 0.1F, 5, DMAProjectiles.EXPLOSIVE_LASER, DMSoundEvents.ENTITY_DALEK_CANNON_CHARGE,
			DMSoundEvents.ENTITY_DALEK_CANNON_SHOOT, (new Item.Properties().durability(1000)).tab(ItemGroup.TAB_COMBAT)));

	public static RegistryObject<Item> PISTOL = ITEMS.register("pistol", ()
		-> new SingleShotGunItem(DMItemTiers.DALEK_GUNSTICK, 0.15F, 5, DMAProjectiles.PURPLE_LASER, DMSoundEvents.ITEM_GUN_CLICK,
		DMASoundEvents.PISTOL_SHOOT, (new Item.Properties().durability(100)).tab(ItemGroup.TAB_COMBAT)));

	public static RegistryObject<Item> TARDIS_GOLD_KEY = ITEMS.register("tardis_gold_key",
		() -> new TardisRemoteKeyItem((new Item.Properties()).durability(32).tab(DMTabs.DM_TARDIS)));

	public static RegistryObject<Item> DINO_NUGGETS = ITEMS.register("dino_nuggets",
		() -> new FoodItem((new Item.Properties()).food(DMAFoods.DINO_NUGGETS).tab(ItemGroup.TAB_FOOD)));

	public static RegistryObject<Item> DINO_NUGGETS_CUSTARD = ITEMS.register("dino_nuggets_custard",
		() -> new FoodItem((new Item.Properties()).food(DMAFoods.DINO_NUGGETS_CUSTARD).tab(ItemGroup.TAB_FOOD)));
}
