package com.jdolphin.dmadditions.event;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.item.ChristmasHatItem;
import com.jdolphin.dmadditions.loot.modifiers.AddItemLootModifier;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = DmAdditions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMAEventBusEvents {
	@SubscribeEvent
	public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event){
		event.getRegistry().registerAll(
			new AddItemLootModifier.Serializer().setRegistryName(Helper.createAdditionsRL("add_item"))
		);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerItemColor(ColorHandlerEvent.Item event) {
		if(DMAItems.CHRISTMAS_HAT != null) event.getItemColors().register(ChristmasHatItem::itemColor, DMAItems.CHRISTMAS_HAT.get());
	}
}
