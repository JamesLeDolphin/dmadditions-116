package com.jdolphin.dmadditions.items;

import com.jdolphin.dmadditions.DmAdditions;
import com.swdteam.common.RegistryHandler;
import com.swdteam.common.init.DMItemTiers;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.item.LasergunItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public class ItemInit {


    public static RegistryObject<Item> PISTOL = RegistryHandler.ITEMS.register("pistol", () -> new LasergunItem(DMItemTiers.DALEK_GUNSTICK, 0.0F, DMProjectiles.GREEN_LASER, DMSoundEvents.ENTITY_DALEK_GUNSTICK_CHARGE, DMSoundEvents.ENTITY_DALEK_GUNSTICK_SHOOT, (new Item.Properties()).tab(ItemGroup.TAB_COMBAT)));


}
