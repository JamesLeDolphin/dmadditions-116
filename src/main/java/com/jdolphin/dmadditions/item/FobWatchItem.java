package com.jdolphin.dmadditions.item;

import com.jdolphin.dmadditions.init.DMACapabilities;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.util.ChatUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
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
import java.util.Objects;

public class FobWatchItem extends Item {
	public static final String REGEN_TAG = "Regenerations";
	private int regens;

	public FobWatchItem(Properties properties) {
		this(properties, 12);
	}

	public FobWatchItem(Properties properties, int regenerations) {
		super(properties);
		this.regens = regenerations;
	}

	public boolean canBeDepleted() {
		return this.regens > 0;
	}

	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
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
			player.getCapability(DMACapabilities.REGEN_CAP_CAPABILITY).ifPresent(cap -> {
				if (player.isSecondaryUseActive()) {
					if (cap.hasRegens() && !isFull(stack)) {
						cap.removeRegens(1);
						addRegen(stack, 1);
						Helper.print("took a regen");
					}
				}
				if (!player.isSecondaryUseActive() && hasRegens(stack)) {
					cap.addRegens(1);
					Helper.print("gave a regen");
					addRegen(stack, -1);
				}
			});
		}
		return ActionResult.success(stack);
	}

	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> list) {
		if (group.equals(ItemGroup.TAB_SEARCH) || group.equals(ItemGroup.TAB_MISC)) {
			ItemStack stack = new ItemStack(this);
			stack.getOrCreateTag().putInt(REGEN_TAG, 12);
			list.add(stack);
		}
	}

	public boolean hasRegens(ItemStack stack) {
		CompoundNBT tag = stack.getOrCreateTag();
		return tag.contains(REGEN_TAG) && tag.getInt(REGEN_TAG) > 0;
	}

	public boolean isFull(ItemStack stack) {
		CompoundNBT tag = stack.getOrCreateTag();
		return tag.contains(REGEN_TAG) && tag.getInt(REGEN_TAG) == 12;
	}

	public void addRegen(ItemStack stack, int i) {
		CompoundNBT tag = stack.getOrCreateTag();
		int j = tag.contains(REGEN_TAG) ? tag.getInt(REGEN_TAG) : 0;
		tag.putInt(REGEN_TAG, MathHelper.clamp(0, j + i ,12));
	}

	public boolean showDurabilityBar(ItemStack stack) {
		return stack.getOrCreateTag().contains(REGEN_TAG);
	}

	public double getDurabilityForDisplay(ItemStack stack) {
		CompoundNBT tag = stack.getOrCreateTag();
		if (tag.contains(REGEN_TAG)) return 1 - (double) tag.getInt(REGEN_TAG) / 12;
		else return 0;
	}

	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> textComponents, ITooltipFlag flag) {
		CompoundNBT tag = stack.getOrCreateTag();
		if (tag.contains(REGEN_TAG)) {
			int i = tag.getInt(REGEN_TAG);
			textComponents.add(new StringTextComponent(i + " regeneration" + (i == 1 ? "" : "s"))
				.withStyle(TextFormatting.DARK_GRAY));
		}
	}


	public int getRGBDurabilityForDisplay(ItemStack stack) {
		return new Color(255,215,0).getRGB();
	}
}
