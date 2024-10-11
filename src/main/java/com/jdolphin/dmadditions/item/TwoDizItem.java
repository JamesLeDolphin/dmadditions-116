package com.jdolphin.dmadditions.item;

import com.swdteam.util.ChatUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class TwoDizItem extends Item {
	public static String ENTITY_WIDTH = "entity_width";

	public TwoDizItem(Properties properties) {
		super(properties);
	}

	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
		CompoundNBT tag = entity.getPersistentData();

			if (!tag.contains(ENTITY_WIDTH)) {
				tag.putFloat(ENTITY_WIDTH, 0.9f);
			}
			float width = tag.getFloat(ENTITY_WIDTH);
			if (width >= 0.1) {
				tag.putFloat(ENTITY_WIDTH, width - 0.1f);
			} else {
				tag.putFloat(ENTITY_WIDTH, 1);
			}

		entity.refreshDimensions();
		return super.interactLivingEntity(stack, player, entity, hand);
	}

	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		CompoundNBT playerTag = player.getPersistentData();
		if (playerTag.contains(ENTITY_WIDTH) && playerTag.getFloat(ENTITY_WIDTH) < 1 && player.isShiftKeyDown()) {
			playerTag.putFloat(ENTITY_WIDTH, 1);
			ChatUtil.sendCompletedMsg(player, new TranslationTextComponent("notice.dmadditions.size_reset"), ChatUtil.MessageType.STATUS_BAR);
			return ActionResult.success(player.getItemInHand(hand));
		}
		return ActionResult.fail(player.getItemInHand(hand));
	}
}
