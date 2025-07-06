package com.jdolphin.dmadditions.common.world.carver;

import com.google.common.collect.ImmutableSet;
import com.jdolphin.dmadditions.common.init.DMABlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class GallifreyCarver extends CaveWorldCarver {
	public GallifreyCarver(Codec<ProbabilityConfig> codec) {
		super(codec, 256);
		this.replaceableBlocks = ImmutableSet.of(DMABlocks.GALLIFREY_STONE.get(), Blocks.GRASS_BLOCK, DMABlocks.GALLIFREY_SAND.get(), DMABlocks.GALLIFREY_SANDSTONE.get());
	}
}
