package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.client.audio.ChristmasTreeTickableSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChristmasTreeEntity extends MonsterEntity {
	public ChristmasTreeEntity(EntityType<? extends MonsterEntity> p_i48553_1_, World p_i48553_2_) {
		super(p_i48553_1_, p_i48553_2_);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes()
			.add(Attributes.MOVEMENT_SPEED, 0.3)
			.add(Attributes.MAX_HEALTH, 32.0)
			.add(Attributes.ATTACK_DAMAGE, 2.0)
			.add(Attributes.FOLLOW_RANGE, 20.0);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.addBehaviourGoals();
	}

	protected void addBehaviourGoals() {
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
	}

	@OnlyIn(Dist.CLIENT)
	public void playSound() {
		if (!this.level.isClientSide) return;

		SoundHandler soundManager = Minecraft.getInstance().getSoundManager();


		ChristmasTreeTickableSound sound = new ChristmasTreeTickableSound(this);
		if (!soundManager.isActive(sound)) {
			soundManager.play(sound);
		}
	}

	@Override
	public void onAddedToWorld() {
		super.onAddedToWorld();

		if (this.level.isClientSide) {
			this.playSound();
		}
	}
}
