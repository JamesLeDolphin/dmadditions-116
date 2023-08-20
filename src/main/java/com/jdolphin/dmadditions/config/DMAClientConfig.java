package com.jdolphin.dmadditions.config;

import com.jdolphin.dmadditions.client.title.vortex.Vortex;
import com.jdolphin.dmadditions.client.title.vortex.VortexType;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Random;


public final class DMAClientConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	//public static ForgeConfigSpec.ConfigValue<Boolean> dma_vortex;
	public static ForgeConfigSpec.ConfigValue<Boolean> dma_classic;
	//public static ForgeConfigSpec.EnumValue<VortexType> vortex_type;
	//public static ForgeConfigSpec.BooleanValue white_hole;

	static {
		BUILDER.push("Dalek Mod: Additions Client Config");
		//dma_vortex = BUILDER.comment("Use time vortex on title screen").define("vortex", true);
		dma_classic = BUILDER.comment("Use classic Dalek Mod (1.12) background images on title screen").define("classic", true);
		//white_hole = BUILDER.comment("Enable White Hole at the end of the vortex").define("white_hole", false);
		//vortex_type = BUILDER.comment("The type of vortex to use").defineEnum("vortex_type", VortexType.NONE);

		BUILDER.pop();
		SPEC = BUILDER.build();
	}
	//public static Vortex getVortex() {
	//	if (vortex_type.get() == VortexType.NONE) {
	//		int i = new Random().nextInt(VortexType.values().length);
	//		return VortexType.values()[i].vortex;
	//	}
	//	return vortex_type.get().vortex;
	//}
	//
	//public static boolean whiteHole() {
	//	return white_hole.get();
	//}
}
