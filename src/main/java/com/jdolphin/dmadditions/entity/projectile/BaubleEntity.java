package com.jdolphin.dmadditions.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BaubleEntity extends SnowballEntity {
	public BaubleEntity(EntityType<? extends SnowballEntity> p_i50159_1_, World p_i50159_2_) {
		super(p_i50159_1_, p_i50159_2_);
	}

	public BaubleEntity(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	public BaubleEntity(World p_i1774_1_, LivingEntity p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
	}


	@Override
	protected void onHit(RayTraceResult rayTraceResult) {
		super.onHit(rayTraceResult);
		this.level.explode(this, this.getX(), this.getY(), this.getZ(), 1, Explosion.Mode.DESTROY);
	}
}
