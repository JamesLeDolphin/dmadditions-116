package com.jdolphin.dmadditions.common.block.tardis;

import com.jdolphin.dmadditions.common.block.IHorizontalFaceBlock;
import com.swdteam.common.block.AbstractRotateableWaterLoggableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;

public class RotateableBlock extends AbstractRotateableWaterLoggableBlock implements IHorizontalFaceBlock {

	public RotateableBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACE);
	}
}
