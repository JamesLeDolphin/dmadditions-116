package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.init.DMAItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class JamesLeDolphinEntity extends DolphinEntity {
	public JamesLeDolphinEntity(EntityType<? extends DolphinEntity> p_i50275_1_, World p_i50275_2_) {
		super(p_i50275_1_, p_i50275_2_);
	}

	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance p_180481_1_) {
		if (AdventUnlock.isDecember() && random.nextBoolean() && DMAItems.SANTA_HAT != null) {
			this.equipItemIfPossible(new ItemStack(DMAItems.SANTA_HAT.get()));
			this.setDropChance(EquipmentSlotType.HEAD, 0.25f);
		}
	}

	@Nullable
	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
		populateDefaultEquipmentSlots(p_213386_2_);
		return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
	}
}
