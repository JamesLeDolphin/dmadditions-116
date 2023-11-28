package com.jdolphin.dmadditions.client.init;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.client.render.entity.BessieRenderer;
import com.jdolphin.dmadditions.client.render.entity.ChristmasTreeRenderer;
import com.jdolphin.dmadditions.client.render.entity.FlyingSharkRenderer;
import com.jdolphin.dmadditions.client.render.entity.JamesLeDolphinRenderer;
import com.jdolphin.dmadditions.client.render.entity.RacnossRenderer;
import com.jdolphin.dmadditions.client.render.entity.RenderPilotFish;
import com.jdolphin.dmadditions.client.render.entity.RenderSnowman;
import com.jdolphin.dmadditions.client.render.entity.TorchwoodSuvRenderer;
import com.jdolphin.dmadditions.client.render.entity.WoodenCybermanRenderer;
import com.jdolphin.dmadditions.init.DMAEntities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class DMAEntityRenderRegistry {
	public DMAEntityRenderRegistry() {
	}

	@SuppressWarnings({"rawtype", "unchecked"})
	public static void registryEntityRenders() {
		DmAdditions.LOGGER.info("Registering DMA Entity Renders");

		registerRender(DMAEntities.JAMESLEDOLPHIN, JamesLeDolphinRenderer::new);
		registerRender(DMAEntities.WOODEN_CYBERMAN, WoodenCybermanRenderer::new);
		registerRender(DMAEntities.PILOT_FISH, RenderPilotFish::new);
		registerRender(DMAEntities.SNOWMAN, RenderSnowman::new);
		registerRender(DMAEntities.CHRISTMAS_TREE, ChristmasTreeRenderer::new);
		registerRender(DMAEntities.BESSIE, BessieRenderer::new);
		registerRender(DMAEntities.TW_SUV, TorchwoodSuvRenderer::new);
		registerRender((RegistryObject) DMAEntities.FLYING_SHARK, FlyingSharkRenderer::new);
		registerRender((RegistryObject) DMAEntities.RACNOSS, RacnossRenderer::new);
	}



	public static <T extends Entity> void registerRender(RegistryObject<EntityType<T>> entityClass, IRenderFactory<? super T> renderFactory) {
		if (entityClass == null) return;
		RenderingRegistry.registerEntityRenderingHandler(entityClass.get(), renderFactory);
	}
}
