package com.jdolphin.dmadditions.entity.cyber;

import com.jdolphin.dmadditions.advent.TimedUnlock;
import com.jdolphin.dmadditions.init.DMAItems;
import com.swdteam.common.entity.CybermanEntity;
import com.swdteam.common.entity.dalek.DalekEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Arrays;


public class MondasianEntity extends MonsterEntity {
	public static String TAG_MONDASIAN_TYPE = "MondasianType";

	public static final DataParameter<String> MONDASIAN_TYPE = EntityDataManager.defineId(MondasianEntity.class, DataSerializers.STRING);

	public MondasianEntity(EntityType<? extends MonsterEntity> type, World world) {
		super(type, world);
	}

	public void addAdditionalSaveData(@Nonnull CompoundNBT compound) {
		if (this.entityData != null) {
			compound.putString(TAG_MONDASIAN_TYPE, this.entityData.get(MONDASIAN_TYPE));
		}

		super.addAdditionalSaveData(compound);
	}

	public void readAdditionalSaveData(CompoundNBT compound) {
		if (compound.contains(TAG_MONDASIAN_TYPE)) {
			this.setMondasianType(compound.getString(TAG_MONDASIAN_TYPE));
		}

		super.readAdditionalSaveData(compound);
	}

	public ItemStack getPickedResult(RayTraceResult target) {
		return DMAItems.MONDASIAN_SPAWNER.get().getDefaultInstance();
	}

	protected void defineSynchedData() {
		MondasianType[] types = MondasianType.values();
		this.getEntityData().define(MONDASIAN_TYPE, types[this.random.nextInt(types.length)].getName());
		super.defineSynchedData();
	}

	public MondasianType getMondasianType() {
		return MondasianType.get(this.entityData.get(MONDASIAN_TYPE));
	}

	public void setMondasianType(String type) {
		setMondasianType(MondasianType.get(type));
	}

	public void setMondasianType(MondasianType type) {
		if (this.entityData != null) {
			this.entityData.set(MONDASIAN_TYPE, type.getName());
		}
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MonsterEntity.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 16.0D)
			.add(Attributes.MOVEMENT_SPEED, (double) 0.35F)
			.add(Attributes.ATTACK_DAMAGE, 5.0D)
			.add(Attributes.FOLLOW_RANGE, 20.0);

	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, CybermanEntity.class)).setAlertOthers());
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, DalekEntity.class, true));
		this.targetSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, true));
	}

	public enum MondasianType {
		BLONDE("blonde"),
		COP("cop"),
		ALEX("alex"),
		CYB4("cyb4"),
		JAMES("james"),
		LUCIFER("lucifer"),
		;

		private final String name;

		public String getName() {
			return this.name;
		}

		MondasianType(String name) {
			this.name = name;
		}

		public static MondasianType get(String name) {
			return Arrays.stream(values())
				.filter(type -> type.name.equals(name)).findFirst()
				.orElse(BLONDE);
		}
	}

	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance p_180481_1_) {
		super.populateDefaultEquipmentSlots(p_180481_1_);

		TimedUnlock.handlePumpkinHead(this);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty,
			SpawnReason reason, ILivingEntityData data, CompoundNBT nbt) {
		populateDefaultEquipmentSlots(difficulty);
		return super.finalizeSpawn(world, difficulty, reason, data, nbt);
	}
}
