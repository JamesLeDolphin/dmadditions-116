package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.config.DMACommonConfig;
import com.swdteam.common.entity.CyberdroneEntity;
import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.EnumSet;
import java.util.Random;

@Mixin(CyberdroneEntity.class)
public class CyberDroneMixin extends FlyingEntity implements IForgeEntity {

	protected CyberDroneMixin(EntityType<? extends FlyingEntity> p_i48578_1_, World p_i48578_2_) {
		super(p_i48578_1_, p_i48578_2_);
	}

	/**
	 * @author JamesLeDolphin
	 * @reason Config
	 */
	@Overwrite
	protected void registerGoals() {
		this.goalSelector.addGoal(5, new RandomFlyGoal((CyberdroneEntity)(Object) this));
		this.goalSelector.addGoal(7, new LookAroundGoal((CyberdroneEntity) (Object)this));
		this.goalSelector.addGoal(7, new LaserAttackGoal((CyberdroneEntity) (Object) this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, DalekEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, VillagerEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, ZombieVillagerEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, ZombieEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, HuskEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, SkeletonEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, StrayEntity.class, true));
	}
	@Mixin(LaserAttackGoal.class)
	static class LaserAttackGoal extends Goal {
		private final CyberdroneEntity drone;
		public int chargeTime;

		public LaserAttackGoal(CyberdroneEntity p_i45837_1_) {
			this.drone = p_i45837_1_;
		}

		public boolean canUse() {
			return this.drone.getTarget() != null;
		}

		public void start() {
			this.chargeTime = 0;
		}

		public void tick() {
			LivingEntity livingentity = this.drone.getTarget();
			if (livingentity.distanceToSqr(this.drone) < 512.0 && this.drone.canSee(livingentity)) {
				World world = this.drone.level;
				++this.chargeTime;
				if (this.chargeTime == 20) {
					double d0 = 0.0;
					double d1 = 0.0;
					double d2 = 0.0;
					if (!livingentity.isAlive()) {
						return;
					}

					d0 = livingentity.getX() - this.drone.getX();
					d1 = livingentity.getY(0.3333333333333333) - this.drone.getY() - 0.75;
					d2 = livingentity.getZ() - this.drone.getZ();
					LaserEntity laser = new LaserEntity(world, this.drone, 0.2F, 2.5F);
					if (!DMACommonConfig.disable_cyberdrone_laser.get()) {
						laser.setExplosionSize(2.0F);
						laser.setLaserType(DMProjectiles.EXPLOSIVE_LASER);
						laser.setCausesFireExplosion(this.drone.level.random.nextBoolean());
					} else {
						laser.setLaserType(DMProjectiles.ORANGE_LASER);
						laser.setExplosive(false);
					}
					laser.shoot(d0, d1, d2, 2.5F, 0.0F);
					this.drone.playSound((SoundEvent) DMSoundEvents.ENTITY_CYBERMAN_SHOOT.get(), 1.0F, 1.0F);
					world.addFreshEntity(laser);
					this.chargeTime = 0;
				}
			} else if (this.chargeTime > 0) {
				--this.chargeTime;
			}

		}
	}
	@Mixin(RandomFlyGoal.class)
	static class RandomFlyGoal extends Goal {
		private final CyberdroneEntity drone;

		public RandomFlyGoal(CyberdroneEntity p_i45836_1_) {
			this.drone = p_i45836_1_;
			this.setFlags(EnumSet.of(Flag.MOVE));
		}

		public boolean canUse() {
			MovementController movementcontroller = this.drone.getMoveControl();
			if (!movementcontroller.hasWanted()) {
				return true;
			} else {
				double d0 = movementcontroller.getWantedX() - this.drone.getX();
				double d1 = movementcontroller.getWantedY() - this.drone.getY();
				double d2 = movementcontroller.getWantedZ() - this.drone.getZ();
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				return d3 < 1.0 || d3 > 3600.0;
			}
		}

		public boolean canContinueToUse() {
			return false;
		}

		public void start() {
			Random random = this.drone.getRandom();
			double d0 = this.drone.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double d1 = this.drone.getY() + (double)((random.nextFloat() * 1.5F - 1.0F) * 16.0F);
			double d2 = this.drone.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.drone.getMoveControl().setWantedPosition(d0, d1, d2, 1.0);
		}
	}


	@Mixin(LookAroundGoal.class)
	static class LookAroundGoal extends Goal {
		private final CyberdroneEntity drone;

		public LookAroundGoal(CyberdroneEntity p_i45839_1_) {
			this.drone = p_i45839_1_;
			this.setFlags(EnumSet.of(Flag.LOOK));
		}

		public boolean canUse() {
			return true;
		}

		public void tick() {
			if (this.drone.getTarget() == null) {
				Vector3d vector3d = this.drone.getDeltaMovement();
				this.drone.yRot = -((float) MathHelper.atan2(vector3d.x, vector3d.z)) * 57.295776F;
				this.drone.yBodyRot = this.drone.yRot;
			} else {
				LivingEntity livingentity = this.drone.getTarget();
				double d0 = 64.0;
				if (livingentity.distanceToSqr(this.drone) < 512.0) {
					double d1 = livingentity.getX() - this.drone.getX();
					double d2 = livingentity.getZ() - this.drone.getZ();
					this.drone.yRot = -((float)MathHelper.atan2(d1, d2)) * 57.295776F;
					this.drone.yBodyRot = this.drone.yRot;
				}
			}

		}
	}

}
