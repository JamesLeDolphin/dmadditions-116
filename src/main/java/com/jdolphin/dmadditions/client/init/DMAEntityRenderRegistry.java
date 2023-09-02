package com.jdolphin.dmadditions.client.init;

import com.jdolphin.dmadditions.client.render.entity.*;
import com.jdolphin.dmadditions.init.DMAEntities;
import com.swdteam.main.DalekMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class DMAEntityRenderRegistry {
	public DMAEntityRenderRegistry() {
	}

	public static void registryEntityRenders() {
		DalekMod.LOGGER.info("Registering DMA Entity Renders");

		registerRender(DMAEntities.JAMESLEDOLPHIN, JamesLeDolphinRenderer::new);
		registerRender(DMAEntities.WOODEN_CYBERMAN, WoodenCybermanRenderer::new);
		registerRender(DMAEntities.PILOT_FISH, RenderPilotFish::new);
		registerRender(DMAEntities.SNOWMAN, RenderSnowman::new);
		registerRender(DMAEntities.CHRISTMAS_TREE, ChristmasTreeRenderer::new);
		registerRender(DMAEntities.BESSIE, BessieRenderer::new);
		registerRender(DMAEntities.TW_SUV, TorchwoodSuvRenderer::new);
		registerRender(DMAEntities.BEATRICE_FLYING_SHARK, BeatriceFlyingSharkRenderer::new);
	}



	public static <T extends Entity> void registerRender(RegistryObject<EntityType<T>> entityClass, IRenderFactory<? super T> renderFactory) {
		if (entityClass == null) return;
		RenderingRegistry.registerEntityRenderingHandler(entityClass.get(), renderFactory);
	}
}
