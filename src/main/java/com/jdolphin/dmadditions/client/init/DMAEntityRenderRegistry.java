package com.jdolphin.dmadditions.client.init;

import com.jdolphin.dmadditions.client.render.entity.RenderPilotFish;
import com.jdolphin.dmadditions.client.render.entity.WoodenCybermanRenderer;
import com.jdolphin.dmadditions.init.DMAEntities;
import com.swdteam.main.DalekMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class DMAEntityRenderRegistry {
	public DMAEntityRenderRegistry() {
	}
	public static void registryEntityRenders() {
		DalekMod.LOGGER.info("Registering DMA Entity Renders");
		registerRender(DMAEntities.WOODEN_CYBERMAN_ENTITY.get(), WoodenCybermanRenderer::new);

		registerRender(DMAEntities.PILOT_FISH.get(), RenderPilotFish::new);
	}

	public static <T extends Entity> void registerRender(EntityType<T> entityClass, IRenderFactory<? super T> renderFactory) {
		RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
	}
}
