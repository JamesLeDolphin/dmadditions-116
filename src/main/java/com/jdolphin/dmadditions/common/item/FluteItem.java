package com.jdolphin.dmadditions.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class FluteItem extends Item {
	SoundEvent[] sounds;
	public FluteItem(Properties properties) {
		super(properties);
		this.sounds = new SoundEvent[]{SoundEvents.NOTE_BLOCK_FLUTE, SoundEvents.NOTE_BLOCK_CHIME, SoundEvents.NOTE_BLOCK_BELL,
			SoundEvents.NOTE_BLOCK_PLING, SoundEvents.NOTE_BLOCK_XYLOPHONE};
	}

	@Override
	public @NotNull UseAction getUseAnimation(@NotNull ItemStack stack) {
		return UseAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!world.isClientSide()) {
			SoundEvent sound = sounds[player.getRandom().nextInt(sounds.length)];
			world.playSound(null, player.blockPosition(), sound, SoundCategory.PLAYERS, 1.0f, 1.0f);
		}
		world.addParticle(ParticleTypes.NOTE,  player.getRandomX(1), player.getY() + 0.7, player.getRandomZ(1),
			255,0,0);

		return ActionResult.pass(stack);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 7200;
	}
}
