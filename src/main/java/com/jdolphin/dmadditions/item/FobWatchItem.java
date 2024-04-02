package com.jdolphin.dmadditions.item;

import com.jdolphin.dmadditions.init.DMACapabilities;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.util.ChatUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class FobWatchItem extends Item {

	public FobWatchItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!world.isClientSide()) {
			Helper.print("interacted");
			player.getCapability(DMACapabilities.PLAYER_DATA).ifPresent(cap -> {
				if (player.isShiftKeyDown() && stack.getDamageValue() < 12) {
					if (cap.hasRegens()) {
						cap.addRegens(-1);
						stack.setDamageValue(stack.getDamageValue() + 1);
						Helper.print("took a regen");
					}
				}
				if (!player.isShiftKeyDown() && stack.getDamageValue() > 0) {
					cap.addRegens(1);
					Helper.print("gave a regen");
					stack.setDamageValue(stack.getDamageValue() - 1);
				}
			});
		}
		return ActionResult.success(stack);
	}
}
