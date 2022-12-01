package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.init.DMANBTKeys;
import com.swdteam.common.entity.LookAtGoalBetter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeEntity;

import javax.annotation.Nonnull;

// Robo Santa or something
public class PilotFishEntity extends MonsterEntity implements IForgeEntity {
	public static final DataParameter<String> PILOT_FISH_TYPE = EntityDataManager.defineId(PilotFishEntity.class, DataSerializers.STRING);
	private Goal meleeAttack;

	public PilotFishEntity(EntityType<? extends MonsterEntity> type, World world) {
		super(type, world);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes()
			.add(Attributes.MOVEMENT_SPEED, 0.3)
			.add(Attributes.MAX_HEALTH, 20.0)
			.add(Attributes.ATTACK_DAMAGE, 2.0)
			.add(Attributes.FOLLOW_RANGE, 20.0);
	}

	public void addAdditionalSaveData(@Nonnull CompoundNBT compound) {
		if (this.entityData != null) {
			compound.putString(DMANBTKeys.TYPE_PILOT_FISH, this.entityData.get(PILOT_FISH_TYPE));
		}

		super.addAdditionalSaveData(compound);
	}

	public void readAdditionalSaveData(CompoundNBT compound) {
		if (compound.contains(DMANBTKeys.TYPE_PILOT_FISH)) {
			this.setPilotFishType(compound.getString(DMANBTKeys.TYPE_PILOT_FISH));
		}

		super.readAdditionalSaveData(compound);
	}

	protected void defineSynchedData() {
		PilotFishType[] types = PilotFishType.values();
		this.getEntityData().define(PILOT_FISH_TYPE, types[this.random.nextInt(types.length)].getName());
		super.defineSynchedData();
	}

	public PilotFishType getPilotFishType() {
		return PilotFishType.get(this.entityData.get(PILOT_FISH_TYPE));
	}

	public void setPilotFishType(String type) {
		setPilotFishType(PilotFishType.get(type));
	}

	public void setPilotFishType(PilotFishType type) {
		if (this.entityData != null) {
			this.entityData.set(PILOT_FISH_TYPE, type.getName());
		}
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PiglinEntity.class, 6.0F, 1.0, 1.2));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PiglinBruteEntity.class, 6.0F, 1.0, 1.2));
		this.goalSelector.addGoal(2, this.meleeAttack = new MeleeAttackGoal(this, 1.0, false));

		this.goalSelector.addGoal(5, new LookAtGoalBetter(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.8));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Entity.class));
	}


}