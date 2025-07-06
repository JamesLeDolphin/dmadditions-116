package com.jdolphin.dmadditions.client.event;

import com.jdolphin.dmadditions.DMAdditions;
import com.jdolphin.dmadditions.client.init.DMAEntityRenderRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(
	modid = DMAdditions.MODID,
	bus = Mod.EventBusSubscriber.Bus.MOD,
	value = {Dist.CLIENT}
)
public class ClientModEvents {
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		DMAEntityRenderRegistry.registerEntityRenderers();
	}
}
