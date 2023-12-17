package com.jdolphin.dmadditions.event;

import javax.annotation.Nonnull;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.client.DMAColorHandler;
import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.item.ChristmasHatItem;
import com.jdolphin.dmadditions.loot.modifiers.AddItemLootModifier;

import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DmAdditions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMAEventBusEvents {
	@SubscribeEvent
	public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event){
		event.getRegistry().registerAll(
			new AddItemLootModifier.Serializer().setRegistryName(new ResourceLocation(DmAdditions.MODID, "add_item"))
		);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerItemColor(ColorHandlerEvent.Item event) {
		if(DMAItems.CHRISTMAS_HAT != null) event.getItemColors().register(ChristmasHatItem::itemColor, DMAItems.CHRISTMAS_HAT.get());
	}
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void renderEvent(RenderLivingEvent event) {
		if (event.getEntity() instanceof VillagerEntity) {
			VillagerRenderer renderer = (VillagerRenderer) event.getRenderer();
			//renderer.addLayer();
		}
	}
}
