package com.jdolphin.dmadditions.world.carver;

import com.google.common.collect.ImmutableSet;
import com.jdolphin.dmadditions.init.DMABlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

public class GallifreyCarver extends CaveWorldCarver {
	public GallifreyCarver(Codec<ProbabilityConfig> codec) {
		super(codec, 256);
		this.replaceableBlocks = ImmutableSet.of(DMABlocks.GALLIFREY_STONE.get(), Blocks.GRASS_BLOCK, Blocks.RED_SAND);
	}
}
