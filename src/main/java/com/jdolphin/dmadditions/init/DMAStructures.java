package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.structure.Spaceship1Structure;
import com.jdolphin.dmadditions.structure.Spaceship2Structure;
import com.jdolphin.dmadditions.structure.Spaceship3Structure;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static com.swdteam.common.init.DMStructures.setupMapSpacingAndLand;

public class DMAStructures {
	public static final DeferredRegister<Structure<?>> DEFERRED_REGISTRY_STRUCTURE;
	public static final RegistryObject<Spaceship1Structure> SPACESHIP_1;
	public static final RegistryObject<Spaceship2Structure> SPACESHIP_2;
	public static final RegistryObject<Spaceship3Structure> SPACESHIP_3;

	public DMAStructures() {
	}

	private static <T extends Structure<?>> RegistryObject<T> registerStructure(String name, Supplier<T> structure) {
		return DEFERRED_REGISTRY_STRUCTURE.register(name, structure);
	}

	public static void setupStructures() {
		setupMapSpacingAndLand((Structure<NoFeatureConfig>) SPACESHIP_1.get(), new StructureSeparationSettings(50, 10, 42069),
			false);
		setupMapSpacingAndLand((Structure<NoFeatureConfig>) SPACESHIP_2.get(), new StructureSeparationSettings(40, 30, 72039),
			false);
		setupMapSpacingAndLand((Structure<NoFeatureConfig>) SPACESHIP_3.get(), new StructureSeparationSettings(70, 20, 89249),
			false);
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
