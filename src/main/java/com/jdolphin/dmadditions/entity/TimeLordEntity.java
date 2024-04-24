package com.jdolphin.dmadditions.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.world.World;

import java.util.Random;

public class TimeLordEntity extends LivingEntity {
	protected TimeLordEntity(EntityType<? extends LivingEntity> p_i48577_1_, World p_i48577_2_) {
		super(p_i48577_1_, p_i48577_2_);
	}

	@Override
	public Iterable<ItemStack> getArmorSlots() {
		return null;
	}

	@Override
	public ItemStack getItemBySlot(EquipmentSlotType slotType) {
		if (slotType.equals(EquipmentSlotType.MAINHAND)) return this.getMainHandItem();
		else return this.getOffhandItem();
	}

	@Override
	public void setItemSlot(EquipmentSlotType p_184201_1_, ItemStack p_184201_2_) {

	}

	@Override
	public HandSide getMainArm() {
		return Math.random() == 0.1 ? HandSide.LEFT : HandSide.RIGHT;
	}
}
