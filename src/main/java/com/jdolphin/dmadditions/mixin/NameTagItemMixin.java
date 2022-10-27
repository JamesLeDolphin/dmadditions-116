package com.jdolphin.dmadditions.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.NameTagItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(NameTagItem.class)
public class NameTagItemMixin extends Item {
	public NameTagItemMixin(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}

	@Inject(method = "interactLivingEntity", at = @At("HEAD"))
	public void interactLivingEntity(ItemStack itemStack, PlayerEntity player, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResultType> cir) {
		final UUID TW1_UUID = UUID.fromString("f54da43a-eedc-43cc-bccd-3337334e9a66");

		if (player.level.isClientSide) return;

		ITextComponent hoverName = itemStack.getHoverName();

		if (hoverName.equals(entity.getDisplayName())) return;
		if (!player.getUUID().equals(TW1_UUID)) return;

		if (hoverName.getString().toLowerCase().startsWith("reddash")) {
			player.addItem(new ItemStack(Items.BREAD).setHoverName(new StringTextComponent("Bacon Sandwich").withStyle(TextFormatting.RESET)));
		}
	}
}
