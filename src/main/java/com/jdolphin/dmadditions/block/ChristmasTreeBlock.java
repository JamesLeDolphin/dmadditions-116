package com.jdolphin.dmadditions.block;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ChristmasTreeBlock extends Block {

	public ChristmasTreeBlock(Properties p_i48440_1_) {
		super(p_i48440_1_);
	}

	@Override
	public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
		return p_196271_2_ == Direction.DOWN && !this.canSurvive(p_196271_1_, p_196271_4_, p_196271_5_)
			? Blocks.AIR.defaultBlockState() : super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos blockPos) {
		return canSupportCenter(reader, blockPos.below(), Direction.UP);
	}

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
		return VoxelShapes.or(VoxelShapes.box(0.1d, 0d, 0.1d, 0.9d, 1d, 0.9d),
			VoxelShapes.box(0.3d, 1d, 0.3d, 0.7d, 1.8d, 0.7d));
	}

	@Override
	public void onRemove(BlockState p_196243_1_, World p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_, boolean p_196243_5_) {
		/* TODO: spawn entity
			It should also not spawn in creative,
			and it shouldn't spawn if you have silk touch
		*/
	}
}
