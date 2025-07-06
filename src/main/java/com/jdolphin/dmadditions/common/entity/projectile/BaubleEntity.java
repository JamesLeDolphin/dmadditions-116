package com.jdolphin.dmadditions.common.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BaubleEntity extends SnowballEntity {
	public BaubleEntity(EntityType<? extends SnowballEntity> entityType, World world) {
		super(entityType, world);
	}

	public BaubleEntity(World world, double v, double v1, double v2) {
		super(world, v, v1, v2);
	}

	public BaubleEntity(World world, LivingEntity livingEntity) {
		super(world, livingEntity);
	}


	@Override
	protected void onHit(RayTraceResult rayTraceResult) {
		super.onHit(rayTraceResult);
		this.level.explode(this, this.getX(), this.getY(), this.getZ(), 1, Explosion.Mode.DESTROY);
	}
}
