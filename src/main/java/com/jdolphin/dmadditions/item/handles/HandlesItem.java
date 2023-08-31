package com.jdolphin.dmadditions.item.handles;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class HandlesItem extends Item {
	public HandlesItem(Item.Properties settings) {
		super(settings);
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (!world.isClientSide) {
			// Open the chat interface for the player to type their question
			player.sendMessage(new StringTextComponent("You interacted with the Handles item!"), player.getUUID());
			// You can implement further logic to handle communication with ChatGPTIntegration here
		}
		return ActionResult.success(player.getItemInHand(hand));
	}
}
