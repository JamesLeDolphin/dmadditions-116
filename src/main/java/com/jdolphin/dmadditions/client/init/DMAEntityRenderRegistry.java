package com.jdolphin.dmadditions.client.init;

import com.jdolphin.dmadditions.DMAdditions;
import com.jdolphin.dmadditions.client.render.entity.*;
import com.jdolphin.dmadditions.client.render.tileentity.control.TardisControlRenderer;
import com.jdolphin.dmadditions.init.DMAEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class DMAEntityRenderRegistry {

	public static void registerEntityRenderers() {
		DMAdditions.LOGGER.info("Registering DMA Entity Renders");

		registerRender(DMAEntities.JAMESLEDOLPHIN, JamesLeDolphinRenderer::new);
		registerRender(DMAEntities.MONDASIAN, MondasianRenderer::new);
		registerRender(DMAEntities.TIMELORD, TimeLordRenderer::new);
		registerRender(DMAEntities.CYBERCOW, CybercowRenderer::new);
		registerRender(DMAEntities.WOODEN_CYBERMAN, WoodenCybermanRenderer::new);
		registerRender(DMAEntities.MONDAS_CYBERMAN, MondasCybermanRenderer::new);
		registerRender(DMAEntities.SNOWMAN, SnowmanRenderer::new);
		registerRender(DMAEntities.BESSIE, BessieRenderer::new);
		registerRender(DMAEntities.DAVROS_CHAIR, DavrosChairRenderer::new);
		registerRender(DMAEntities.TW_SUV, TorchwoodSuvRenderer::new);
		registerRender(DMAEntities.FLYING_SHARK, FlyingSharkRenderer::new);
		registerRender(DMAEntities.RACNOSS, RacnossRenderer::new);
		registerRender(DMAEntities.SHOPPING_CART, ShoppingCartRenderer::new);
		registerRender(DMAEntities.WHISPERMAN, WhispermanRenderer::new);
		registerRender(DMAEntities.KANTROFARRI, KantrofarriRenderer::new);
		registerRender(DMAEntities.HEROBRINE, HerobrineRenderer::new);
		registerRender(DMAEntities.CLOCKWORK_DROID, ClockworkDroidRenderer::new);
		registerRender(DMAEntities.ICE_WARRIOR, IceWarriorRenderer::new);
		registerRender(DMAEntities.ZYGON, ZygonRenderer::new);
		registerRender(DMAEntities.EMPTY_VILLAGER, EmptyVillagerRenderer::new);
		registerRender(DMAEntities.EMPTY_CHILD, EmptyChildRenderer::new);
		registerRender(DMAEntities.DALEK_MUTANT, DalekMutantRenderer::new);
		registerRender(DMAEntities.NETHERITE_CYBERMAN, NetheriteCybermanRenderer::new);
		registerRender(DMAEntities.CYBER_PIGLIN, CyberPiglinRenderer::new);
	}


	public static <T extends Entity> void registerRender(RegistryObject<EntityType<T>> entityClass, IRenderFactory<? super T> renderFactory) {
		if (entityClass == null) return;
		RenderingRegistry.registerEntityRenderingHandler(entityClass.get(), renderFactory);
	}
}
