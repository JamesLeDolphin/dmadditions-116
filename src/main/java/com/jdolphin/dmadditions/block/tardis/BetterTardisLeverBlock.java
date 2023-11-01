package com.jdolphin.dmadditions.block.tardis;

import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeverBlock;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BetterTardisLeverBlock extends LeverBlock{
	public BetterTardisLeverBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, Boolean.FALSE).setValue(FACE, AttachFace.FLOOR));
	}
    
	public void switchLever(BlockState state, World worldIn, BlockPos pos) {
		worldIn.setBlockAndUpdate(pos, state.setValue(POWERED, !(Boolean) state.getValue(POWERED)));
		worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), DMSoundEvents.TARDIS_CONTROLS_LEVER.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
		this.updateNeighbours(state, worldIn, pos);
	}

	public void updateNeighbours(BlockState state, World world, BlockPos pos) {
		world.updateNeighborsAt(pos, this);
		world.updateNeighborsAt(pos.relative(getConnectedDirection(state).getOpposite()), this);
	}
}
