package com.jdolphin.dmadditions.block.christmas;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ChristmasPuddingBlock extends Block {
	public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, 1);
	protected static final VoxelShape[] SHAPE_BY_BITE = new VoxelShape[]{Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.box(3.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D)};

	public ChristmasPuddingBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(BITES, Integer.valueOf(0)));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> blockBlockStateBuilder) {
		blockBlockStateBuilder.add(BITES);
	}

	@Override
	public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
		return SHAPE_BY_BITE[blockState.getValue(BITES)];
	}

	public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
		if (world.isClientSide) {
			ItemStack itemstack = playerEntity.getItemInHand(hand);
			if (this.eat(world, blockPos, blockState, playerEntity).consumesAction()) {
				return ActionResultType.SUCCESS;
			}

			if (itemstack.isEmpty()) {
				return ActionResultType.CONSUME;
			}
		}

		return this.eat(world, blockPos, blockState, playerEntity);
	}

	private ActionResultType eat(IWorld iWorld, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
		if (!playerEntity.canEat(false)) {
			return ActionResultType.PASS;
		} else {
			playerEntity.awardStat(Stats.EAT_CAKE_SLICE);
			playerEntity.getFoodData().eat(2, 0.1F);
			int i = blockState.getValue(BITES);
			if (i < 1) {
				iWorld.setBlock(blockPos, blockState.setValue(BITES, Integer.valueOf(i + 1)), 3);
			} else {
				iWorld.removeBlock(blockPos, false);
			}

			return ActionResultType.SUCCESS;
		}
	}
}