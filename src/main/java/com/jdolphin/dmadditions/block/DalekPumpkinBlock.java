package com.jdolphin.dmadditions.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.PumpkinBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import static com.jdolphin.dmadditions.init.DMABlocks.CARVED_DALEK_PUMPKIN;

public class DalekPumpkinBlock extends PumpkinBlock {
	public DalekPumpkinBlock(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
		ItemStack itemstack = playerEntity.getItemInHand(hand);
		if (itemstack.getItem() == Items.SHEARS) {
			if (!world.isClientSide) {
				Direction direction = blockRayTraceResult.getDirection();
				Direction direction1 = direction.getAxis() == Direction.Axis.Y ? playerEntity.getDirection().getOpposite() : direction;
				world.playSound((PlayerEntity) null, blockPos, SoundEvents.PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.setBlock(blockPos, CARVED_DALEK_PUMPKIN.get().defaultBlockState().setValue(CarvedPumpkinBlock.FACING, direction1), 11);
				ItemEntity itementity = new ItemEntity(world, (double) blockPos.getX() + 0.5D + (double) direction1.getStepX() * 0.65D, (double) blockPos.getY() + 0.1D, (double) blockPos.getZ() + 0.5D + (double) direction1.getStepZ() * 0.65D, new ItemStack(Items.PUMPKIN_SEEDS, 4));
				itementity.setDeltaMovement(0.05D * (double) direction1.getStepX() + world.random.nextDouble() * 0.02D, 0.05D, 0.05D * (double) direction1.getStepZ() + world.random.nextDouble() * 0.02D);
				world.addFreshEntity(itementity);
				itemstack.hurtAndBreak(1, playerEntity, (p_220282_1_) -> {
					p_220282_1_.broadcastBreakEvent(hand);
				});
			}

			return ActionResultType.sidedSuccess(world.isClientSide);
		} else {
			return super.use(blockState, world, blockPos, playerEntity, hand, blockRayTraceResult);
		}
	}
}
