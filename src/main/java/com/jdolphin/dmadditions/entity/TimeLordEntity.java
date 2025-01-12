package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.init.DMAItems;
import com.swdteam.common.entity.CybermanEntity;
import com.swdteam.common.entity.LookAtGoalBetter;
import com.swdteam.common.entity.dalek.DalekEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;

public class TimeLordEntity extends RegeneratingEntity implements IAngerable {
	public static String TYPE_TIMELORD = "TimelordType";
	private UUID persistentAngerTarget;
	private int remainingPersistentAngerTime;
	private static final RangedInteger PERSISTENT_ANGER_TIME = TickRangeConverter.rangeOfSeconds(20, 39);
	public static final DataParameter<String> TIMELORD_TYPE = EntityDataManager.defineId(TimeLordEntity.class, DataSerializers.STRING);

	public TimeLordEntity(EntityType<? extends RegeneratingEntity> type, World world) {
		super(type, world);
	}

	protected void defineSynchedData() {

		this.getEntityData().define(TIMELORD_TYPE, getRandomTimelordType().getName());
		super.defineSynchedData();
	}

	public TimeLordType getRandomTimelordType() {
		TimeLordType[] types = TimeLordType.values();
		return types[this.random.nextInt(types.length)];
	}

	public ItemStack getPickedResult(RayTraceResult result) {
		return DMAItems.TIMELORD_SPAWNER.get().getDefaultInstance();
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoalBetter(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(6, new LookAtGoalBetter(this, TimeLordEntity.class, 6.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(4, new MeleeAttackGoal(this, 1.5f, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, CybermanEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this,
			PlayerEntity.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new AvoidEntityGoal<>(this, DalekEntity.class, 16.0F, 1.5, 1.2));
	}

	public TimeLordType getTimelordType() {
		return TimeLordType.get(this.entityData.get(TIMELORD_TYPE));
	}

	public void setTimelordType(String type) {
		setTimelordType(TimeLordType.get(type));
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return LivingEntity.createLivingAttributes()
			.add(Attributes.ATTACK_DAMAGE, 1.0D)
			.add(Attributes.MOVEMENT_SPEED, (double) 0.2F)
			.add(Attributes.ATTACK_SPEED, 1.0f)
			.add(Attributes.LUCK)
			.add(Attributes.ATTACK_KNOCKBACK)
			.add(Attributes.FOLLOW_RANGE, 30.0D);
	}

	public void tick() {
		super.tick();
		if (this.getRegenTicks() == 10) {
			this.setTimelordType(getRandomTimelordType());
		}
	}

	public void setTimelordType(TimeLordType type) {
		if (this.entityData != null) {
			this.entityData.set(TIMELORD_TYPE, type.getName());
		}
	}

	public void addAdditionalSaveData(@Nonnull CompoundNBT compound) {
		if (this.entityData != null) {
			compound.putString(TYPE_TIMELORD, this.entityData.get(TIMELORD_TYPE));
		}

		super.addAdditionalSaveData(compound);
	}

	public void readAdditionalSaveData(CompoundNBT compound) {
		if (compound.contains(TYPE_TIMELORD)) {
			this.setTimelordType(compound.getString(TYPE_TIMELORD));
		}

		super.readAdditionalSaveData(compound);
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return remainingPersistentAngerTime;
	}

	@Override
	public void setRemainingPersistentAngerTime(int i) {
		this.remainingPersistentAngerTime = i;
	}

	@Override
	public @Nullable UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID uuid) {
		this.persistentAngerTarget = uuid;
	}

	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.randomValue(this.random));
	}

	public enum TimeLordType {
		ARI("ari"),
		ZURI("zuri"),
		ALEX("alex"),
		EFE("efe"),
		KAI("kai"),
		MAKENA("makena"),
		NOOR("noor"),
		STEVE("steve"),
		SUNNY("sunny"),
		;

		private final String name;
		public final Function<Random, List<ItemStack>> getInventory;

		public String getName() {
			return this.name;
		}

		TimeLordType(String name) {
			this.name = name;
			this.getInventory = (random) -> null;
		}

		TimeLordType(String name, Function<Random, List<ItemStack>> getInventory) {
			this.name = name;
			this.getInventory = getInventory;
		}

		public static TimeLordType get(String name) {
			return Arrays.stream(values())
				.filter(timeLordType -> timeLordType.name.equals(name)).findFirst()
				.orElse(ARI);
		}
	}
}
