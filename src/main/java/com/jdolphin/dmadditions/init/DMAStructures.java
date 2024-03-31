package com.jdolphin.dmadditions.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.world.structure.*;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class DMAStructures {
	public static final DeferredRegister<Structure<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, DmAdditions.MODID);

	public static final RegistryObject<Structure<NoFeatureConfig>> MANOR = registerStructure("manor", () -> new ManorStructure(NoFeatureConfig.CODEC));
	public static final RegistryObject<Structure<NoFeatureConfig>> CYBER_UNDERGROUND = registerStructure("cyber_underground",
		() -> new CyberUndergroundStructure(NoFeatureConfig.CODEC));

//	public static final RegistryObject<Structure<NoFeatureConfig>> DEAD_TREE_1 = registerStructure("dead_tree_1", () -> new DeadTree1Structure(NoFeatureConfig.CODEC));
//	public static final RegistryObject<Structure<NoFeatureConfig>> DEAD_TREE_2 = registerStructure("dead_tree_2", () -> new DeadTree2Structure(NoFeatureConfig.CODEC));
	public static final RegistryObject<Structure<NoFeatureConfig>> CYBER_MONDAS = registerStructure("mondas_bases",
		() -> new MondasCyberBase(NoFeatureConfig.CODEC));

	public DMAStructures() {
	}

	private static <T extends Structure<?>> RegistryObject<T> registerStructure(String name, Supplier<T> structure) {
		return DEFERRED_REGISTRY_STRUCTURE.register(name, structure);
	}

	public static void setupStructures() {
		setupMapSpacingAndLand(MANOR.get(), new StructureSeparationSettings(50, 10, 42069314), false);
		setupMapSpacingAndLand(CYBER_UNDERGROUND.get(), new StructureSeparationSettings(40, 10, 23512), false);
//		setupMapSpacingAndLand(DEAD_TREE_1.get(), new StructureSeparationSettings(5, 5, 5), false);
//		setupMapSpacingAndLand(DEAD_TREE_2.get(), new StructureSeparationSettings(5, 5, 5), false);
		setupMapSpacingAndLand(CYBER_MONDAS.get(), new StructureSeparationSettings(30, 10, 23578), false);

	}

	public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
		Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);
		if (transformSurroundingLand) {
			Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder().addAll(Structure.NOISE_AFFECTING_FEATURES).add(structure).build();
		}

		DimensionStructuresSettings.DEFAULTS =
			ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
				.putAll(DimensionStructuresSettings.DEFAULTS)
				.put(structure, structureSeparationSettings)
				.build();
	}
}
