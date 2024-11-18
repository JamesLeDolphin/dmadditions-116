package com.jdolphin.dmadditions.world.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class AndrozaniminorCarver extends CaveWorldCarver {
	public AndrozaniminorCarver(Codec<ProbabilityConfig> codec) {
		super(codec, 256);
		this.replaceableBlocks = ImmutableSet.of(Blocks.SNOW_BLOCK, Blocks.GRASS_BLOCK);
	}
}
