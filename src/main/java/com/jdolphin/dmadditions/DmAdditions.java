package com.jdolphin.dmadditions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jdolphin.dmadditions.client.ClientDMBusEvents;
import com.jdolphin.dmadditions.client.init.DMATileRenderRegistry;
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
import com.jdolphin.dmadditions.util.Helper;
import com.jdolphin.dmadditions.world.structure.DMAConfiguredStructures;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.serialization.Codec;
import com.swdteam.common.block.IRust;
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
import net.minecraftforge.fml.ExtensionPoint;
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
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;



@Mod(DmAdditions.MODID)
public class DmAdditions {
	public static final String MODID = "dmadditions";
	public static final String VERSION = "1.3.9";
	public static final boolean IS_DEBUG = java.lang.management.ManagementFactory.getRuntimeMXBean().
		getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

	public static final Logger LOGGER = LogManager.getLogger();
	public static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
	public static final Random RANDOM = new Random();

	public static boolean hasNTM() {
		return ModList.get().isLoaded("tardis");
	}
	public static boolean hasTC() {
		return ModList.get().isLoaded("tconstruct");
	}
	public static boolean hasIMMP() {
		return ModList.get().isLoaded("immersive_portals");
	}



	public DmAdditions() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		LOGGER.info(IS_DEBUG ? "Running in debugger" : "Not running in debugger");
		bus.addListener(this::commonSetup);
		bus.addListener(this::doClientStuff);
		bus.addListener(this::entityAttributeEvent);
		bus.addListener(this::runLater);
		//This one line fixes joinging servers that dont have dma
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> false));
		// Register things
		DMAStructures.DEFERRED_REGISTRY_STRUCTURE.register(bus);
		DMABlocks.BLOCKS.register(bus);
		DMAEntities.ENTITY_TYPES.register(bus);
		DMABlockEntities.TILE_ENTITY_TYPES.register(bus);
		DMAItems.ITEMS.register(bus);
		DMAWorldCarvers.WORLD_CARVERS.register(bus);
		DMABiomes.BIOMES.register(bus);
		DMAProjectiles.init();
		DMALootConditionManager.init();
		DMASoundEvents.init();
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, DMAClientConfig.SPEC, "dma-client.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DMACommonConfig.SPEC, "dma-common.toml");
		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(DMAEventHandlerGeneral.class);
		IEventBus vengaBus = MinecraftForge.EVENT_BUS;
		vengaBus.addListener(EventPriority.HIGH, this::biomeModification);
		vengaBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
		if (hasTC()) {
			DMAFluids.FLUIDS.register(bus);
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
		this.registerCommands(event.getDispatcher());
	}

	public void entityAttributeEvent(EntityAttributeCreationEvent event) {
		event.put(DMAEntities.JAMESLEDOLPHIN.get(), JamesLeDolphinEntity.createAttributes().build());
		event.put(DMAEntities.WOODEN_CYBERMAN.get(), WoodenCybermanEntity.setCustomAttributes().build());
		event.put(DMAEntities.BESSIE.get(), BessieEntity.setCustomAttributes().build());
		event.put(DMAEntities.TW_SUV.get(), TorchwoodSuvEntity.setCustomAttributes().build());
		event.put(DMAEntities.SNOWMAN.get(), SnowmanEntity.setCustomAttributes().build());
		event.put(DMAEntities.CHRISTMAS_TREE.get(), ChristmasTreeEntity.setCustomAttributes().build());
		event.put(DMAEntities.PILOT_FISH.get(), PilotFishEntity.setCustomAttributes().build());
		event.put(DMAEntities.FLYING_SHARK.get(), FlyingSharkEntity.setCustomAttributes().build());
		event.put(DMAEntities.RACNOSS.get(), RacnossEntity.setCustomAttributes().build());
		event.put(DMAEntities.SHOPPING_CART.get(), ShoppingCartEntity.setCustomAttributes().build());
		event.put(DMAEntities.TORCHWOOD_TANK.get(), ShoppingCartEntity.setCustomAttributes().build());
		event.put(DMAEntities.CHRISTMAS_CREEPER.get(), CreeperEntity.createAttributes().build());
		event.put(DMAEntities.WHISPERMAN.get(), WhispermanEntity.createAttributes().build());
		event.put(DMAEntities.KANTROFARRI.get(), KantrofarriEntity.createAttributes().build());
		event.put(DMAEntities.JIM.get(), JimEntity.createAttributes().build());
	}


	private void commonSetup(FMLCommonSetupEvent event) {
		DMASpawnerRegistry.init();
		event.enqueueWork(() -> {
			DMAPackets.init();
			DMAStructures.setupStructures();
			DMAConfiguredStructures.registerConfiguredStructures();
		});

		if (hasNTM()) Helper.info("Enabling New Tardis Mod compatibility features");
		if (hasTC()) Helper.info("Enabling Tinker's Construct compatibility features");
		if (hasIMMP()) Helper.info("Enabling Immersive Portals compatibility features");
		if (hasIMMP() && hasNTM()) Helper.warn("New Tardis Mod and Immersive Portals may not work well together! You've been warned!");
	}


	private static Method GETCODEC_METHOD;

	@SuppressWarnings("unchecked")
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
			chunkSource.generator.getSettings().structureConfig().put(DMAStructures.CYBER_UNDERGROUND.get(),
				DimensionStructuresSettings.DEFAULTS.get(DMAStructures.CYBER_UNDERGROUND.get()));
			chunkSource.generator.getSettings().structureConfig().put(DMAStructures.MANOR.get(),
				DimensionStructuresSettings.DEFAULTS.get(DMAStructures.MANOR.get()));

			chunkSource.generator.getSettings().structureConfig().put(DMAStructures.DEAD_TREE_1.get(),
				DimensionStructuresSettings.DEFAULTS.get(DMAStructures.DEAD_TREE_1.get()));
			chunkSource.generator.getSettings().structureConfig().put(DMAStructures.DEAD_TREE_2.get(),
				DimensionStructuresSettings.DEFAULTS.get(DMAStructures.DEAD_TREE_2.get()));


		}

	}

	public static void registerStructure(RegistryKey<DimensionSettings> dimension, Structure<?> structure, StructureSeparationSettings separationSettings) {
		WorldGenRegistries.NOISE_GENERATOR_SETTINGS.getOptional(dimension).ifPresent((dimensionSettings) -> {
			DimensionStructuresSettings structuresSettings = dimensionSettings.structureSettings();
			structuresSettings.structureConfig.put(structure, separationSettings);
		});
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		DMABlocks.registerRenderTypes();
		MinecraftForge.EVENT_BUS.register(ClientDMBusEvents.class);
		DMATileRenderRegistry.init();
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
			if (isBiomeValidForDeadTree(category, biomeRegistryKey)) {
				final List<Supplier<StructureFeature<?, ?>>> structures = event.getGeneration().getStructures();
				structures.add(() -> DMAConfiguredStructures.CONFIGURED_DEAD_TREE_1);
			}
			if (isBiomeValidForDeadTree(category, biomeRegistryKey)) {
				final List<Supplier<StructureFeature<?, ?>>> structures = event.getGeneration().getStructures();
				structures.add(() -> DMAConfiguredStructures.CONFIGURED_DEAD_TREE_2);
			}
		}
	}

	/* To specify a type of biome return category == Biome.Category.TAIGA, however if you want to specify a speficic
	 biome like in the example below, use (biomeRegistryKey != null && biomeRegistryKey.toString().equals("minecraft:snowy_taiga"));
	 		-TW1 	*/
	//Okay will do - Jam

	private static boolean isBiomeValidForManor(Biome.Category category, ResourceLocation biomeRegistryKey) {
		return (biomeRegistryKey != null && biomeRegistryKey.toString().equals("minecraft:snowy_taiga"));
	}

	private static boolean isBiomeValidForDeadTree(Biome.Category category, ResourceLocation biomeRegistryKey) {
		return (biomeRegistryKey != null && biomeRegistryKey.toString().equals("dmadditions:mondas_dead_forest"));
	}
	private static boolean isBiomeValidForCyberUnderground(Biome.Category category, ResourceLocation biomeRegistryKey) {
		return (biomeRegistryKey != null && biomeRegistryKey.toString().equals("minecraft:snowy_taiga"));
	}

	@SubscribeEvent
	public void addReloadListeners(AddReloadListenerEvent event){
		event.addListener(new JokeReloadListener(GSON, "cracker_jokes"));
	}

	private void runLater(ParallelDispatchEvent event) {
		if(DMABlocks.MAGPIE_TELEVISION != null){
			event.enqueueWork(() -> {
				DMSonicRegistry.SONIC_LOOKUP.put(DMABlocks.MAGPIE_TELEVISION.get(), new SonicMagpieTelevision());
				IRust.rustedMap.put(DMABlocks.STEEL_BEAMS_ROUNDEL_CONTAINER.get(), DMABlocks.RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER.get());
				IRust.rustedMap.put(DMABlocks.STEEL_BEAMS_ROUNDEL_CONTAINER.get(), DMABlocks.RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER.get());
				IRust.rustedMap.put(DMABlocks.FILLED_STEEL_BEAMS_ROUNDEL_CONTAINER.get(), DMABlocks.FILLED_RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER.get());
				IRust.rustedMap.put(DMABlocks.STEEL_REINFORCED_WALLING_ROUNDEL_CONTAINER.get(), DMABlocks.RUSTED_STEEL_REINFORCED_WALLING_ROUNDEL_CONTAINER.get());
			});
		}
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
}
