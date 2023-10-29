package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.carver.MoonCarver;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DMAWorldCarvers {
	public static final DeferredRegister<WorldCarver<?>> WORLD_CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, DmAdditions.MODID);
	public static final RegistryObject<MoonCarver> CARVER;

	public DMAWorldCarvers() {
	}

	static {
		CARVER = WORLD_CARVERS.register("moon_carver", () -> {
			return new MoonCarver(ProbabilityConfig.CODEC);
		});
	}
}
