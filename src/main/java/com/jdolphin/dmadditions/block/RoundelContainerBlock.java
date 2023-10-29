package com.jdolphin.dmadditions.block;

import com.jdolphin.dmadditions.tileentity.RoundelContainerTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.BarrelTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SuppressWarnings("deprecation")
public class RoundelContainerBlock extends ContainerBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;


	public RoundelContainerBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}
	@Override
	public BlockRenderType getRenderShape(BlockState p_149645_1_) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void onRemove(BlockState state, World world, BlockPos pos, BlockState state1, boolean boo) {
		if (!state.is(state1.getBlock())) {
			TileEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof IInventory) {
				InventoryHelper.dropContents(world, pos, (IInventory)tileentity);
				world.updateNeighbourForOutputSignal(pos, this);
			}

			super.onRemove(state, world, pos, state1, boo);
		}
	}

	@Override
	public void setPlacedBy(World p_180633_1_, BlockPos p_180633_2_, BlockState p_180633_3_, @javax.annotation.Nullable LivingEntity p_180633_4_, ItemStack p_180633_5_) {
		if (p_180633_5_.hasCustomHoverName()) {
			TileEntity tileentity = p_180633_1_.getBlockEntity(p_180633_2_);
			if (tileentity instanceof BarrelTileEntity) {
				((BarrelTileEntity)tileentity).setCustomName(p_180633_5_.getHoverName());
			}
		}
	}

	public boolean hasAnalogOutputSignal(BlockState p_149740_1_) {
		return true;
	}

	public int getAnalogOutputSignal(BlockState p_180641_1_, World p_180641_2_, BlockPos p_180641_3_) {
		return Container.getRedstoneSignalFromBlockEntity(p_180641_2_.getBlockEntity(p_180641_3_));
	}

	public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
		if (world.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			TileEntity tileentity = world.getBlockEntity(blockPos);
			if (tileentity instanceof RoundelContainerTileEntity) {
				playerEntity.playSound(SoundEvents.BARREL_OPEN, 1, 1);
				playerEntity.openMenu((RoundelContainerTileEntity)tileentity);
				playerEntity.awardStat(Stats.OPEN_BARREL);
				PiglinTasks.angerNearbyPiglins(playerEntity, true);
			}

			return ActionResultType.CONSUME;
		}
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}


	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> state) {
		state.add(FACING);
	}

	@Nullable
	public TileEntity newBlockEntity(IBlockReader blockReader) {
		return new RoundelContainerTileEntity();
	}

	public BlockState getStateForPlacement(BlockItemUseContext context) {
		double rot = context.getHorizontalDirection().getOpposite().toYRot() + (float) (context.getPlayer().isShiftKeyDown() ? 90 : 0);
		return this.defaultBlockState().setValue(FACING, Direction.fromYRot(rot));
	}

	public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
		TileEntity tileentity = serverWorld.getBlockEntity(blockPos);

		if (tileentity instanceof RoundelContainerTileEntity) {
			((RoundelContainerTileEntity) tileentity).recheckOpen();
		}
	}
	public static class WaterLoggable extends RoundelContainerBlock implements IWaterLoggable {
		public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

		public WaterLoggable(AbstractBlock.Properties properties) {
			super(properties);
			this.registerDefaultState((BlockState)super.defaultBlockState().setValue(WATERLOGGED, false));
		}

		@OnlyIn(Dist.CLIENT)
		public boolean skipRendering(BlockState p_200122_1_, BlockState p_200122_2_, Direction p_200122_3_) {
			return p_200122_2_.is(this) || super.skipRendering(p_200122_1_, p_200122_2_, p_200122_3_);
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
			state.add(WATERLOGGED);
		}
	}
}
