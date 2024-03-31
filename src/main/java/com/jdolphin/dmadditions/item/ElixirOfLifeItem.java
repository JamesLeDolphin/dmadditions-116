package com.jdolphin.dmadditions.item;

import com.jdolphin.dmadditions.init.DMACapabilities;
import com.swdteam.util.ChatUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ElixirOfLifeItem extends Item {
	public ElixirOfLifeItem(Properties properties) {
		super(properties);
	}

	public UseAction getUseAnimation(ItemStack stack) {
		return UseAction.DRINK;
	}

	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!world.isClientSide()) {
			player.getCapability(DMACapabilities.PLAYER_DATA).ifPresent(cap -> {
				cap.setRegens(cap.getMaxRegens());
				ChatUtil.sendMessageToPlayer(player, "You've gained 12 regenerations", ChatUtil.MessageType.CHAT);
			});
			if (!player.isCreative()) {
				stack.shrink(1);
				return ActionResult.consume(stack);
			}
		} return ActionResult.pass(stack);
	}
}
