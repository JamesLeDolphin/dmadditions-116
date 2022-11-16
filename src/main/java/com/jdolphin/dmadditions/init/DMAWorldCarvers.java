package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.carver.MoonCarver;
import com.swdteam.common.RegistryHandler;
import com.swdteam.common.carver.ClassicWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;

public class DMAWorldCarvers {
	public static final RegistryObject<MoonCarver> CARVER;

	public DMAWorldCarvers() {
	}

	static {
		CARVER = RegistryHandler.WORLD_CARVERS.register("moon_carver", () -> {
			return new MoonCarver(ProbabilityConfig.CODEC);
		});
	}
}
