package com.jdolphin.dmadditions.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class DMAClientConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;

	public static final ForgeConfigSpec.ConfigValue<Boolean> dma_title;

	static {
		BUILDER.push("Dalek Mod: Additions Client Config");

		dma_title = BUILDER.comment("Use Dalek Mod: Additions title screen. Deafult = true").define("dma_title_classic", true);

		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}
