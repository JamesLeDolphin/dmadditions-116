package com.jdolphin.dmadditions.block.tardis;

import com.jdolphin.dmadditions.init.DMASoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CopperHandbrake extends BetterFlightLeverBlock {
	public CopperHandbrake(Properties properties) {
		super(properties);
	}

	public void switchLever(BlockState state, World worldIn, BlockPos pos) {
		worldIn.setBlockAndUpdate(pos, (BlockState)state.setValue(POWERED, !(Boolean)state.getValue(POWERED)));
		worldIn.playSound((PlayerEntity)null, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (SoundEvent)DMASoundEvents.COPPER_HANDBRAKE.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
	}
}
