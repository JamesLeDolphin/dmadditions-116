package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.items.TardisGoldKeyItem;
import com.swdteam.common.RegistryHandler;
import com.swdteam.common.init.*;
import com.swdteam.common.item.FoodItem;
import com.swdteam.common.item.LasergunItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import net.minecraftforge.fml.RegistryObject;


public class DMAdditionsItems {

	public static RegistryObject<Item> DINO_NUGGETS;
	public static RegistryObject<Item> DINO_NUGGETS_CUSTARD;
	public static RegistryObject<Item> PISTOL;
	public static RegistryObject<Item> TARDIS_GOLD_KEY;

	static {
		PISTOL = RegistryHandler.ITEMS.register("pistol",
				() -> new LasergunItem(DMItemTiers.DALEK_GUNSTICK, 0.15F, DMAdditionsProjectiles.METALLIC_GOLD_LASER, DMSoundEvents.ENTITY_DALEK_GUNSTICK_CHARGE,
						DMAdditionsSoundEvents.PISTOL_SHOOT, (new Item.Properties()).tab(ItemGroup.TAB_COMBAT)));

		TARDIS_GOLD_KEY = RegistryHandler.ITEMS.register("tardis_gold_key",
				() -> new TardisGoldKeyItem((new Item.Properties()).tab(DMTabs.DM_TARDIS), ""));

		DINO_NUGGETS = RegistryHandler.ITEMS.register("dino_nuggets",
				() -> new FoodItem((new Item.Properties()).food(DMAdditionsFoods.DINO_NUGGETS).tab(ItemGroup.TAB_FOOD)));

		DINO_NUGGETS_CUSTARD = RegistryHandler.ITEMS.register("dino_nuggets_custard",
				() -> new FoodItem((new Item.Properties()).food(DMAdditionsFoods.DINO_NUGGETS_CUSTARD).tab(ItemGroup.TAB_FOOD)));
	}
}
