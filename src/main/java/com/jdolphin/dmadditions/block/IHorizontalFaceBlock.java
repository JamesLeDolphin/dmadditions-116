package com.jdolphin.dmadditions.block;


import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.extensions.IForgeBlock;

import javax.annotation.Nullable;

public interface IHorizontalFaceBlock extends IForgeBlock {
	EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;
	DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	BlockState defaultBlockState = null;

	default boolean canSurvive(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
		return canAttach(p_196260_2_, p_196260_3_, getConnectedDirection(p_196260_1_).getOpposite());
	}

	static boolean canAttach(IWorldReader p_220185_0_, BlockPos p_220185_1_, Direction p_220185_2_) {
		BlockPos blockpos = p_220185_1_.relative(p_220185_2_);
		return p_220185_0_.getBlockState(blockpos).isFaceSturdy(p_220185_0_, blockpos, p_220185_2_.getOpposite());
	}

	@Nullable
	default BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
		for (Direction direction : p_196258_1_.getNearestLookingDirections()) {
			BlockState blockstate;
			if (direction.getAxis() == Direction.Axis.Y) {
				blockstate = this.defaultBlockState().setValue(FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR).setValue(FACING, p_196258_1_.getHorizontalDirection());
			} else {
				blockstate = this.defaultBlockState().setValue(FACE, AttachFace.WALL).setValue(FACING, direction.getOpposite());
			}

			if (blockstate.canSurvive(p_196258_1_.getLevel(), p_196258_1_.getClickedPos())) {
				return blockstate;
			}
		}

		return null;
	}

	default BlockState defaultBlockState() {
		return IHorizontalFaceBlock.defaultBlockState;
	}

	default Direction getConnectedDirection(BlockState state) {
		switch (state.getValue(FACE)) {
			case CEILING:
				return Direction.DOWN;
			case FLOOR:
				return Direction.UP;
			default:
				return state.getValue(FACING);
		}
	}

	VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context);

	default VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return getShape(state, worldIn, pos, context);
	}

	default BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState1, IWorld world, BlockPos blockPos, BlockPos blockPos1) {
		return getConnectedDirection(blockState).getOpposite() == direction && !blockState.canSurvive(world, blockPos) ? Blocks.AIR.defaultBlockState() : blockState;
	}
}
