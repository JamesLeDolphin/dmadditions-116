package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.init.DMASoundEvents;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public abstract class RegeneratingEntity extends CreatureEntity {
	public static final String TAG_REGENS = "Regenerations";
	private final BasicParticleType particle = this.random.nextDouble() > 0.5 ? ParticleTypes.FLAME : ParticleTypes.SOUL_FIRE_FLAME;;
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
		this.setRegenTicks(Helper.seconds(10));
		Helper.playSound(level, this.blockPosition(), DMASoundEvents.REGEN_START.get(), SoundCategory.NEUTRAL);
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

	public void regenTick() {
		this.setRegenTicks(this.getRegenTicks() - 1);
		this.yHeadRot = 0;
		this.setRot(0, 0);
		this.setTarget(null);
		this.getNavigation().stop();

		double rand = this.random.nextDouble();

		this.level.addParticle(particle, this.getX() - 0.15 * rand, this.getEyeY() + 0.25, this.getZ() + 0.35 * rand, 0.0D, 0.2, 0.0D);
		this.level.addParticle(particle, this.getX() + 0.15 * rand / 2, this.getEyeY() + 0.25, this.getZ() + 0.35 * rand, 0.0D, 0.2, 0.0D);

		this.level.addParticle(particle, this.getX() + 1 - 0.25 * rand, this.getEyeY() + 0.4, this.getZ() + 0.25 * rand, 0.1D, 0.2, 0.0D);

		this.level.addParticle(particle, this.getX() - 0.75 - 0.25 * rand, this.getEyeY() + 0.4, this.getZ() + 0.25 * rand, -0.1D, 0.2, 0.0D);


		if (getRegenTicks() == 20) this.setHealth(this.getMaxHealth());
	}

	@Override
	public void tick() {
		super.tick();
		if (isRegenerating()) {
			regenTick();
		}
	}
}
