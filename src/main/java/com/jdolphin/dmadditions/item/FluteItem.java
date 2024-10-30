package com.jdolphin.dmadditions.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class FluteItem extends Item {
	public FluteItem(Properties properties) {
		super(properties);
	}

	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (!world.isClientSide) {
			playFluteSound(world, player);
		}
		return ActionResult.success(player.getItemInHand(hand));
	}

	private void playFluteSound(World world, PlayerEntity player) {
		world.playSound(null, player.blockPosition(), SoundEvents.NOTE_BLOCK_FLUTE, SoundCategory.PLAYERS, 1.0F, 1.0F);
		world.playSound(null, player.blockPosition(), SoundEvents.NOTE_BLOCK_CHIME, SoundCategory.PLAYERS, 1.0F, 1.2F);
		world.playSound(null, player.blockPosition(), SoundEvents.NOTE_BLOCK_BASS, SoundCategory.PLAYERS, 1.0F, 1.5F);
	}
}
