package com.jdolphin.dmadditions;

import com.jdolphin.dmadditions.block.IRustToo;
import com.jdolphin.dmadditions.block.RoundelContainerBlock;
import com.jdolphin.dmadditions.client.proxy.DMAClientProxy;
import com.jdolphin.dmadditions.client.proxy.DMAServerProxy;
import com.jdolphin.dmadditions.commands.*;
import com.jdolphin.dmadditions.compat.tconstruct.FluidTags;
import com.jdolphin.dmadditions.compat.tconstruct.TinkersRenderType;
import com.jdolphin.dmadditions.config.DMAClientConfig;
import com.jdolphin.dmadditions.config.DMACommonConfig;
import com.jdolphin.dmadditions.entity.*;
import com.jdolphin.dmadditions.event.DMAEventHandlerGeneral;
import com.jdolphin.dmadditions.init.*;
import com.mojang.brigadier.CommandDispatcher;
import com.swdteam.common.block.RoundelBlock;
import com.swdteam.common.init.DMBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.command.CommandSource;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
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
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static com.jdolphin.dmadditions.init.DMABlocks.registerBlock;


@Mod("dmadditions")
public class DmAdditions {
	public static final String MODID = "dmadditions";

	public static final boolean IS_DEBUG = java.lang.management.ManagementFactory.getRuntimeMXBean().
		getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();
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
		modEventBus.addListener(this::entityAttributeEvent);
		// Register things
		DMABlocks.BLOCKS.register(modEventBus);

		RegistryHandler.init();
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, DMAClientConfig.SPEC, "dma-client.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DMACommonConfig.SPEC, "dma-common.toml");
		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(DMAEventHandlerGeneral.class);
		IEventBus vengaBus = MinecraftForge.EVENT_BUS;
		vengaBus.addListener(EventPriority.HIGH, this::biomeModification);
		if (hasTC()) {
			DMAFluids.FLUIDS.register(modEventBus);
		}
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

	public void entityAttributeEvent(EntityAttributeCreationEvent event) {
		event.put(DMAEntities.JAMESLEDOLPHIN.get(), JamesLeDolphinEntity.createAttributes().build());
		event.put(DMAEntities.WOODEN_CYBERMAN.get(), WoodenCybermanEntity.setCustomAttributes().build());
		event.put(DMAEntities.BESSIE.get(), BessieEntity.setCustomAttributes().build());
		event.put(DMAEntities.TW_SUV.get(), TorchwoodSuvEntity.setCustomAttributes().build());
		event.put(DMAEntities.SNOWMAN.get(), SnowmanEntity.setCustomAttributes().build());
		event.put(DMAEntities.CHRISTMAS_TREE.get(), ChristmasTreeEntity.setCustomAttributes().build());
		event.put(DMAEntities.PILOT_FISH.get(), PilotFishEntity.setCustomAttributes().build());
	}

	private void setup(FMLCommonSetupEvent event) {
		DMASpawnerRegistry.init();
		IRustToo.addRustedVariants();
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		DMA_PROXY.doClientStuff(event);
		RenderTypeLookup.setRenderLayer(DMABlocks.STEEL_BEAMS_ROUNDEL_CONTAINER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(DMABlocks.RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(DMABlocks.STAINLESS_STEEL_BEAMS_ROUNDEL_CONTAINER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(DMABlocks.TARDIS_SNOWGLOBE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(DMABlocks.CHRISTMAS_TREE.get(), RenderType.cutoutMipped());
		if (hasTC()) {
			TinkersRenderType.setTranslucent(DMAFluids.molten_dalekanium);
			TinkersRenderType.setTranslucent(DMAFluids.molten_steel);
			TinkersRenderType.setTranslucent(DMAFluids.molten_stainless_steel);
			TinkersRenderType.setTranslucent(DMAFluids.molten_metalert);
			TinkersRenderType.setTranslucent(DMAFluids.molten_silicon);
		}
	}

	@SubscribeEvent
	static void gatherData(final GatherDataEvent event) {
		DataGenerator datagenerator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		if (event.includeServer()) {
			if (hasTC()) {
				datagenerator.addProvider(new FluidTags(datagenerator, existingFileHelper));
			}
		}
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
	// Replaces all missing mappings if possible. Surely theres a better way but eh
	@SubscribeEvent
	public void missingItems(RegistryEvent.MissingMappings<Item> event) {
		for (RegistryEvent.MissingMappings.Mapping<Item> itemMapping : event.getMappings("dalekmod")) {
			ResourceLocation regName = itemMapping.key;
			if (regName != null) {
				String path = regName.getPath();
				RegistryHandler.ITEMS.getEntries().stream()
					.filter(thing -> thing.getId().getPath().equals(path))
					.forEach(item -> itemMapping.remap(item.get()));
			}
		}
	}

	@SubscribeEvent
	public void missingEntities(RegistryEvent.MissingMappings<EntityType<?>> event) {
		for (RegistryEvent.MissingMappings.Mapping<EntityType<?>> entityMapping : event.getMappings("dalekmod")) {
			ResourceLocation regName = entityMapping.key;
			if (regName != null) {
				String path = regName.getPath();
				RegistryHandler.ENTITY_TYPES.getEntries().stream()
					.filter(thing -> thing.getId().getPath().equals(path))
					.forEach(entity -> entityMapping.remap(entity.get()));
			}
		}
	}
	@SubscribeEvent
	public void missingBlocks(RegistryEvent.MissingMappings<Block> event) {
		for (RegistryEvent.MissingMappings.Mapping<Block> blockMapping : event.getMappings("dalekmod")) {
			ResourceLocation regName = blockMapping.key;
			if (regName != null) {
				String path = regName.getPath();
				DMABlocks.BLOCKS.getEntries().stream()
					.filter(thing -> thing.getId().getPath().equals(path))
					.forEach(block -> blockMapping.remap(block.get()));
			}
		}
	}

	@SubscribeEvent
	public void missingBiomes(RegistryEvent.MissingMappings<Biome> event) {
		for (RegistryEvent.MissingMappings.Mapping<Biome> mapping : event.getMappings("dalekmod")) {
			mapping.remap(DMABiomes.MOON_BIOME.get());
		}
	}
	@SubscribeEvent
	public void missingCarvers(RegistryEvent.MissingMappings<WorldCarver<?>> event) {
		for (RegistryEvent.MissingMappings.Mapping<WorldCarver<?>> mapping : event.getMappings("dalekmod")) {
			ResourceLocation regName = mapping.key;
			mapping.remap(DMAWorldCarvers.CARVER.get());

		}
	}

}
