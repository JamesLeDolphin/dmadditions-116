package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.advent.TimedUnlock;
import com.jdolphin.dmadditions.init.DMAItems;
import com.swdteam.common.init.DMItems;
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
	public JamesLeDolphinEntity(EntityType<? extends DolphinEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance difficultyInstance) {
		TimedUnlock.handlePumpkinHead(this);

		if (TimedUnlock.isDecember() && random.nextBoolean() && DMItems.RED_SANTA_HAT != null) {
			this.equipItemIfPossible(new ItemStack(DMItems.RED_SANTA_HAT.get()));
			this.setDropChance(EquipmentSlotType.HEAD, 0.25f);
		}
	}

	@Nullable
	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
		populateDefaultEquipmentSlots(difficultyInstance);
		return super.finalizeSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
	}
}
