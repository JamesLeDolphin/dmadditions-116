package com.jdolphin.dmadditions.block.christmas;

import com.swdteam.common.item.DataModuleItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MagpieTelevisionBlock extends HorizontalBlock {
	public MagpieTelevisionBlock(Properties builder) {
		super(builder);
	}
	public static final IntegerProperty CHANNEL = IntegerProperty.create("channel", 0, 1);
	public static final BooleanProperty ON = BooleanProperty.create("on");
	private boolean powered = false;
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

		if (!world.isClientSide && handIn == Hand.MAIN_HAND && player.getItemInHand(handIn).isEmpty() && state.getValue(ON)){
			world.setBlockAndUpdate(pos, state.cycle(CHANNEL));
			return ActionResultType.SUCCESS;
		}

		return ActionResultType.FAIL;
	}

	public void neighborChanged(BlockState state, World world, BlockPos blockPos, Block block, BlockPos blockPos1, boolean isMoving) {
		if (!world.isClientSide) {
			boolean nPower = world.hasNeighborSignal(blockPos);

			if(powered != nPower && nPower){
				world.setBlockAndUpdate(blockPos, state.cycle(ON));
			}

			powered = nPower;
		}
	}

}

//If you want to add another channel, increment the maximum value for CHANNEL,
//add a 64x64 texture to the tv_channels directory, add a model in the magpie
//directory which parents from magpie_television.json and overrides the "channel"
//texture, then copy the blockstates used for each channel but change what number
//channel is at and set the model to your channel's model