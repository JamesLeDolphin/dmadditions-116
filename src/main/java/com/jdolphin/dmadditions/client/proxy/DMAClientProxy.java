package com.jdolphin.dmadditions.client.proxy;

import com.jdolphin.dmadditions.client.init.ClientRenderer;
import com.jdolphin.dmadditions.client.init.DMATileRenderRegistry;
import com.swdteam.client.init.BusClientEvents;
import com.swdteam.main.proxy.ServerProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class DMAClientProxy extends DMAServerProxy {
	public void doClientStuff(FMLClientSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(ClientRenderer.class);
	}
	public static void registerReloadable() {
		DMATileRenderRegistry.init();
	}
}
