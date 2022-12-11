package com.jdolphin.dmadditions.structure;

import com.jdolphin.dmadditions.init.DMAStructures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;

public class DMAConfiguredStructures {
	public static StructureFeature<?, ?> CONFIGURED_SPACESHIP_1;
	public static StructureFeature<?, ?> CONFIGURED_SPACESHIP_2;
	public static StructureFeature<?, ?> CONFIGURED_SPACESHIP_3;
	
	public DMAConfiguredStructures() {
	}
	public static void registerConfiguredStructures() {
		Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, new ResourceLocation("dalekmod", "configured_spaceship_1"), CONFIGURED_SPACESHIP_1);
		Registry.register(registry, new ResourceLocation("dalekmod", "configured_spaceship_2"), CONFIGURED_SPACESHIP_2);
		Registry.register(registry, new ResourceLocation("dalekmod", "configured_spaceship_3"), CONFIGURED_SPACESHIP_3);
	}

	static {
		CONFIGURED_SPACESHIP_1 = (DMAStructures.SPACESHIP_1.get()).configured(IFeatureConfig.NONE);
		CONFIGURED_SPACESHIP_2 = (DMAStructures.SPACESHIP_2.get()).configured(IFeatureConfig.NONE);
		CONFIGURED_SPACESHIP_3 = (DMAStructures.SPACESHIP_3.get()).configured(IFeatureConfig.NONE);
	}
}
