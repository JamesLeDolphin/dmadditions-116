package com.jdolphin.dmadditions.block;

import com.swdteam.common.block.AbstractRotateableWaterLoggableBlock;
import com.swdteam.common.block.RotatableTileEntityBase;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

import java.util.function.Supplier;

import static com.swdteam.common.block.AbstractRotateableWaterLoggableBlock.WATERLOGGED;

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

	@Nullable
	@Override
	default BlockState getStateForPlacement(BlockItemUseContext context) {
		Direction direction = context.getClickedFace();
		BlockPos blockpos = context.getClickedPos();
		FluidState fluidstate = context.getLevel().getFluidState(blockpos);
		BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos().relative(direction.getOpposite()));
		return blockstate.getValue(FACING) == direction ? this.defaultBlockState().setValue(FACING, direction.getOpposite()).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER) : this.defaultBlockState().setValue(FACING, direction).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
	}

	@Override
	default ITextComponent getName(BlockState blockState, BlockPos blockPos, Vector3d vector3d, PlayerEntity playerEntity) {
		return IBetterBlockTooltip.super.getName(blockState, blockPos, vector3d, playerEntity);
	}
	public static class WaterLoggable extends RotatableTileEntityBase implements IWaterLoggable {
		public static final BooleanProperty WATERLOGGED;

		public WaterLoggable(Supplier<TileEntity> tileEntitySupplier, int light) {
			super(tileEntitySupplier, light);
			this.registerDefaultState((BlockState)super.defaultBlockState().setValue(WATERLOGGED, false));
		}

		public WaterLoggable(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
			super(tileEntitySupplier, properties);
			this.registerDefaultState((BlockState)super.defaultBlockState().setValue(WATERLOGGED, false));
		}

		public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
			return VoxelShapes.box(0.0, 0.0, 0.0, 0.9999, 1.0, 0.9999);
		}

		public BlockState getStateForPlacement(BlockItemUseContext context) {
			BlockPos blockpos = context.getClickedPos();
			FluidState fluidstate = context.getLevel().getFluidState(blockpos);
			return (BlockState)super.getStateForPlacement(context).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
		}

		public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
			if ((Boolean)p_196271_1_.getValue(WATERLOGGED)) {
				p_196271_4_.getLiquidTicks().scheduleTick(p_196271_5_, Fluids.WATER, Fluids.WATER.getTickDelay(p_196271_4_));
			}

			return super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
		}

		public FluidState getFluidState(BlockState state) {
			return (Boolean)state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
		}

		protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> state) {
			super.createBlockStateDefinition(state);
			state.add(new Property[]{WATERLOGGED});
		}

		static {
			WATERLOGGED = BlockStateProperties.WATERLOGGED;
		}
	}
}
