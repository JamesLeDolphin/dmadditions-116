package com.jdolphin.dmadditions.entity.cyber;

import com.jdolphin.dmadditions.init.DMADamageSources;
import com.jdolphin.dmadditions.init.DMAEntities;
import com.swdteam.common.entity.CybermanEntity;
import com.swdteam.common.entity.CybermatEntity;
import com.swdteam.common.init.DMEntities;
import com.swdteam.common.init.DMItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class CyberCowEntity extends CowEntity {
	public CyberCowEntity(EntityType<? extends CyberCowEntity> entity, World world) {
		super(entity, world);
	}

	public CyberCowEntity(World world) {
		super(DMAEntities.CYBERCOW.get(), world);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.5));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(Items.WHEAT, DMItems.CIRCUIT.get()), false));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(6, new LookAtGoal(this, CybermanEntity.class, 6.0F));
		this.goalSelector.addGoal(6, new LookAtGoal(this, MondasCybermanEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
	}

	public static AttributeModifierMap.@NotNull MutableAttribute createAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.MOVEMENT_SPEED, 0.18);
	}

	public ActionResultType mobInteract(PlayerEntity entity, Hand hand) {
		ItemStack itemInHand = entity.getItemInHand(hand);
		World world = this.level;
		if (!world.isClientSide()) {
			if (itemInHand.getItem() == Items.SHEARS) {
				Random rand = entity.getRandom();
				itemInHand.hurt(1, rand, (ServerPlayerEntity) entity);
				this.hurt(DMADamageSources.SHEARED, 2.0f);
				if (rand.nextInt(3) == 0) {
					CowEntity cow = new CowEntity(EntityType.COW, world);
					cow.setPos(this.getX(), this.getY(), this.getZ());
					cow.setYBodyRot(this.yBodyRot);
					cow.setYHeadRot(this.getYHeadRot());
					cow.setHealth(this.getHealth());
					cow.hurt(DMADamageSources.SHEARED, 0.5f);

					if (this.hasCustomName()) {
						cow.setCustomName(this.getCustomName());
						cow.setCustomNameVisible(this.isCustomNameVisible());
					}

					if (this.isPersistenceRequired()) {
						cow.setPersistenceRequired();
					}

					cow.setInvulnerable(this.isInvulnerable());
					this.remove();
					world.addFreshEntity(cow);
					world.addFreshEntity(new ItemEntity(world, this.getX(), this.getY(), this.getZ(), DMItems.CIRCUIT.get().getDefaultInstance()));
				}
				if (rand.nextInt(10) == 0) {
					entity.hurt(DMADamageSources.ELECTROCUTION, 2.0f);
					this.kill();
				}
				return ActionResultType.SUCCESS;
			} else {
				return super.mobInteract(entity, hand);
			}
		}
		return ActionResultType.PASS;
	}

	public boolean hurt(DamageSource source, float amount) {
		World world = this.level;
		if (!world.isClientSide()) {
			if (this.random.nextInt(10) == 0) {
				world.addFreshEntity(new CybermatEntity(DMEntities.CYBERMAT_ENTITY.get(), world));
			}
			return true;
		}
		return false;
	}

	public boolean canFallInLove() {
		return false;
	}

	public boolean canMate(AnimalEntity p_70878_1_) {
		return false;
	}

	public boolean isBaby() {
		return false;
	}

	public void setBaby(boolean b) {}
}
