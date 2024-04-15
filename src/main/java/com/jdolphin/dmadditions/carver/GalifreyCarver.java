package com.jdolphin.dmadditions.carver;

import com.google.common.collect.ImmutableSet;
import com.jdolphin.dmadditions.init.DMABlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class GalifreyCarver extends CaveWorldCarver {
	public GalifreyCarver(Codec<ProbabilityConfig> codec) {
		super(codec, 256);
		this.replaceableBlocks = ImmutableSet.of(DMABlocks.CROCITE.get(), Blocks.GRASS_BLOCK, Blocks.RED_SAND);
	}
}
