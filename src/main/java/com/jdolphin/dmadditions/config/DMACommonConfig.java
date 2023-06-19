package com.jdolphin.dmadditions.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class DMACommonConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;

	public static final ForgeConfigSpec.ConfigValue<Integer> randomizer_max;
	public static final ForgeConfigSpec.ConfigValue<Boolean> disable_spawns;

	static {
		BUILDER.push("Dalek Mod: Additions Common Config");

		randomizer_max = BUILDER.comment("Maximum distance for randomizer. Deafult = 10000").define("randomizer_max", 10000);
		disable_spawns = BUILDER.comment("Disable the spawning of Dalek Mod mobs. Deafult = false").define("disable_spawns", false);

		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}
