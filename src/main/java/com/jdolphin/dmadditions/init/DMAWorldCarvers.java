package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.carver.AndrozaniminorCarver;
import com.jdolphin.dmadditions.carver.MoonCarver;
import com.swdteam.common.RegistryHandler;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;

public class DMAWorldCarvers {
	public static final RegistryObject<MoonCarver> CARVER;
	public static final RegistryObject<AndrozaniminorCarver> ANDROZANIMINOR_CARVER;

	public DMAWorldCarvers() {
	}

	static {
		CARVER = RegistryHandler.WORLD_CARVERS.register("moon_carver", () -> {
			return new MoonCarver(ProbabilityConfig.CODEC);
		});
		ANDROZANIMINOR_CARVER = RegistryHandler.WORLD_CARVERS.register("androzaniminor_carver", () -> {
			return new AndrozaniminorCarver(ProbabilityConfig.CODEC);
		});

	}
}
