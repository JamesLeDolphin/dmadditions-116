package com.jdolphin.dmadditions.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class DMACommonConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;

	public static final ForgeConfigSpec.ConfigValue<Integer> randomizer_max;
	public static final ForgeConfigSpec.ConfigValue<Boolean> dma_title;

	static {
		BUILDER.push("Dalek Mod: Additions Config");

		randomizer_max = BUILDER.comment("Maximum distance for randomizer. Deafult = 10000").define("randomizer_max", 10000);
		dma_title = BUILDER.comment("Use Dalek Mod: Additions title screen. Deafult = true").define("dma_title_classic", true);

		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}
