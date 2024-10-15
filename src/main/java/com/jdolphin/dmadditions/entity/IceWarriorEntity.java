package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.init.DMAEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
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

public class IceWarriorEntity extends MonsterEntity {
	public static final String TAG_ICEWARRIOR= "IcewarriorType";
	public static final DataParameter<String> ICEWARRIOR_TYPE = EntityDataManager.defineId(IceWarriorEntity.class, DataSerializers.STRING);

	public IceWarriorEntity(EntityType<IceWarriorEntity> type, World world) {
		super(type, world);
	}

	public IceWarriorEntity(World world) {
		super(DMAEntities.CLOCKWORK_DROID.get(), world);
	}

//	@Override
//	protected void defineSynchedData() {
//		IceWarriorType[] types = IceWarriorType.values();
//		this.entityData.define(ICEWARRIOR_TYPE, types[this.random.nextInt(types.length)].getName());
//		super.defineSynchedData();
//	}

//	public IceWarriorType getClockworkDroidType() {
//		return IceWarriorType.get(this.entityData.get(ICEWARRIOR_TYPE));
//	}

//	public void setIceWarriorType(String type) {
//		setIceWarriorType(IceWarriorType.get(type));
//	}

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
	}
//	public void setIceWarriorType(IceWarriorType type) {
//		if (this.entityData != null) {
//			this.entityData.set(ICEWARRIOR_TYPE, type.getName());
//		}
//	}

	@Override
	public void addAdditionalSaveData(@Nonnull CompoundNBT compound) {
		if (this.entityData != null) {
			compound.putString(TAG_ICEWARRIOR, this.entityData.get(ICEWARRIOR_TYPE));
		}

		super.addAdditionalSaveData(compound);
	}

//	@Override
//	public void readAdditionalSaveData(CompoundNBT compound) {
//		if (compound.contains(TAG_ICEWARRIOR)) {
//			this.setIceWarriorType(compound.getString(TAG_ICEWARRIOR));
//		}
//
//		super.readAdditionalSaveData(compound);
//	}


	@Override
	public HandSide getMainArm() {
		return null;
	}

//	public enum ClockworkDroidType {
//		VARIANT1("variant_1"),
//		VARIANT2("variant_2"),
//		VARIANT3("variant_3");
//
//		private final String name;
//		public final Function<Random, List<ItemStack>> getInventory;
//
//		public String getName() {
//			return this.name;
//		}
//
//		IceWarriorType(String name) {
//			this.name = name;
//			this.getInventory = (random) -> null;
//		}
//
//		IceWarriorType(String name, Function<Random, List<ItemStack>> getInventory) {
//			this.name = name;
//			this.getInventory = getInventory;
//		}
//
//		public static IceWarriorType get(String name) {
//			return Arrays.stream(values())
//				.filter(type -> type.name.equals(name)).findFirst()
//				.orElse(VARIANT2);
//		}
//	}
}