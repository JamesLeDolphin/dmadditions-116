package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.block.christmas.ChristmasCrackerBlock;
import com.jdolphin.dmadditions.init.DMAItems;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.Collection;

public class ChristmasCreeperEntity extends CreeperEntity {
	private static final DataParameter<Integer> DATA_SWELL_DIR = EntityDataManager.defineId(CreeperEntity.class, DataSerializers.INT);
	private static final DataParameter<Boolean> DATA_IS_POWERED = EntityDataManager.defineId(CreeperEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> DATA_IS_IGNITED = EntityDataManager.defineId(CreeperEntity.class, DataSerializers.BOOLEAN);
	private int oldSwell;
	private int swell;
	private int maxSwell = 30;
	private int explosionRadius = 3;
	private int droppedSkulls;

	public ChristmasCreeperEntity(EntityType<? extends CreeperEntity> entityType, World world) {
		super(entityType, world);

		this.equipItemIfPossible(new ItemStack(DMAItems.SANTA_HAT.get()));
	}

	public void tick() {
		if (this.isAlive()) {
			this.oldSwell = this.swell;
			if (this.isIgnited()) {
				this.setSwellDir(1);
			}

			int i = this.getSwellDir();
			if (i > 0 && this.swell == 0) {
				this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
			}

			this.swell += i;
			if (this.swell < 0) {
				this.swell = 0;
			}

			if (this.swell >= this.maxSwell) {
				this.swell = this.maxSwell;
				this.explodeCreeper();
			}
		}

		super.tick();
	}

	private void explodeCreeper() {
		if (!this.level.isClientSide) {
			Explosion.Mode explosion$mode = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
			float f = this.isPowered() ? 2.0F : 1.0F;
			this.dead = true;
			ChristmasCrackerBlock.openCracker(level, this.getEyePosition(1));

			this.remove();
			this.spawnLingeringCloud();
		}
	}

	private void spawnLingeringCloud() {
		Collection<EffectInstance> collection = this.getActiveEffects();
		if (!collection.isEmpty()) {
			AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.level, this.getX(), this.getY(), this.getZ());
			areaeffectcloudentity.setRadius(2.5F);
			areaeffectcloudentity.setRadiusOnUse(-0.5F);
			areaeffectcloudentity.setWaitTime(10);
			areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
			areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float) areaeffectcloudentity.getDuration());

			for (EffectInstance effectinstance : collection) {
				areaeffectcloudentity.addEffect(new EffectInstance(effectinstance));
			}

			this.level.addFreshEntity(areaeffectcloudentity);
		}

	}

}
