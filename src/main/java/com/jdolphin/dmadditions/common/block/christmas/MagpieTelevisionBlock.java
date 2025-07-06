package com.jdolphin.dmadditions.common.block.christmas;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MagpieTelevisionBlock extends HorizontalBlock {
	public static final VoxelShape SHAPE_NS = Block.box(0, 0, 2.5, 16, 13, 13.5);
	public static final VoxelShape SHAPE_EW = Block.box(2.5, 0, 0, 13.5, 13, 16);

	public MagpieTelevisionBlock(Properties builder) {
		super(builder);
	}

	public static final IntegerProperty CHANNEL = IntegerProperty.create("channel", 0, 5);
	public static final BooleanProperty ON = BooleanProperty.create("on");

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		switch (state.getValue(HorizontalBlock.FACING)) {
			case EAST:
			case WEST:
				return SHAPE_EW;
			default:
				return SHAPE_NS;
		}
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
		builder.add(CHANNEL);
		builder.add(ON);
		super.createBlockStateDefinition(builder);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState()
			.setValue(FACING, context.getHorizontalDirection().getOpposite())
			.setValue(CHANNEL, 0)
			.setValue(ON, false);
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!world.isClientSide) {
			if (player.isCrouching()) {
				world.setBlockAndUpdate(pos, state.setValue(ON, !state.getValue(ON)));
				world.playSound(null, pos, SoundEvents.CAT_HISS, SoundCategory.BLOCKS, 0.2f, 20f);
			}
			if (state.getValue(ON) && !player.isCrouching()) {
				int current = state.getValue(CHANNEL);
				world.setBlockAndUpdate(pos, state.setValue(CHANNEL, current == 5 ? 0 : current + 1));
				world.playSound(null, pos, SoundEvents.CAT_HISS, SoundCategory.BLOCKS, 0.2f, 20f);
			}
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.FAIL;
	}

	public void neighborChanged(BlockState state, World world, BlockPos blockPos, Block block, BlockPos blockPos1, boolean isMoving) {
		if (!world.isClientSide) {
			boolean on = state.getValue(ON);
			boolean hasSignal = world.hasNeighborSignal(blockPos);
			if (on != hasSignal) {
				if (on) {
					world.setBlockAndUpdate(blockPos, state.setValue(ON, true));
				} else {
					world.setBlockAndUpdate(blockPos, state.setValue(ON, false));
				}
			}
		}
	}

}

//If you want to add another channel, increment the maximum value for CHANNEL,
//add a 4:3 but square texture to the tv_channels directory, add a model in the magpie
//directory which parents from magpie_television.json and overrides the "channel"
//texture, then copy the blockstates used for each channel but change what number
//channel is at and set the model to your channel's model

//For animated channels make an animated texture with each frame as 4:3 but square. Keep in mind
//you can only really go up to 20fps.
