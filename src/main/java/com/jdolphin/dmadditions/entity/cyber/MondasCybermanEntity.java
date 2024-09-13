package com.jdolphin.dmadditions.entity.cyber;

import com.jdolphin.dmadditions.init.DMAEntities;
import com.jdolphin.dmadditions.init.DMAItems;
import com.swdteam.common.entity.CybermanEntity;
import com.swdteam.common.entity.dalek.DalekEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class MondasCybermanEntity extends WoodenCybermanEntity {
	private static final DataParameter<Integer> DATA_ID_ATTACK_TARGET = EntityDataManager.defineId(MondasCybermanEntity.class, DataSerializers.INT);
	private LivingEntity clientSideCachedAttackTarget;
	private int clientSideAttackTime;

	public MondasCybermanEntity(EntityType<? extends MondasCybermanEntity> entityType, World world) {
		super(entityType, world);
	}

	public MondasCybermanEntity(World world) {
		super(DMAEntities.MONDAS_CYBERMAN.get(), world);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MonsterEntity.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 6.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.5D)
			.add(Attributes.FOLLOW_RANGE, 16.0D)
			.add(Attributes.MAX_HEALTH, 30.0D);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_ID_ATTACK_TARGET, 0);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(4, new MondasCybermanEntity.AttackGoal(this));
		this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.0D, 80));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookAtGoal(this, CybermanEntity.class, 12.0F, 0.01F));
		this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this,
			LivingEntity.class, 10, true, false, new MondasCybermanEntity.TargetPredicate(this)));
	}

	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		if (target.isAlive()) {
			if (this.hasGun()) super.performRangedAttack(target, distanceFactor);
			else if (distanceFactor <= 5) {
				this.lookAt(target, 10.0f, 10.0f);
				float f = 1.0F;
				if (this.level.getDifficulty() == Difficulty.NORMAL) {
					f = 2.0F;
				}
				if (this.level.getDifficulty() == Difficulty.NORMAL) {
					f = 3.0F;
				}
				target.hurt(DamageSource.indirectMagic(this, this), f);
				target.hurt(DamageSource.mobAttack(this), (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
				this.setTarget((LivingEntity)null);
			}
		}
	}

	public void aiStep() {
		if (this.isAlive()) {
			if (this.level.isClientSide) {

				if (this.hasActiveAttackTarget()) {
					if (this.clientSideAttackTime < this.getAttackDuration()) {
						++this.clientSideAttackTime;
					}

					LivingEntity livingentity = this.getActiveAttackTarget();
					if (livingentity != null) {
						this.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
						this.getLookControl().tick();
						double d0 = livingentity.getX() - this.getX();
						double d1 = livingentity.getY(0.5) - this.getEyeY();
						double d2 = livingentity.getZ() - this.getZ();
						double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
						d0 = d0 / d3;
						d1 = d1 / d3;
						d2 = d2 / d3;
						double d4 = this.random.nextDouble();

						while(d4 < d3) {
							d4 += 1.8D + this.random.nextDouble() * 1.7D;
							this.level.addParticle(ParticleTypes.FLAME, this.getX() + d0 * d4, this.getEyeY() + d1 * d4, this.getZ() + d2 * d4, 0.0D, 0.0D, 0.0D);
						}
					}
				}
			}

			if (this.hasActiveAttackTarget()) {
				this.yRot = this.yHeadRot;
			}
		}

		super.aiStep();
	}

	static class TargetPredicate implements Predicate<LivingEntity> {
		private final MondasCybermanEntity cyberman;

		public TargetPredicate(MondasCybermanEntity p_i45832_1_) {
			this.cyberman = p_i45832_1_;
		}

		public boolean test(@Nullable LivingEntity entity) {
			return (entity instanceof PlayerEntity || entity instanceof DalekEntity) && entity.distanceToSqr(this.cyberman) > 9.0D;
		}
	}

	static class AttackGoal extends Goal {
		private final MondasCybermanEntity cyber;
		private int attackTime;

		public AttackGoal(MondasCybermanEntity p_i45833_1_) {
			this.cyber = p_i45833_1_;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		public boolean canUse() {
			LivingEntity livingentity = this.cyber.getTarget();
			return livingentity != null && livingentity.isAlive();
		}

		public boolean canContinueToUse() {
			return super.canContinueToUse() && (this.cyber.distanceToSqr(this.cyber.getTarget()) > 5.0D);
		}

		public void start() {
			this.attackTime = -10;
			this.cyber.getNavigation().stop();
			this.cyber.getLookControl().setLookAt(this.cyber.getTarget(), 90.0F, 90.0F);
			this.cyber.hasImpulse = true;
		}

		public void stop() {
			this.cyber.setActiveAttackTarget(0);
			this.cyber.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingentity = this.cyber.getTarget();
			this.cyber.getNavigation().stop();
			this.cyber.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
			if (!this.cyber.canSee(livingentity)) {
				this.cyber.setTarget((LivingEntity)null);
			} else {
				++this.attackTime;
				if (this.attackTime == 0) {
					this.cyber.setActiveAttackTarget(this.cyber.getTarget().getId());
				} else if (this.attackTime >= this.cyber.getAttackDuration()) {
					float f = 1.0F;
					if (this.cyber.level.getDifficulty() == Difficulty.HARD) {
						f += 2.0F;
					}

					livingentity.hurt(DamageSource.indirectMagic(this.cyber, this.cyber), f);
					livingentity.hurt(DamageSource.mobAttack(this.cyber), (float)this.cyber.getAttributeValue(Attributes.ATTACK_DAMAGE));
					this.cyber.setTarget((LivingEntity)null);
				}

				super.tick();
			}
		}
	}

	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(DMAItems.WOODEN_CYBERMAN_SPAWNER.get());
	}

	public int getAttackDuration() {
		return 80;
	}

	private void setActiveAttackTarget(int p_175463_1_) {
		this.entityData.set(DATA_ID_ATTACK_TARGET, p_175463_1_);
	}

	public boolean hasActiveAttackTarget() {
		return this.entityData.get(DATA_ID_ATTACK_TARGET) != 0;
	}

	@Nullable
	public LivingEntity getActiveAttackTarget() {
		if (!this.hasActiveAttackTarget()) {
			return null;
		} else if (this.level.isClientSide) {
			if (this.clientSideCachedAttackTarget != null) {
				return this.clientSideCachedAttackTarget;
			} else {
				Entity entity = this.level.getEntity(this.entityData.get(DATA_ID_ATTACK_TARGET));
				if (entity instanceof LivingEntity) {
					this.clientSideCachedAttackTarget = (LivingEntity)entity;
					return this.clientSideCachedAttackTarget;
				} else {
					return null;
				}
			}
		} else {
			return this.getTarget();
		}
	}

	public void onSyncedDataUpdated(DataParameter<?> p_184206_1_) {
		super.onSyncedDataUpdated(p_184206_1_);
		if (DATA_ID_ATTACK_TARGET.equals(p_184206_1_)) {
			this.clientSideAttackTime = 0;
			this.clientSideCachedAttackTarget = null;
		}

	}
}
