package com.jdolphin.dmadditions.common.world.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class WinterPlanetCarver extends CaveWorldCarver {
	public WinterPlanetCarver(Codec<ProbabilityConfig> codec) {
		super(codec, 256);
		this.replaceableBlocks = ImmutableSet.of(Blocks.SNOW_BLOCK, Blocks.GRASS_BLOCK);
	}
}
