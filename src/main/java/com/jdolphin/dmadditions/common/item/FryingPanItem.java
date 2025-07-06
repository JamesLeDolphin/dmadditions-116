package com.jdolphin.dmadditions.common.item;

import com.jdolphin.dmadditions.common.init.DMASoundEvents;
import com.jdolphin.dmadditions.common.util.Helper;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.SoundCategory;

import java.util.Set;

public class FryingPanItem extends ToolItem {
	public FryingPanItem(float p_i48512_1_, float p_i48512_2_, IItemTier p_i48512_3_, Set<Block> p_i48512_4_, Properties p_i48512_5_) {
		super(p_i48512_1_, p_i48512_2_, p_i48512_3_, p_i48512_4_, p_i48512_5_);
	}


	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity entity2) {
		super.hurtEnemy(stack, entity, entity2);
		Helper.playSound(entity.level, entity.blockPosition(), DMASoundEvents.PAN.get(), SoundCategory.PLAYERS);
		return true;
	}
}
