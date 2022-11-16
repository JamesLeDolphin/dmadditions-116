package com.jdolphin.dmadditions.carver;

import com.mojang.serialization.Codec;
import com.swdteam.common.carver.ClassicWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class MoonCarver extends ClassicWorldCarver {
	public MoonCarver(Codec<ProbabilityConfig> codec) {
		super(codec);
	}
}
