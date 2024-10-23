package com.jdolphin.dmadditions.mixin.other;

import com.jdolphin.dmadditions.advent.TimedUnlock;
import com.swdteam.common.entity.CybermanEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CybermanEntity.class)
public abstract class CybermanEntityMixin extends MonsterEntity implements IRangedAttackMob {

	protected CybermanEntityMixin(EntityType<? extends MonsterEntity> entity, World world) {
		super(entity, world);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty,
			SpawnReason reason, ILivingEntityData data, CompoundNBT nbt) {

		if (TimedUnlock.isHalloween() && random.nextBoolean()) {
			this.setItemSlot(EquipmentSlotType.HEAD,
					new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
			this.armorDropChances[EquipmentSlotType.HEAD.getIndex()] = 0.0F;
		}

		return super.finalizeSpawn(world, difficulty, reason, data, nbt);
	}
}
