package com.jdolphin.dmadditions.block;

import com.swdteam.common.block.AbstractRotateableWaterLoggableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

public interface IBetterPanel extends IHorizontalFaceBlock, IBetterBlockTooltip {
	DirectionProperty FACING = AbstractRotateableWaterLoggableBlock.FACING;

	VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
	VoxelShape WEST_AABB = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
	VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
	VoxelShape UP_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
	VoxelShape DOWN_AABB = Block.box(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);

	@Override
	default BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState1, IWorld world, BlockPos blockPos, BlockPos blockPos1) {
		return getConnectedDirection(blockState).getOpposite() == direction && !blockState.canSurvive(world, blockPos) ? Blocks.AIR.defaultBlockState() : blockState;
	}

	@Override
	default boolean canSurvive(BlockState blockState, IWorldReader worldReader, BlockPos pos) {
		if (blockState.is(Blocks.AIR)) return false;

		return IHorizontalFaceBlock.canAttach(worldReader, pos, getConnectedDirection(blockState).getOpposite());
	}

	@Override
	default VoxelShape getShape(BlockState state, IBlockReader blockReader, BlockPos pos, ISelectionContext selectionContext) {
		switch (state.getValue(FACE)) {
			case FLOOR:
				return UP_AABB;
			case WALL:
				switch (state.getValue(FACING)) {
					case EAST:
						return EAST_AABB;
					case WEST:
						return WEST_AABB;
					case SOUTH:
						return SOUTH_AABB;
					case NORTH:
					default:
						return NORTH_AABB;
				}
			case CEILING:
			default:
				return DOWN_AABB;
		}
	}

	@Override
	default VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return getShape(state, worldIn, pos, context);
	}
	default FluidState getFluidState(BlockState state) {
		return (Boolean)state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Nullable
	@Override
	default BlockState getStateForPlacement(BlockItemUseContext context) {
		Direction direction = context.getClickedFace();
		BlockPos blockpos = context.getClickedPos();
		FluidState fluidstate = context.getLevel().getFluidState(blockpos);
		BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos().relative(direction.getOpposite()));
		return blockstate.is() && blockstate.getValue(FACING) == direction ? ((BlockState)this.defaultBlockState().setValue(FACING, direction.getOpposite())).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER) : (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, direction)).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
	}

	@Override
	default ITextComponent getName(BlockState blockState, BlockPos blockPos, Vector3d vector3d, PlayerEntity playerEntity) {
		return IBetterBlockTooltip.super.getName(blockState, blockPos, vector3d, playerEntity);
	}
}
