package com.jdolphin.dmadditions.world.structure;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.init.DMAStructures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class DMAConfiguredStructures {
	public static StructureFeature<?, ?> CONFIGURED_MANOR = DMAStructures.MANOR.get().configured(IFeatureConfig.NONE);
	public static StructureFeature<?, ?> CONFIGURED_CYBER_UNDERGROUND = DMAStructures.CYBER_UNDERGROUND.get().configured(IFeatureConfig.NONE);


	public DMAConfiguredStructures() {
	}

	public static void registerConfiguredStructures() {
		Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, new ResourceLocation(DmAdditions.MODID, "configured_manor"), CONFIGURED_MANOR);
		Registry.register(registry, new ResourceLocation(DmAdditions.MODID, "configured_cyber_underground"), CONFIGURED_CYBER_UNDERGROUND);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(DMAStructures.MANOR.get(), CONFIGURED_MANOR);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(DMAStructures.CYBER_UNDERGROUND.get(), CONFIGURED_CYBER_UNDERGROUND);
	}
}