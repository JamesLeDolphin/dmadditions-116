package com.jdolphin.dmadditions.common.block.tardis;

import com.jdolphin.dmadditions.common.init.DMASoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CoralHandbrake extends BetterFlightLeverBlock {
	public CoralHandbrake(Properties properties) {
		super(properties);
	}

	public void switchLever(BlockState state, World worldIn, BlockPos pos) {
		worldIn.setBlockAndUpdate(pos, state.setValue(POWERED, !(Boolean) state.getValue(POWERED)));
		worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), DMASoundEvents.CORAL_HANDBRAKE.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
	}
}
