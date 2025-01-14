package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.RandomBlockMatchRuleTest;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.ForkyTrunkPlacer;

public class DMAConfiguredStructures {
	public static StructureFeature<?, ?> CONFIGURED_MANOR = DMAStructures.MANOR.get().configured(IFeatureConfig.NONE);
	public static StructureFeature<?, ?> CONFIGURED_CYBER_UNDERGROUND = DMAStructures.CYBER_UNDERGROUND.get().configured(IFeatureConfig.NONE);
	public static StructureFeature<?, ?> CONFIGURED_CYBER_MONDAS = DMAStructures.CYBER_MONDAS.get().configured(IFeatureConfig.NONE);
	public static StructureFeature<?, ?> CONFIGURED_MONDAS_RUIN = DMAStructures.MONDAS_RUIN.get().configured(IFeatureConfig.NONE);
	public static StructureFeature<?, ?> CONFIGURED_SHED = DMAStructures.GALLIFREY_SHED.get().configured(IFeatureConfig.NONE);
	public static StructureFeature<?, ?> CONFIGURED_CITADEL = DMAStructures.CITADEL.get().configured(IFeatureConfig.NONE);

	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> DEAD_TREE =
		register("dead_tree", Feature.TREE.configured((
			new BaseTreeFeatureConfig.Builder(
				new SimpleBlockStateProvider(Blocks.OAK_LOG.defaultBlockState()),
				new SimpleBlockStateProvider(Blocks.AIR.defaultBlockState()),
				new BlobFoliagePlacer(FeatureSpread.fixed(1), FeatureSpread.fixed(0), 1),
				new ForkyTrunkPlacer(4, 2, 3),
				new TwoLayerFeature(1, 1, 1))).heightmap(Heightmap.Type.WORLD_SURFACE_WG).ignoreVines().build()));

	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> GALLIFREY_TREE =
		register("gallifrey_tree", Feature.TREE.configured((
			new BaseTreeFeatureConfig.Builder(
				new SimpleBlockStateProvider(DMABlocks.GALLIFREY_OAK_LOG.get().defaultBlockState()),
				new SimpleBlockStateProvider(DMABlocks.GALLIFREY_OAK_LEAVES.get().defaultBlockState()),
				new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3),
				new ForkyTrunkPlacer(4, 5, 3),
				new TwoLayerFeature(3, 1, 2))).heightmap(Heightmap.Type.WORLD_SURFACE_WG).ignoreVines().build()));

	public static final ConfiguredFeature<OreFeatureConfig, ?> SONIC_ORE =
		OreFeature.ORE.configured(new OreFeatureConfig(new RandomBlockMatchRuleTest(Blocks.STONE, 0.25f),
			DMABlocks.STONE_SONIC_CRYSTAL_ORE.get().defaultBlockState(), 2));

	public static void registerConfiguredStructures() {
		Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, Helper.createAdditionsRL("configured_manor"), CONFIGURED_MANOR);
		Registry.register(registry, Helper.createAdditionsRL("configured_citadel"), CONFIGURED_CITADEL);
		Registry.register(registry, Helper.createAdditionsRL("configured_gallifrey_shed"), CONFIGURED_SHED);
		Registry.register(registry, Helper.createAdditionsRL("configured_cyber_underground"), CONFIGURED_CYBER_UNDERGROUND);
		Registry.register(registry, Helper.createAdditionsRL("configured_mondas_base"), CONFIGURED_CYBER_MONDAS);
		Registry.register(registry, Helper.createAdditionsRL("configured_mondas_ruin"), CONFIGURED_MONDAS_RUIN);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(DMAStructures.MANOR.get(), CONFIGURED_MANOR);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(DMAStructures.CITADEL.get(), CONFIGURED_CITADEL);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(DMAStructures.GALLIFREY_SHED.get(), CONFIGURED_SHED);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(DMAStructures.CYBER_MONDAS.get(), CONFIGURED_CYBER_MONDAS);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(DMAStructures.CYBER_UNDERGROUND.get(), CONFIGURED_CYBER_UNDERGROUND);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(DMAStructures.MONDAS_RUIN.get(), CONFIGURED_MONDAS_RUIN);

	}

	private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key,
																				 ConfiguredFeature<FC, ?> configuredFeature) {
		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
	}
}
