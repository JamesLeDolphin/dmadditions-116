package com.jdolphin.dmadditions.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.jdolphin.dmadditions.structure.Spaceship1Structure;
import com.jdolphin.dmadditions.structure.Spaceship2Structure;
import com.jdolphin.dmadditions.structure.Spaceship3Structure;
import com.swdteam.common.structure.*;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class DMAStructures {
	public static final DeferredRegister<Structure<?>> DEFERRED_REGISTRY_STRUCTURE;
	public static final RegistryObject<Structure<NoFeatureConfig>> SPACESHIP_1;
	public static final RegistryObject<Structure<NoFeatureConfig>> SPACESHIP_2;
	public static final RegistryObject<Structure<NoFeatureConfig>> SPACESHIP_3;

	public DMAStructures() {
	}

	private static <T extends Structure<?>> RegistryObject<T> registerStructure(String name, Supplier<T> structure) {
		return DEFERRED_REGISTRY_STRUCTURE.register(name, structure);
	}

	public static void setupStructures() {
		setupMapSpacingAndLand((Structure) SPACESHIP_1.get(), new StructureSeparationSettings(50, 10, 42069),
			false);
		setupMapSpacingAndLand((Structure) SPACESHIP_2.get(), new StructureSeparationSettings(40, 30, 72039),
			false);
		setupMapSpacingAndLand((Structure) SPACESHIP_3.get(), new StructureSeparationSettings(70, 20, 89249),
			false);
	}

	public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
		Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);
		if (transformSurroundingLand) {
			Structure.NOISE_AFFECTING_FEATURES = ImmutableList.builder().addAll(Structure.NOISE_AFFECTING_FEATURES).add(structure).build();
		}

		DimensionStructuresSettings.DEFAULTS = ImmutableMap.builder().putAll(DimensionStructuresSettings.DEFAULTS).put(structure, structureSeparationSettings).build();
	}

	static {
		DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, "dmadditions");
		SPACESHIP_1 = registerStructure("spaceship_1", () -> {
			return new Spaceship1Structure(NoFeatureConfig.CODEC);
		});
		SPACESHIP_2 = registerStructure("spaceship_2", () -> {
			return new Spaceship2Structure(NoFeatureConfig.CODEC);
		});
		SPACESHIP_3 = registerStructure("spaceship_3", () -> {
			return new Spaceship3Structure(NoFeatureConfig.CODEC);
		});
	}
}
