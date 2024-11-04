package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Rotation;
import net.minecraft.world.World;

import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public abstract class RegeneratingEntity extends CreatureEntity {
	public static final String TAG_REGENS = "Regenerations";
	public static final DataParameter<Integer> REGENS = EntityDataManager.defineId(RegeneratingEntity.class,
			DataSerializers.INT);
	public static final DataParameter<Integer> REGEN_TICKS = EntityDataManager.defineId(RegeneratingEntity.class,
			DataSerializers.INT);

	protected RegeneratingEntity(EntityType<? extends RegeneratingEntity> entityType, World world) {
		super(entityType, world);
	}

	protected void defineSynchedData() {
		this.getEntityData().define(REGENS, 12);
		this.getEntityData().define(REGEN_TICKS, 0);
		super.defineSynchedData();
	}

	public int getRegens() {
		return this.entityData.get(REGENS);
	}

	public boolean hasRegens() {
		return getRegens() > 0;
	}

	public void setRegens(int regens) {
		this.entityData.set(REGENS, regens);
	}

	public void addAdditionalSaveData(@Nonnull CompoundNBT compound) {
		if (this.entityData != null) {
			compound.putInt(TAG_REGENS, this.entityData.get(REGENS));
		}

		super.addAdditionalSaveData(compound);
	}

	public void readAdditionalSaveData(CompoundNBT compound) {
		if (compound.contains(TAG_REGENS)) {
			this.setRegens(compound.getInt(TAG_REGENS));
		}
		super.readAdditionalSaveData(compound);
	}

	public int getRegenTicks() {
		return this.entityData.get(REGEN_TICKS);
	}

	public void setRegenTicks(int ticks) {
		this.entityData.set(REGEN_TICKS, ticks);
	}

	public void setRegen() {
		int i = getRegens();
		setRegens(Math.max(i - 1, 0));
		this.addEffect(new EffectInstance(Effects.REGENERATION, 20 * 10, 1, false, false));
		this.setRegenTicks(Helper.seconds(10));
	}

	@Override
	public boolean hurt(@NotNull DamageSource source, float amount) {
		super.hurt(source, amount);
		if (this.getHealth() - amount <= 0 && canRegen()) {
			this.setRegen();
			return false;
		}
		return super.hurt(source, amount);
	}

	public boolean canRegen() {
		return hasRegens() && getRegenTicks() == 0;
	}

	public boolean isRegenerating() {
		return this.getRegenTicks() > 0;
	}

	@Override
	public void tick() {
		super.tick();
		if (isRegenerating()) {
			this.setRegenTicks(this.getRegenTicks() - 1);
			this.yHeadRot = 0;
			this.rotate(Rotation.NONE);
			LogManager.getLogger().debug("Regenerating");
			this.setTarget(null);
			this.getNavigation().stop();
			this.setHealth(this.getMaxHealth());
			this.revive();
			this.addEffect(new EffectInstance(Effects.GLOWING, 3, 1, false, false, false));
		}
	}
}
