package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class RegeneratingEntity extends MobEntity {
	private int regenTicks;
	private int regens;

	protected RegeneratingEntity(EntityType<? extends RegeneratingEntity> p_i48576_1_, World p_i48576_2_) {
		super(p_i48576_1_, p_i48576_2_);
	}

	public void setRegen() {
		this.regenTicks = Helper.seconds(30);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		super.hurt(source, amount);
		if (this.getHealth() - amount <= 0 && canRegen()) {
			this.setRegen();
		}
		return true;
	}

	public boolean canRegen() {
		return regens > 0 && regenTicks == 0;
	}

	public boolean isRegenerating() {
		return this.regenTicks > 0;
	}

	@Override
	public void tick() {
		super.tick();
		if (regenTicks > 0) regenTicks--;
		if (isRegenerating()) {
			this.setSpeed(0);
		}
	}
}
