package com.jdolphin.dmadditions.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class DMACommonConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;

	public static final ForgeConfigSpec.ConfigValue<Integer> randomizer_max;
	public static final ForgeConfigSpec.ConfigValue<Boolean> disable_spawns;
	public static final ForgeConfigSpec.ConfigValue<Boolean> disable_swd_laser;

	static {
		BUILDER.push("Dalek Mod: Additions Common Config");

		randomizer_max = BUILDER.comment("Maximum distance for randomizer. Default = 10000").define("randomizer_max", 10000);
		disable_spawns = BUILDER.comment("Disable the spawning of Dalek Mod mobs. Default = false").define("disable_spawns", false);
		disable_swd_laser = BUILDER.comment("Disable special weapons daleks explosive laser. Default = false").define("disable_swd_laser", false);

		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}
