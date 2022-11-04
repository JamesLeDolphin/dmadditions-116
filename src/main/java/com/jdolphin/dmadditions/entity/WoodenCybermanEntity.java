package com.jdolphin.dmadditions.entity;

import com.swdteam.common.entity.CybermanEntity;
import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.entity.LookAtGoalBetter;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.goal.RangedLaserAttack;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTags;
import com.swdteam.util.SWDMathUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

public class WoodenCybermanEntity extends CybermanEntity {
	public WoodenCybermanEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}
	private Goal meeleAttack;


	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3499999940395355).add(Attributes.MAX_HEALTH, 20.0).add(Attributes.ATTACK_DAMAGE, 2.0).add(Attributes.FOLLOW_RANGE, 20.0);
	}
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(3, new AvoidEntityGoal(this, PiglinEntity.class, 6.0F, 1.0, 1.2));
		this.goalSelector.addGoal(3, new AvoidEntityGoal(this, PiglinBruteEntity.class, 6.0F, 1.0, 1.2));
		if (this.hasGun()) {
			this.goalSelector.addGoal(4, new RangedLaserAttack(this, 1.0, 20, 15.0F));
		} else {
			this.goalSelector.addGoal(2, this.meeleAttack = new MeleeAttackGoal(this, 1.0, false));
		}

		this.goalSelector.addGoal(5, new LookAtGoalBetter(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.8));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, DalekEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, VillagerEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, ZombieVillagerEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, ZombieEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, HuskEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, SkeletonEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, StrayEntity.class, true));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
	}
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound((SoundEvent)DMSoundEvents.ENTITY_CYBERMAN_STEP.get(), 0.5F, SWDMathUtils.randomRange(0.8F, 1.2F));
	}

	public void playAmbientSound() {
		if (this.tickCount >= 200) {
			this.playSound((SoundEvent)DMSoundEvents.ENTITY_CYBERMAN_LIVING.get(), 0.5F, 1.0F);
		}

	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return (SoundEvent)DMSoundEvents.ENTITY_CYBERMAN_HURT.get();
	}

	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		double d0 = 0.0;
		double d1 = 0.0;
		double d2 = 0.0;
		if (target.isAlive()) {
			d0 = target.getX() - this.getX();
			d1 = target.getY(0.3) - this.getY() - 0.75;
			d2 = target.getZ() - this.getZ();
			if (this.level.random.nextInt(5) == 2) {
				this.playSound((SoundEvent) DMSoundEvents.ENTITY_CYBERMAN_LIVING.get(), 1.0F, 1.0F);
			}

			LaserEntity laser = new LaserEntity(this.level, this, 0.2F, 2.0F);
			laser.setLaserType(DMProjectiles.FIRE);
			laser.shoot(d0, d1, d2, 2.5F, 0.0F);
			this.playSound((SoundEvent)DMSoundEvents.ENTITY_DALEK_FLAME_THROWER_SHOOT.get(), 1.0F, 1.0F);
			this.level.addFreshEntity(laser);
		}
	}
	@Override
	public boolean hurt(DamageSource source, float f) {
		return super.hurt(source, f);
	}
}
