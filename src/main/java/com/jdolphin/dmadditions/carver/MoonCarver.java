package com.jdolphin.dmadditions.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.swdteam.common.init.DMBlocks;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class MoonCarver extends CaveWorldCarver {
	public MoonCarver(Codec<ProbabilityConfig> codec) {
		super(codec, 256);
		this.replaceableBlocks = ImmutableSet.of(DMBlocks.ANORTHOSITE.get(), DMBlocks.METEORITE.get());
	}
}
