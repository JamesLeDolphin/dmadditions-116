package com.jdolphin.dmadditions.block;

import com.jdolphin.dmadditions.init.DMABlocks;
import com.swdteam.common.init.DMTags;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.util.ChatUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public interface IRustToo {
	BooleanProperty WAXED = BooleanProperty.create("waxed");
	Map<Block, Block> rustedMap = new HashMap();
	static void addRustedVariants() {
		rustedMap.put(DMABlocks.STEEL_BEAMS_ROUNDEL_CONTAINER.get(), DMABlocks.RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER.get());
		rustedMap.put(DMABlocks.STEEL_BEAMS_ROUNDEL_CONTAINER.get(), DMABlocks.RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER.get());
		rustedMap.put(DMABlocks.FILLED_STEEL_BEAMS_ROUNDEL_CONTAINER.get(), DMABlocks.FILLED_RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER.get());
		rustedMap.put(DMABlocks.STEEL_REINFORCED_WALLING_ROUNDEL_CONTAINER.get(), DMABlocks.RUSTED_STEEL_REINFORCED_WALLING_ROUNDEL_CONTAINER.get());

	}
	default BlockState getRustedState(BlockState state) {
		return rustedMap.get(state.getBlock()) == null ? null : rustedMap.get(state.getBlock()).defaultBlockState();
	}

	default boolean wax(World world, BlockPos pos, PlayerEntity player, Hand hand) {
		BlockState state = world.getBlockState(pos);
		ItemStack itemstack = player.getItemInHand(hand);
		if (state.getValue(WAXED)) {
			if (!world.isClientSide) {
				ChatUtil.sendError(player, DMTranslationKeys.BLOCK_WAX_ALREADY_WAXED, ChatUtil.MessageType.CHAT);
			}

			return false;
		} else {
			if (!world.isClientSide) {
				ChatUtil.sendCompletedMsg(player, DMTranslationKeys.BLOCK_WAX_COMPLETED, ChatUtil.MessageType.CHAT);
			}

			if (!player.isCreative()) {
				itemstack.shrink(1);
			}

			world.setBlockAndUpdate(pos, state.setValue(WAXED, true));
			world.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundCategory.BLOCKS, 0.5F, 1.0F);
			return true;
		}
	}

	default void rustTick(IRustToo rustState, BlockState state, ServerWorld world, BlockPos pos, Random rand) {
		if (!world.isClientSide && rustState.getRustedState(state) != null) {
			boolean isWaxed = state.getValue(WAXED);
			if (!isWaxed) {
				boolean isRainedOn = world.isRainingAt(pos.above());
				boolean isWaterLogged = state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED);
				if (!isRainedOn && !isWaterLogged) {
					Direction[] var9 = Direction.values();
					int var10 = var9.length;

					for(int var11 = 0; var11 < var10; ++var11) {
						Direction dir = var9[var11];
						BlockPos neighborPos = pos.relative(dir);
						Block neighborBlock = world.getBlockState(neighborPos).getBlock();
						boolean nextToRust = DMTags.Blocks.RUSTED_BLOCKS.contains(neighborBlock);
						boolean nextToWater = FluidTags.WATER.contains(world.getFluidState(neighborPos).getType());
						if (nextToRust || nextToWater) {
							world.setBlockAndUpdate(pos, rustState.getRustedState(state));
							return;
						}
					}

					return;
				}

				world.setBlockAndUpdate(pos, rustState.getRustedState(state));
			}
		}

	}
}
