package com.jdolphin.dmadditions;

import com.jdolphin.dmadditions.block.IRustToo;
import com.jdolphin.dmadditions.client.proxy.DMAClientProxy;
import com.jdolphin.dmadditions.client.proxy.DMAServerProxy;
import com.jdolphin.dmadditions.entity.WoodenCybermanEntity;
import com.jdolphin.dmadditions.init.DMABlocks;
import com.jdolphin.dmadditions.init.DMAEntities;
import com.swdteam.common.entity.CybermanEntity;
import com.swdteam.common.init.DMEntities;
import com.swdteam.main.proxy.ClientProxy;
import com.swdteam.main.proxy.ServerProxy;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.config.DMAClientConfig;
import com.jdolphin.dmadditions.config.DMACommonConfig;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("dmadditions")
public class DmAdditions {
	public static final String MODID = "dmadditions";
	public static final boolean IS_DEBUG = java.lang.management.ManagementFactory.getRuntimeMXBean().
		getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

	// Directly reference a log4j logger.
	private static final Logger LOGGER = LogManager.getLogger();
	public static final DMAServerProxy DMA_PROXY = (DMAServerProxy) DistExecutor.runForDist(() -> {
		return DMAClientProxy::new;
	}, () -> {
		return DMAServerProxy::new;
	});


	public DmAdditions() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		LOGGER.info(IS_DEBUG ? "Running in debugger" : "Not running in debugger");
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::doClientStuff);
		// Register things
		RegistryHandler.init();
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, DMAClientConfig.SPEC, "dma-client.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DMACommonConfig.SPEC, "dma-common.toml");
		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);

	}
	private void setup(FMLCommonSetupEvent event) {
		IRustToo.addRustedVariants();
	GlobalEntityTypeAttributes.put((EntityType) DMAEntities.WOODEN_CYBERMAN_ENTITY.get(), WoodenCybermanEntity.setCustomAttributes().build());
	}
	private void doClientStuff(final FMLClientSetupEvent event) {
		DMA_PROXY.doClientStuff(event);
		RenderTypeLookup.setRenderLayer(DMABlocks.STEEL_BEAMS_ROUNDEL_CONTAINER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(DMABlocks.RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(DMABlocks.STAINLESS_STEEL_BEAMS_ROUNDEL_CONTAINER.get(), RenderType.cutout());
	}
}
