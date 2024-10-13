package com.jdolphin.dmadditions.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
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

public class ClockWorkDroidEntity extends MobEntity {
	public static final String TYPE_CLOCKWORKDROID = "ClockWorkDroidType";
	public static final DataParameter<String> CLOCKWORKDROID_TYPE = EntityDataManager.defineId(ClockWorkDroidEntity.class, DataSerializers.STRING);

	public ClockWorkDroidEntity(EntityType<ClockWorkDroidEntity> type, World world) {
		super(type, world);
	}

	@Override
	protected void defineSynchedData() {
		ClockWorkDroidType[] types = ClockWorkDroidType.values();
		this.entityData.define(CLOCKWORKDROID_TYPE, types[this.random.nextInt(types.length)].getName());
	}

	public ClockWorkDroidType getClockWorkDroidType() {
		return ClockWorkDroidType.get(this.entityData.get(CLOCKWORKDROID_TYPE));
	}

	public void setClockWorkDroidType(String type) {
		setClockWorkDroidType(ClockWorkDroidType.get(type));
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

	public void setClockWorkDroidType(ClockWorkDroidType type) {
		if (this.entityData != null) {
			this.entityData.set(CLOCKWORKDROID_TYPE, type.getName());
		}
	}

	@Override
	public void addAdditionalSaveData(@Nonnull CompoundNBT compound) {
		if (this.entityData != null) {
			compound.putString(TYPE_CLOCKWORKDROID, this.entityData.get(CLOCKWORKDROID_TYPE));
		}

		super.addAdditionalSaveData(compound);
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		if (compound.contains(TYPE_CLOCKWORKDROID)) {
			this.setClockWorkDroidType(compound.getString(TYPE_CLOCKWORKDROID));
		}

		super.readAdditionalSaveData(compound);
	}

	@Override
	public Iterable<ItemStack> getArmorSlots() {
		return null;
	}

	@Override
	public ItemStack getItemBySlot(EquipmentSlotType equipmentSlotType) {
		return null;
	}

	@Override
	public void setItemSlot(EquipmentSlotType equipmentSlotType, ItemStack itemStack) {

	}

	@Override
	public HandSide getMainArm() {
		return null;
	}

	public enum ClockWorkDroidType {
		VARIANT1("variant1"),
		VARIANT2("variant2"),
		VARIANT3("variant3");

		private final String name;
		public final Function<Random, List<ItemStack>> getInventory;

		public String getName() {
			return this.name;
		}

		ClockWorkDroidType(String name) {
			this.name = name;
			this.getInventory = (random) -> null;
		}

		ClockWorkDroidType(String name, Function<Random, List<ItemStack>> getInventory) {
			this.name = name;
			this.getInventory = getInventory;
		}

		public static ClockWorkDroidType get(String name) {
			return Arrays.stream(values())
				.filter(ClockWorkDroidType -> ClockWorkDroidType.name.equals(name)).findFirst()
				.orElse(VARIANT2);
		}
	}
}