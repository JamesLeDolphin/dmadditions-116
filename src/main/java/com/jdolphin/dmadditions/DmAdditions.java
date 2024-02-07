package com.jdolphin.dmadditions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.block.IRustToo;
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
import com.jdolphin.dmadditions.jokes.JokeReloadListener;
import com.jdolphin.dmadditions.sonic.SonicMagpieTelevision;
import com.jdolphin.dmadditions.world.structure.DMAConfiguredStructures;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.serialization.Codec;
import com.swdteam.common.init.DMSonicRegistry;
import net.minecraft.block.Block;
import net.minecraft.command.CommandSource;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.item.Item;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Supplier;



@Mod("dmadditions")
public class DmAdditions {
	public static final String MODID = "dmadditions";
	public static final String VERSION = "1.3.4";
	public static final boolean IS_DEBUG = java.lang.management.ManagementFactory.getRuntimeMXBean().
		getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();
	public static final DMAServerProxy DMA_PROXY = DistExecutor.runForDist(() -> {
		return DMAClientProxy::new;
	}, () -> {
		return DMAServerProxy::new;
	});
	public static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
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
		modEventBus.addListener(this::runLater);
		DMAStructures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
		// Register things
		DMABlocks.BLOCKS.register(modEventBus);
		DMAEntities.ENTITY_TYPES.register(modEventBus);
		DMABlockEntities.TILE_ENTITY_TYPES.register(modEventBus);
		DMAItems.ITEMS.register(modEventBus);
		DMAWorldCarvers.WORLD_CARVERS.register(modEventBus);
		DMABiomes.BIOMES.register(modEventBus);
		DMAProjectiles.init();
		new DMALootConditionManager();
		new DMASoundEvents();
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, DMAClientConfig.SPEC, "dma-client.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DMACommonConfig.SPEC, "dma-common.toml");
		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(DMAEventHandlerGeneral.class);
		IEventBus vengaBus = MinecraftForge.EVENT_BUS;
		vengaBus.addListener(EventPriority.HIGH, this::biomeModification);
		vengaBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
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

		if(DMAEntities.FLYING_SHARK != null)
			event.put(DMAEntities.FLYING_SHARK.get(), FlyingSharkEntity.setCustomAttributes().build());

		if(DMAEntities.RACNOSS != null)
			event.put(DMAEntities.RACNOSS.get(), RacnossEntity.setCustomAttributes().build());

		if(DMAEntities.SHOPPING_CART != null)
			event.put(DMAEntities.SHOPPING_CART.get(), ShoppingCartEntity.setCustomAttributes().build());
		
		if(DMAEntities.TORCHWOOD_TANK != null)
			event.put(DMAEntities.TORCHWOOD_TANK.get(), ShoppingCartEntity.setCustomAttributes().build());

// 		if(DMAEntities.ICE_GOVERNESS != null)
// 			event.put(DMAEntities.ICE_GOVERNESS.get(), IceGovernessEntity.createAttributes().build());

		if(DMAEntities.CHRISTMAS_CREEPER != null)
			event.put(DMAEntities.CHRISTMAS_CREEPER.get(), CreeperEntity.createAttributes().build());

		if(DMAEntities.WHISPERMAN != null)
			event.put(DMAEntities.WHISPERMAN.get(), WhispermanEntity.createAttributes().build());

		if(DMAEntities.KANTROFARRI != null)
			event.put(DMAEntities.KANTROFARRI.get(), KantrofarriEntity.createAttributes().build());

		if(DMAEntities.JIM != null)
			event.put(DMAEntities.JIM.get(), JimEntity.createAttributes().build());
	}

	private void setup(FMLCommonSetupEvent event) {
		DMASpawnerRegistry.init();
		IRustToo.addRustedVariants();
		event.enqueueWork(() -> {
			DMAStructures.setupStructures();
			DMAConfiguredStructures.registerConfiguredStructures();
		});
	}


	private static Method GETCODEC_METHOD;
	public void addDimensionalSpacing(WorldEvent.Load event) {
		if (event.getWorld() instanceof ServerWorld) {
			ServerWorld world = (ServerWorld) event.getWorld();
			if (!world.dimension().equals(World.OVERWORLD)) {
				return;
			}
			ServerChunkProvider chunkSource = world.getChunkSource();
			try {
				if (GETCODEC_METHOD == null)
					GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
				ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(chunkSource.generator));
				if (cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
			} catch (Exception e) {
				LOGGER.error("Was unable to check if " + world.dimension().location() + " is using Terraforged's ChunkGenerator.");
			}

			if (chunkSource.getGenerator() instanceof FlatChunkGenerator &&
				world.dimension().equals(World.OVERWORLD)) {
				return;
			}
			if (world.isFlat()) {
				return;
			}
			if (AdventUnlock.unlockAt(2)) {
				chunkSource.generator.getSettings().structureConfig().put(DMAStructures.CYBER_UNDERGROUND.get(), DimensionStructuresSettings.DEFAULTS.get(DMAStructures.CYBER_UNDERGROUND.get()));
			}

			if (AdventUnlock.unlockAt(9)) {
				chunkSource.generator.getSettings().structureConfig().put(DMAStructures.MANOR.get(), DimensionStructuresSettings.DEFAULTS.get(DMAStructures.MANOR.get()));
			}

		}

	}

	public static void registerStructure(RegistryKey<DimensionSettings> dimension, Structure<?> structure, StructureSeparationSettings separationSettings) {
		WorldGenRegistries.NOISE_GENERATOR_SETTINGS.getOptional(dimension).ifPresent((dimensionSettings) -> {
			DimensionStructuresSettings structuresSettings = dimensionSettings.structureSettings();
			structuresSettings.structureConfig.put(structure, separationSettings);
		});
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		DMA_PROXY.doClientStuff(event);

		DMABlocks.registerRenderTypes();

		if(hasTC()) {
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
		if (hasTC() && event.includeServer()) {
			datagenerator.addProvider(new FluidTags(datagenerator, existingFileHelper));
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

			Biome.Category category = event.getCategory();
			ResourceLocation biomeRegistryKey = event.getName();

			if (isBiomeValidForManor(category, biomeRegistryKey)) {
				final List<Supplier<StructureFeature<?, ?>>> structures = event.getGeneration().getStructures();
				structures.add(() -> DMAConfiguredStructures.CONFIGURED_MANOR);
			}

			if (isBiomeValidForCyberUnderground(category, biomeRegistryKey)) {
				final List<Supplier<StructureFeature<?, ?>>> structures = event.getGeneration().getStructures();
				structures.add(() -> DMAConfiguredStructures.CONFIGURED_CYBER_UNDERGROUND);
			}
		}
	}

	/* To specify a type of biome return category == Biome.Category.TAIGA, however if you want to specify a speficic
	 biome like in the example below, use (biomeRegistryKey != null && biomeRegistryKey.toString().equals("minecraft:snowy_taiga"));
	 		-TW1 	*/

	private static boolean isBiomeValidForManor(Biome.Category category, ResourceLocation biomeRegistryKey) {
		return (biomeRegistryKey != null && biomeRegistryKey.toString().equals("minecraft:snowy_taiga"));
	}

	private static boolean isBiomeValidForCyberUnderground(Biome.Category category, ResourceLocation biomeRegistryKey) {
		return (biomeRegistryKey != null && biomeRegistryKey.toString().equals("minecraft:snowy_taiga"));
	}

	// Replaces all missing mappings if possible. Surely theres a better way but eh
	@SubscribeEvent
	public void missingItems(RegistryEvent.MissingMappings<Item> event) {
		for (RegistryEvent.MissingMappings.Mapping<Item> itemMapping : event.getMappings("dalekmod")) {
			ResourceLocation regName = itemMapping.key;
			if (regName != null) {
				String path = regName.getPath();
				DMAItems.ITEMS.getEntries().stream()
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
				DMAEntities.ENTITY_TYPES.getEntries().stream()
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
			mapping.remap(DMAWorldCarvers.CARVER.get());

		}
	}

	@SubscribeEvent
	public void addReloadListeners(AddReloadListenerEvent event){
		event.addListener(new JokeReloadListener(GSON, "cracker_jokes"));
	}

	private void runLater(ParallelDispatchEvent event) {
		if(DMABlocks.MAGPIE_TELEVISION != null){
			event.enqueueWork(() -> {
				DMSonicRegistry.SONIC_LOOKUP.put(DMABlocks.MAGPIE_TELEVISION.get(), new SonicMagpieTelevision());
			});
		}
	}
}
