package com.jdolphin.dmadditions.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BulletEntity extends ProjectileItemEntity {
	public BulletEntity(EntityType<? extends BulletEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	public BulletEntity(World worldIn, LivingEntity livingEntity) {
		super(EntityType.SNOWBALL, livingEntity, worldIn);
	}

	public BulletEntity(World worldIn, double double1, double double2, double double3) {
		super(EntityType.SNOWBALL, double1, double2, double3, worldIn);
	}

	protected Item getDefaultItem() {
		return Items.SNOWBALL;
	}

	@OnlyIn(Dist.CLIENT)
	private IParticleData getParticle() {
		ItemStack itemstack = this.getItemRaw();
		return (IParticleData)(itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleData(ParticleTypes.ITEM, itemstack));
	}

	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte bite) {
		if (bite == 3) {
			IParticleData iparticledata = this.getParticle();

			for(int i = 0; i < 8; ++i) {
				this.level.addParticle(iparticledata, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}

	}

	protected void onHitEntity(EntityRayTraceResult hitEntity) {
		super.onHitEntity(hitEntity);
		Entity entity = hitEntity.getEntity();
		int i = entity instanceof BlazeEntity ? 3 : 0;
		entity.hurt(DamageSource.thrown(this, this.getOwner()), (float)i);
	}

	protected void onHit(RayTraceResult rayTrace) {
		super.onHit(rayTrace);
		if (!this.level.isClientSide) {
			this.level.broadcastEntityEvent(this, (byte)3);
			this.remove();
		}

	}
}
