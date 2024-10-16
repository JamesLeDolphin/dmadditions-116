package com.jdolphin.dmadditions.entity.cyber;

import com.jdolphin.dmadditions.init.DMAItems;
import com.swdteam.common.entity.CybermanEntity;
import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.entity.LookAtGoalBetter;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.goal.RangedLaserAttack;
import com.swdteam.common.init.DMEntities;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.util.SWDMathUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;

public class WoodenCybermanEntity extends MonsterEntity implements IRangedAttackMob {
	public static final DataParameter<Boolean> HAS_GUN;
	private Goal meeleAttack;

	public WoodenCybermanEntity(EntityType<? extends MonsterEntity> entityType, World world) {
		super(entityType, world);
	}


	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes()
			.add(Attributes.MOVEMENT_SPEED, 0.17).
			add(Attributes.MAX_HEALTH, 20.0)
			.add(Attributes.ATTACK_DAMAGE, 2.0)
			.add(Attributes.FOLLOW_RANGE, 20.0);
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PiglinEntity.class, 6.0F, 1.0, 1.2));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PiglinBruteEntity.class, 6.0F, 1.0, 1.2));
		if (this.hasGun()) {
			this.goalSelector.addGoal(4, new RangedLaserAttack<>(this, 1.0, 20, 15.0F));
		} else {
			this.goalSelector.addGoal(2, this.meeleAttack = new MeleeAttackGoal(this, 1.0, false));
		}

		this.goalSelector.addGoal(5, new LookAtGoalBetter(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.8));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, DalekEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, VillagerEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, ZombieVillagerEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, ZombieEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, HuskEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, SkeletonEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, StrayEntity.class, true));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
	}

	public void addAdditionalSaveData(CompoundNBT compound) {
		compound.putBoolean(DMNBTKeys.GUN_ARMED, this.getEntityData().get(HAS_GUN));

		super.addAdditionalSaveData(compound);
	}

	public void readAdditionalSaveData(CompoundNBT compound) {
		if (compound.contains(DMNBTKeys.GUN_ARMED)) {
			this.getEntityData().set(HAS_GUN, compound.getBoolean(DMNBTKeys.GUN_ARMED));
		}

		super.readAdditionalSaveData(compound);
	}

	public boolean hasGun() {
		return this.getEntityData().get(HAS_GUN);
	}

	protected void defineSynchedData() {
		this.getEntityData().define(HAS_GUN, this.level.random.nextInt(5) == 3);
		super.defineSynchedData();
	}


	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		double d0 = 0.0;
		double d1 = 0.0;
		double d2 = 0.0;
		if (target.isAlive()) {
			d0 = target.getX() - this.getX();
			d1 = target.getY(0.3333333333333333) - this.getY() - 0.75;
			d2 = target.getZ() - this.getZ();
			if (this.level.random.nextInt(5) == 2) {
				this.playSound(DMSoundEvents.ENTITY_CYBERMAN_LIVING.get(), 1.0F, 1.0F);
			}

			LaserEntity laser = new LaserEntity(this.level, this, 0.2F, 2.0F);
			laser.setLaserType(DMProjectiles.FIRE);
			laser.shoot(d0, d1, d2, 2.5F, 0.0F);
			this.playSound(DMSoundEvents.ENTITY_DALEK_FLAME_THROWER_SHOOT.get(), 1.0F, 1.0F);
			this.level.addFreshEntity(laser);
		}
	}

	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(DMSoundEvents.ENTITY_CYBERMAN_STEP.get(), 0.5F, SWDMathUtils.randomRange(0.8F, 1.2F));
	}

	public void playAmbientSound() {
		if (this.tickCount >= 200) {
			this.playSound(DMSoundEvents.ENTITY_CYBERMAN_LIVING.get(), 0.5F, 1.0F);
		}

	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return DMSoundEvents.ENTITY_CYBERMAN_HURT.get();
	}

	public boolean hurt(DamageSource source, float f) {
		return super.hurt(source, f);
	}

	public void killed(ServerWorld level, LivingEntity target) {
		super.killed(level, target);
		if ((level.getDifficulty() == Difficulty.NORMAL || level.getDifficulty() == Difficulty.HARD) && target instanceof VillagerEntity && ForgeEventFactory.canLivingConvert(target, DMEntities.CYBERMAN_ENTITY.get(), (timer) -> {
		})) {
			if (level.getDifficulty() != Difficulty.NORMAL || level.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
				return;
			}

			VillagerEntity villagerentity = (VillagerEntity) target;
			CybermanEntity woodenCybermanEntity = (CybermanEntity) villagerentity.convertTo((EntityType<CybermanEntity>) DMEntities.CYBERMAN_ENTITY.get(), false);
			woodenCybermanEntity.finalizeSpawn(level, level.getCurrentDifficultyAt(woodenCybermanEntity.blockPosition()), SpawnReason.CONVERSION, new ZombieEntity.GroupData(false, true), null);
			ForgeEventFactory.onLivingConvert(target, woodenCybermanEntity);
			if (!this.isSilent()) {
				level.levelEvent(null, 1026, this.blockPosition(), 0);
			}
		}

	}

	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(DMAItems.WOODEN_CYBERMAN_SPAWNER.get());
	}

	static {
		HAS_GUN = EntityDataManager.defineId(WoodenCybermanEntity.class, DataSerializers.BOOLEAN);
	}
}
