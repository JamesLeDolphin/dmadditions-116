package com.jdolphin.dmadditions.config;

import net.minecraftforge.common.ForgeConfigSpec;


public final class DMAClientConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	public static ForgeConfigSpec.ConfigValue<Boolean> dma_classic;

	static {
		BUILDER.push("Dalek Mod: Additions Client Config");
		dma_classic = BUILDER.comment("Use classic Dalek Mod (1.12) background images on title screen").define("classic", false);

		BUILDER.pop();
		SPEC = BUILDER.build();
	}

}
