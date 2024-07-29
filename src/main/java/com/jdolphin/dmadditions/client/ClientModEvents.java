package com.jdolphin.dmadditions.client;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.client.init.DMAEntityRenderRegistry;
import com.jdolphin.dmadditions.client.render.tileentity.control.TardisControlRenderer;
import com.jdolphin.dmadditions.init.DMAEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(
	modid = DmAdditions.MODID,
	bus = Mod.EventBusSubscriber.Bus.MOD,
	value = {Dist.CLIENT}
)
public class ClientModEvents {
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		DMAEntityRenderRegistry.registryEntityRenders();
		RenderingRegistry.registerEntityRenderingHandler(DMAEntities.CONTROL.get(), TardisControlRenderer::new);
	}
}
