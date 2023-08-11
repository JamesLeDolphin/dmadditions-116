package com.jdolphin.dmadditions;

import com.jdolphin.dmadditions.block.IRustToo;
import com.jdolphin.dmadditions.client.proxy.DMAClientProxy;
import com.jdolphin.dmadditions.client.proxy.DMAServerProxy;
import com.jdolphin.dmadditions.commands.*;
import com.jdolphin.dmadditions.config.DMAClientConfig;
import com.jdolphin.dmadditions.config.DMACommonConfig;
import com.jdolphin.dmadditions.entity.*;
import com.jdolphin.dmadditions.event.DMAEventHandlerGeneral;
import com.jdolphin.dmadditions.init.DMABlocks;
import com.jdolphin.dmadditions.init.DMAEntities;
import com.jdolphin.dmadditions.init.DMASpawnerRegistry;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


@Mod("dmadditions")
public class DmAdditions {
	public static final String MODID = "dmadditions";
	public static final boolean IS_DEBUG = java.lang.management.ManagementFactory.getRuntimeMXBean().
		getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

	// Directly reference a log4j logger.
	private static final Logger LOGGER = LogManager.getLogger();
	public static final DMAServerProxy DMA_PROXY = DistExecutor.runForDist(() -> {
		return DMAClientProxy::new;
	}, () -> {
		return DMAServerProxy::new;
	});

	public static boolean hasNTM() {
		return ModList.get().isLoaded("tardis");
	}
	public static boolean hasTC() {
		return ModList.get().isLoaded("tconstruct");
	}


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
		MinecraftForge.EVENT_BUS.register(DMAEventHandlerGeneral.class);
		IEventBus vengaBus = MinecraftForge.EVENT_BUS;
		vengaBus.addListener(EventPriority.HIGH, this::biomeModification);

	}



	public void registerCommands(CommandDispatcher<CommandSource> dispatcher) {
		GameModeCommand.register(dispatcher);
		TeleportCommand.register(dispatcher);
		CommandSit.register(dispatcher);
		ToggleModeCommand.register(dispatcher);
		GodCommand.register(dispatcher);
	}
	@SubscribeEvent
	public void onRegisterCommandEvent(RegisterCommandsEvent event) {
		CommandDispatcher<CommandSource> commandDispatcher = event.getDispatcher();
		this.registerCommands(commandDispatcher);
	}
	private void setup(FMLCommonSetupEvent event) {
		DMASpawnerRegistry.init();
		IRustToo.addRustedVariants();
		event.enqueueWork(() -> {
			GlobalEntityTypeAttributes.put(DMAEntities.JAMESLEDOLPHIN.get(), JamesLeDolphinEntity.createAttributes().build());

			if (DMAEntities.WOODEN_CYBERMAN != null)
				GlobalEntityTypeAttributes.put(DMAEntities.WOODEN_CYBERMAN.get(), WoodenCybermanEntity.setCustomAttributes().build());

			if (DMAEntities.BESSIE != null)
				GlobalEntityTypeAttributes.put(DMAEntities.BESSIE.get(), BessieEntity.setCustomAttributes().build());
			if (DMAEntities.CYBERBIRD != null)
				GlobalEntityTypeAttributes.put(DMAEntities.CYBERBIRD.get(), CyberDroneEntity.setCustomAttributes().build());
			if (DMAEntities.TW_SUV != null)
				GlobalEntityTypeAttributes.put(DMAEntities.TW_SUV.get(), TorchwoodSuvEntity.setCustomAttributes().build());

			if (DMAEntities.SNOWMAN != null)
				GlobalEntityTypeAttributes.put(DMAEntities.SNOWMAN.get(), SnowmanEntity.setCustomAttributes().build());

			if (DMAEntities.CHRISTMAS_TREE != null)
				GlobalEntityTypeAttributes.put(DMAEntities.CHRISTMAS_TREE.get(), ChristmasTreeEntity.setCustomAttributes().build());

			if (DMAEntities.PILOT_FISH != null)
				GlobalEntityTypeAttributes.put(DMAEntities.PILOT_FISH.get(), PilotFishEntity.setCustomAttributes().build());
		});
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		DMA_PROXY.doClientStuff(event);
		RenderTypeLookup.setRenderLayer(DMABlocks.STEEL_BEAMS_ROUNDEL_CONTAINER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(DMABlocks.RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(DMABlocks.STAINLESS_STEEL_BEAMS_ROUNDEL_CONTAINER.get(), RenderType.cutout());
	}

	public void biomeModification(BiomeLoadingEvent event) {
		if (DMASpawnerRegistry.spawns.containsKey(event.getName())) {
			DMASpawnerRegistry.SpawnInfo info = DMASpawnerRegistry.spawns.get(event.getName());

			for (int i = 0; i < info.getSpawners().size(); ++i) {
				DMASpawnerRegistry.SpawnInfo.Spawn spawn = info.getSpawners().get(i);
				List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(spawn.entityType);
				spawns.add(spawn.spawner);
			}
		}
	}

}
