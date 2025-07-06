package com.jdolphin.dmadditions.common.event;

import com.jdolphin.dmadditions.DMAdditions;
import com.jdolphin.dmadditions.common.loot.modifiers.AddItemLootModifier;
import com.jdolphin.dmadditions.common.util.Helper;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = DMAdditions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMAEventBusEvents {
	@SubscribeEvent
	public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
		event.getRegistry().registerAll(
			new AddItemLootModifier.Serializer().setRegistryName(Helper.createAdditionsRL("add_item"))
		);
	}
}
