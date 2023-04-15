package com.jdolphin.dmadditions.block;

import com.jdolphin.dmadditions.tileentity.RoundelContainerTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class RustableRoundelContainerBlock extends RoundelContainerBlock implements IRustToo {
	public RustableRoundelContainerBlock(AbstractBlock.Properties properties) {
		super(properties.randomTicks());
		this.registerDefaultState(super.defaultBlockState().setValue(WAXED, false));
	}


	public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
		if (world.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			TileEntity tileentity = world.getBlockEntity(blockPos);
			if (tileentity instanceof RoundelContainerTileEntity) {
				playerEntity.openMenu((RoundelContainerTileEntity)tileentity);
				PiglinTasks.angerNearbyPiglins(playerEntity, true);
			}

			return ActionResultType.CONSUME;
		}
	}


	@Nullable
	public TileEntity newBlockEntity(IBlockReader blockReader) {
		return new RoundelContainerTileEntity();
	}


	public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
		TileEntity tileentity = serverWorld.getBlockEntity(blockPos);

		if (tileentity instanceof RoundelContainerTileEntity) {
			((RoundelContainerTileEntity) tileentity).recheckOpen();
		}
	}


	public BlockState getStateForPlacement(BlockItemUseContext context) {
		double rot = context.getHorizontalDirection().getOpposite().toYRot() + (float) (context.getPlayer().isShiftKeyDown() ? 90 : 0);
		return this.defaultBlockState().setValue(FACING, Direction.fromYRot(rot));
	}

	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
		this.rustTick(this, state, world, pos, rand);
	}


	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> state) {
		super.createBlockStateDefinition(state);
		state.add(WAXED);
	}

	public float getSlipperiness(BlockState state, IWorldReader world, BlockPos pos, Entity entity) {
		return state.getValue(WAXED) ? 0.9F : super.getSlipperiness(state, world, pos, entity);
	}


	public static class WaterLoggableTransparent extends RustableRoundelContainerBlock implements IWaterLoggable {
		public static final BooleanProperty WATERLOGGED;

		public WaterLoggableTransparent(AbstractBlock.Properties properties) {
			super(properties);
			this.registerDefaultState(super.defaultBlockState().setValue(WATERLOGGED, false));
		}

		public BlockState getRustedState(BlockState state) {
			return super.getRustedState(state).setValue(WATERLOGGED, state.getValue(WATERLOGGED));
		}

		@OnlyIn(Dist.CLIENT)
		public boolean skipRendering(BlockState p_200122_1_, BlockState p_200122_2_, Direction p_200122_3_) {
			return p_200122_2_.is(this) ? true : super.skipRendering(p_200122_1_, p_200122_2_, p_200122_3_);
		}

		public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
			return VoxelShapes.box(0.0, 0.0, 0.0, 0.9999, 1.0, 0.9999);
		}

		public BlockState getStateForPlacement(BlockItemUseContext context) {
			BlockPos blockpos = context.getClickedPos();
			FluidState fluidstate = context.getLevel().getFluidState(blockpos);
			return super.getStateForPlacement(context).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
		}

		public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
			if (p_196271_1_.getValue(WATERLOGGED)) {
				p_196271_4_.getLiquidTicks().scheduleTick(p_196271_5_, Fluids.WATER, Fluids.WATER.getTickDelay(p_196271_4_));
			}

			return super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
		}

		public FluidState getFluidState(BlockState state) {
			return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
		}

		protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> state) {
			super.createBlockStateDefinition(state);
			state.add(WATERLOGGED);
		}

		static {
			WATERLOGGED = BlockStateProperties.WATERLOGGED;
		}
	}
}
