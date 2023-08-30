package com.jdolphin.dmadditions.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import com.jdolphin.dmadditions.entity.ai.goal.*;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;


public class BeatriceFlyingSharkEntity extends AnimalEntity implements IAngerable, IRideable {
	// DataParameter for flying state
	private static final DataParameter<Boolean> FLYING = EntityDataManager.defineId(BeatriceFlyingSharkEntity.class, DataSerializers.BOOLEAN);

	public BeatriceFlyingSharkEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
		// Set initial flying state to false
		this.setFlying(false);

		// Register AI goals
		this.goalSelector.addGoal(6, new FlyRandomlySharkGoal(this));

		// Other attributes initialization

		// Register data parameters
		this.entityData.define(FLYING, false);
	}

	// Getter and Setter for flying state
	public boolean isFlying() {
		return this.entityData.get(FLYING);
	}

	public void setFlying(boolean flying) {
		this.entityData.set(FLYING, flying);
	}

	@Nullable
	@Override
	public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		return null;
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return 0;
	}

	@Override
	public void setRemainingPersistentAngerTime(int p_230260_1_) {

	}

	@Nullable
	@Override
	public UUID getPersistentAngerTarget() {
		return null;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID p_230259_1_) {

	}

	@Override
	public void startPersistentAngerTimer() {

	}

	@Override
	public boolean boost() {
		return false;
	}

	@Override
	public void travelWithInput(Vector3d p_230267_1_) {

	}

	@Override
	public float getSteeringSpeed() {
		return 0;
	}

	// Other methods, goals, and overrides
}