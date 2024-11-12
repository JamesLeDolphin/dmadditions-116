package com.jdolphin.dmadditions.entity.goal;

import com.jdolphin.dmadditions.entity.FlyingSharkEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;

public class FlyRandomlySharkGoal extends Goal {
	private final FlyingSharkEntity entity;
	private Vector3d targetPosition;

	public FlyRandomlySharkGoal(FlyingSharkEntity entity) {
		this.entity = entity;
		this.setFlags(EnumSet.of(Flag.MOVE)); // Ensure that the entity moves during this goal
	}

	@Override
	public boolean canUse() {
		return !entity.isTamed() && entity.getRandom().nextInt(100) == 0;
	}

	@Override
	public void start() {
//		entity.setFlying(true); // Set the entity as flying when starting to fly
		double targetX = entity.getX() + entity.getRandom().nextDouble() * 20 - 10;
		double targetY = entity.getY() + entity.getRandom().nextDouble() * 10 - 5;
		double targetZ = entity.getZ() + entity.getRandom().nextDouble() * 20 - 10;
		targetPosition = new Vector3d(targetX, targetY, targetZ);


	}

	@Override
	public void tick() {
		if (targetPosition != null) {
			double speed = 0.15; // Adjust the flying speed as needed
			entity.getMoveControl().setWantedPosition(targetPosition.x, targetPosition.y, targetPosition.z, speed);
		}
	}

	@Override
	public boolean canContinueToUse() {
		return entity.isFlying() && !entity.isTamed(); // Continue flying as long as the entity is not tamed
	}

	@Override
	public void stop() {
//		entity.setFlying(false); // Set the entity as not flying when stopping this goal
		targetPosition = null;
	}
}
