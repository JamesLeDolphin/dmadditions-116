package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.init.DMAEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.HandSide;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class ClockworkDroidEntity extends MonsterEntity {
	public static final String TAG_CLOCKWORK_DROID = "ClockworkDroidType";
	public static final DataParameter<String> CLOCKWORK_DROID_TYPE = EntityDataManager.defineId(ClockworkDroidEntity.class, DataSerializers.STRING);

	public ClockworkDroidEntity(EntityType<ClockworkDroidEntity> type, World world) {
		super(type, world);
		this.setHealth(20.0F);
	}

	public ClockworkDroidEntity(World world) {
		super(DMAEntities.CLOCKWORK_DROID.get(), world);
	}

	@Override
	protected void defineSynchedData() {
		ClockworkDroidType[] types = ClockworkDroidType.values();
		this.entityData.define(CLOCKWORK_DROID_TYPE, types[this.random.nextInt(types.length)].getName());
		super.defineSynchedData();
	}

	public ClockworkDroidType getClockworkDroidType() {
		return ClockworkDroidType.get(this.entityData.get(CLOCKWORK_DROID_TYPE));
	}

	public void setClockworkDroidType(String type) {
		setClockworkDroidType(ClockworkDroidType.get(type));
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return LivingEntity.createLivingAttributes()
			.add(Attributes.ATTACK_DAMAGE, 1.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.2F)
			.add(Attributes.ATTACK_SPEED, 1.0f)
			.add(Attributes.LUCK)
			.add(Attributes.ATTACK_KNOCKBACK)
			.add(Attributes.FOLLOW_RANGE, 30.0D);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(10, new NearestAttackableTargetGoal(this, LivingEntity.class, false) {
			@Override
			public boolean canUse() {
				return super.canUse() && !(this.target instanceof ClockworkDroidEntity);
			}
		});
		this.goalSelector.addGoal(11, new MeleeAttackGoal(this, 1f, false));
	}

	public void setClockworkDroidType(ClockworkDroidType type) {
		if (this.entityData != null) {
			this.entityData.set(CLOCKWORK_DROID_TYPE, type.getName());
		}
	}

	@Override
	public void addAdditionalSaveData(@Nonnull CompoundNBT compound) {
		if (this.entityData != null) {
			compound.putString(TAG_CLOCKWORK_DROID, this.entityData.get(CLOCKWORK_DROID_TYPE));
		}

		super.addAdditionalSaveData(compound);
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		if (compound.contains(TAG_CLOCKWORK_DROID)) {
			this.setClockworkDroidType(compound.getString(TAG_CLOCKWORK_DROID));
		}

		super.readAdditionalSaveData(compound);
	}


	@Override
	public HandSide getMainArm() {
		return HandSide.RIGHT;
	}

	public enum ClockworkDroidType {
		VARIANT1("variant_1"),
		VARIANT2("variant_2"),
		VARIANT3("variant_3");

		private final String name;
		public final Function<Random, List<ItemStack>> getInventory;

		public String getName() {
			return this.name;
		}

		ClockworkDroidType(String name) {
			this.name = name;
			this.getInventory = (random) -> null;
		}

		public static ClockworkDroidType get(String name) {
			return Arrays.stream(values())
				.filter(type -> type.name.equals(name)).findFirst()
				.orElse(VARIANT2);
		}
	}
}
