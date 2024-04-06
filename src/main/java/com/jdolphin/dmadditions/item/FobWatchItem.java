package com.jdolphin.dmadditions.item;

import com.jdolphin.dmadditions.init.DMACapabilities;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.util.ChatUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

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
				if (player.isSecondaryUseActive()) {
					if (cap.hasRegens() && stack.isDamaged()) {
						cap.removeRegens(1);
						int i = stack.getDamageValue();
						stack.setDamageValue(i - 1);
						Helper.print("took a regen");
					}
				}
				if (!player.isSecondaryUseActive() && stack.getDamageValue() < stack.getMaxDamage() && cap.getRegens() < 12) {
					cap.addRegens(1);
					Helper.print("gave a regen");
					stack.hurt(1, world.random, (ServerPlayerEntity) player);
				}
			});
		}
		return ActionResult.success(stack);
	}

	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> textComponents, ITooltipFlag flag) {
		textComponents.add(new StringTextComponent(stack.getMaxDamage() - stack.getDamageValue() + " regenerations").withStyle(TextFormatting.DARK_GRAY));
	}


	public int getRGBDurabilityForDisplay(ItemStack stack) {
		return new Color(255,215,0).getRGB();
	}
}
