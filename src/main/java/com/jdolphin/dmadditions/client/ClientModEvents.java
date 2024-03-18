package com.jdolphin.dmadditions.client;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.client.init.DMAEntityRenderRegistry;
import com.jdolphin.dmadditions.init.DMAEntities;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(
	modid = DmAdditions.MODID,
	bus = Mod.EventBusSubscriber.Bus.MOD,
	value = {Dist.CLIENT}
)
public class ClientModEvents {
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) { //Im lazy and cba to write the whole line out each time - Jam
		DMAEntityRenderRegistry.registryEntityRenders();
		Helper.registerControlRenderers(DMAEntities.CONTROL.get(), DMAEntities.FLIGHT_CONTROL.get(),
			DMAEntities.DOOR_CONTROL.get());
	}
}
