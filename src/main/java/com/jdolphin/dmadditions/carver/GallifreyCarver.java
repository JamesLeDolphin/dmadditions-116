package com.jdolphin.dmadditions.carver;

import com.google.common.collect.ImmutableSet;
import com.jdolphin.dmadditions.init.DMABlocks;
import com.jdolphin.dmadditions.init.DMAWorldCarvers;
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

	public void apply(Random random, IChunk chunk, Biome biome, int i, int i1, int i2, double d, BlockState state, BlockState state1, int j, long l, SurfaceBuilderConfig config) {
		if (d > 1.0D) {
			SurfaceBuilder.DEFAULT.apply(random, chunk, biome, i, i1, i2, d, state, state1, j, l, SurfaceBuilder.CONFIG_STONE);
		} else {
			SurfaceBuilder.DEFAULT.apply(random, chunk, biome, i, i1, i2, d, state, state1, j, l, SurfaceBuilder.CONFIG_GRASS);
		}

	}
}
