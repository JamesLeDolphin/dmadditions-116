package com.jdolphin.dmadditions.block.tardis;

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
	public void setPlacedBy(World world, BlockPos blockPos, BlockState blockState, @javax.annotation.Nullable LivingEntity livingEntity, ItemStack itemStack) {
		if (itemStack.hasCustomHoverName()) {
			TileEntity tileentity = world.getBlockEntity(blockPos);
			if (tileentity instanceof BarrelTileEntity) {
				((BarrelTileEntity)tileentity).setCustomName(itemStack.getHoverName());
			}
		}
	}

	public boolean hasAnalogOutputSignal(BlockState blockState) {
		return true;
	}

	public int getAnalogOutputSignal(BlockState blockState, World world, BlockPos blockPos) {
		return Container.getRedstoneSignalFromBlockEntity(world.getBlockEntity(blockPos));
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
		public boolean skipRendering(BlockState blockState, BlockState blockState1, Direction direction) {
			return blockState1.is(this) || super.skipRendering(blockState, blockState1, direction);
		}

		public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
			return VoxelShapes.box(0.0, 0.0, 0.0, 0.9999, 1.0, 0.9999);
		}

		public BlockState getStateForPlacement(BlockItemUseContext context) {
			BlockPos blockpos = context.getClickedPos();
			FluidState fluidstate = context.getLevel().getFluidState(blockpos);
			return super.getStateForPlacement(context).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
		}

		public BlockState updateShape(BlockState p_196271_1_, Direction direction, BlockState blockState, IWorld iWorld, BlockPos blockPos, BlockPos blockPos1) {
			if ((Boolean)p_196271_1_.getValue(WATERLOGGED)) {
				iWorld.getLiquidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(iWorld));
			}

			return super.updateShape(p_196271_1_, direction, blockState, iWorld, blockPos, blockPos1);
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
