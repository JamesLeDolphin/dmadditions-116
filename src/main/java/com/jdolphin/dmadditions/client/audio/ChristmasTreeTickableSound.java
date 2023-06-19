package com.jdolphin.dmadditions.client.audio;

import com.jdolphin.dmadditions.entity.ChristmasTreeEntity;
import com.jdolphin.dmadditions.init.DMASoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;

public class ChristmasTreeTickableSound extends TickableSound {
	private final ChristmasTreeEntity entity;

	public ChristmasTreeTickableSound(@Nullable ChristmasTreeEntity entity) {
		super(DMASoundEvents.CHRISTMAS_TREE_JINGLE_BELLS.get(), SoundCategory.RECORDS);
		this.entity = entity;
		this.looping = true;
		this.delay = 0;
		this.volume = 1F;
		if (entity != null) {
			this.x = (float) entity.getX();
			this.y = (float) entity.getY();
			this.z = (float) entity.getZ();

			if (entity.level.getEntitiesOfClass(ChristmasTreeEntity.class, new AxisAlignedBB(this.entity.blockPosition()).inflate(16)).size() > 1) {
				this.stop();
			}
		}
	}

	@Override
	public void tick() {
		if (this.entity == null || !this.entity.isAlive()) {
			this.stop();
		} else {
			this.x = (float) this.entity.getX();
			this.y = (float) this.entity.getY();
			this.z = (float) this.entity.getZ();

			if (entity.level.isClientSide) {
				Minecraft minecraft = Minecraft.getInstance();
				if (minecraft.player == null) return;

				float distance = entity.distanceTo(minecraft.player);

				if (distance > 16) {
					this.volume = 0.5f;
				} else {
					this.volume = 1;
				}
			}
		}
	}
}
