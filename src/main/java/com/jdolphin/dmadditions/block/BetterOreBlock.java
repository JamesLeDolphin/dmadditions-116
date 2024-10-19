package com.jdolphin.dmadditions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

public class BetterOreBlock extends Block {
	private final int minXP;
	private final int maxXP;

	public BetterOreBlock(Properties properties, int minXp, int maxXp) {
		super(properties);
		this.minXP = minXp;
		this.maxXP = maxXp;
	}

	public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silk) {
		int multiplier = fortune == 0 ? 1 : fortune + 1;
		int min = Math.min(minXP, maxXP) * multiplier;
		int max = Math.max(minXP, maxXP) * multiplier;
		return silk == 0 ? MathHelper.nextInt(this.RANDOM, min, max) : 0;
	}
}
