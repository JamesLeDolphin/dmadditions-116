package com.jdolphin.dmadditions.block.christmas;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class ChristmasCrackerBlock extends HorizontalBlock {
	public static final VoxelShape SHAPE_NORTH = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 3.0D, 10.0D);
	public static final VoxelShape SHAPE_EAST = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 3.0D, 16.0D);

	public ChristmasCrackerBlock(Properties p_i48377_1_) {
		super(p_i48377_1_);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos,
			ISelectionContext context) {
		switch (state.getValue(HorizontalBlock.FACING)) {
			case NORTH:
			case SOUTH:
				return SHAPE_NORTH;

			case EAST:
			case WEST:
			default:
				return SHAPE_EAST;
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
		return this.defaultBlockState().setValue(FACING, p_196258_1_.getHorizontalDirection().getOpposite());
	}

	@Override
	public boolean canSurvive(BlockState blockstate, IWorldReader worldReader, BlockPos pos) {
		return worldReader.getBlockState(pos.below()).isFaceSturdy(worldReader, pos, Direction.UP);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HorizontalBlock.FACING);
	}

}
