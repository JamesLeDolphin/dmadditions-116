package com.jdolphin.dmadditions.block.christmas;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;

public class CandyCaneBlock extends DirectionalBlock {
	public CandyCaneBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> blockBlockStateBuilder) {
		blockBlockStateBuilder.add(FACING);
	}
	public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
		return this.defaultBlockState().setValue(FACING, blockItemUseContext.getNearestLookingDirection().getOpposite());
	}
}
