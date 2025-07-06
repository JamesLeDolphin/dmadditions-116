package com.jdolphin.dmadditions.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class EngineBlock extends HorizontalBlock {

	public EngineBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public VoxelShape getShape(BlockState blockstate, IBlockReader reader, BlockPos pos,
							   ISelectionContext context) {
		switch (blockstate.getValue(FACING)) {
			case NORTH:
			case SOUTH:
				return VoxelShapes.or(Block.box(4, 0, 2, 12, 3, 14), Block.box(5, 3, 2, 11, 5, 14));
			case EAST:
			case WEST:
			default:
				return VoxelShapes.or(Block.box(2, 0, 4, 14, 3, 12), Block.box(2, 3, 5, 14, 5, 11));

		}
	}
}
