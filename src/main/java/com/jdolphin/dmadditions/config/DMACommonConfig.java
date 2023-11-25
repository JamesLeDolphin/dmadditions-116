package com.jdolphin.dmadditions.config;

import java.util.Collections;
import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;

public final class DMACommonConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;

	public static final ForgeConfigSpec.ConfigValue<Integer> randomizer_max;
	public static final ForgeConfigSpec.ConfigValue<Boolean> disable_spawns;
	public static final ForgeConfigSpec.ConfigValue<Boolean> disable_swd_laser;
	public static final ForgeConfigSpec.ConfigValue<Boolean> disable_cyberdrone_laser;
	public static final ForgeConfigSpec.ConfigValue<Boolean> disable_explosive_laser;
	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> disable_dma_commands;

	static {
		BUILDER.push("Dalek Mod: Additions Common Config");

		randomizer_max = BUILDER.comment("Maximum distance for randomizer. Default = 10000").define("randomizer_max", 10000);
		disable_spawns = BUILDER.comment("Disable the spawning of Dalek Mod mobs. Default = false").define("disable_spawns", false);
		disable_swd_laser = BUILDER.comment("Disable special weapons daleks explosive laser. Default = false").define("disable_swd_laser", false);
		disable_cyberdrone_laser = BUILDER.comment("Disable cyber drones explosive laser. Default = false").define("disable_cyberdrone_laser", false);
		disable_explosive_laser = BUILDER.comment("Disable all explosive lasers. Default = false").define("disable_explosive_laser", false);

		disable_dma_commands = BUILDER.comment("Disable dma commands").defineList("disable_dma_commands", Collections.emptyList(), entry -> true);

		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}
