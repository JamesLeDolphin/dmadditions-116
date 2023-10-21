package com.jdolphin.dmadditions.entity;

import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class K9Entity extends WolfEntity implements IRangedAttackMob {
	public static final Ingredient TAME_ITEMS = Ingredient.of(DMItems.CIRCUIT.get());

	public K9Entity(EntityType<? extends WolfEntity> p_i50240_1_, World p_i50240_2_) {
		super(p_i50240_1_, p_i50240_2_);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.IRON_GOLEM_HURT;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

		this.goalSelector.addGoal(4, new RangedAttackGoal(this, 1.0D, 10, 15.0F));
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes()
			.add(Attributes.MOVEMENT_SPEED, 0.3F)
			.add(Attributes.MAX_HEALTH, 8.0D)
			.add(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	public boolean isTameItem(ItemStack itemStack) {
		return TAME_ITEMS.test(itemStack);
	}

	public ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {
		ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
		Item item = itemstack.getItem();
		if (this.level.isClientSide) {
			boolean flag = this.isOwnedBy(p_230254_1_) || this.isTame() || isTameItem(itemstack) && !this.isTame() && !this.isAngry();
			return flag ? ActionResultType.CONSUME : ActionResultType.PASS;
		}

		if (this.isTame()) {
			if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
				if (!p_230254_1_.abilities.instabuild) {
					itemstack.shrink(1);
				}

				this.heal((float) item.getFoodProperties().getNutrition());
				return ActionResultType.SUCCESS;
			}

			if (!(item instanceof DyeItem)) {
				ActionResultType actionresulttype = super.mobInteract(p_230254_1_, p_230254_2_);
				if ((!actionresulttype.consumesAction() || this.isBaby()) && this.isOwnedBy(p_230254_1_)) {
					this.setOrderedToSit(!this.isOrderedToSit());
					this.jumping = false;
					this.navigation.stop();
					this.setTarget(null);
					return ActionResultType.SUCCESS;
				}

				return actionresulttype;
			}

			DyeColor dyecolor = ((DyeItem) item).getDyeColor();
			if (dyecolor != this.getCollarColor()) {
				this.setCollarColor(dyecolor);
				if (!p_230254_1_.abilities.instabuild) {
					itemstack.shrink(1);
				}

				return ActionResultType.SUCCESS;
			}
		} else if (isTameItem(itemstack) && !this.isAngry()) {
			if (!p_230254_1_.abilities.instabuild) {
				itemstack.shrink(1);
			}

			if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_230254_1_)) {
				this.tame(p_230254_1_);
				this.navigation.stop();
				this.setTarget(null);
				this.setOrderedToSit(true);
				this.level.broadcastEntityEvent(this, (byte) 7);
			} else {
				this.level.broadcastEntityEvent(this, (byte) 6);
			}

			return ActionResultType.SUCCESS;
		}

		return super.mobInteract(p_230254_1_, p_230254_2_);
	}

	@Override
	public void performRangedAttack(LivingEntity entity, float p_82196_2_) {
		LaserEntity laser = new LaserEntity(entity.level, this, 0.0F, (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
		laser.setLaserType(DMProjectiles.RED_LASER);
		laser.setEmitsSmoke(true);
		laser.setDamageSource(new EntityDamageSource("dalekgun", entity));
		double d0 = entity.getX() - this.getX();
		double d1 = entity.getY() - laser.getY();
		double d2 = entity.getZ() - this.getZ();
		double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
		laser.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F,  0);
//		laser.shoot(this, this.xRot, this.yRot, 0.0F, 2.5F, 0.0F);

		this.playSound(DMSoundEvents.ENTITY_DALEK_LASER_SHOOT.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
		level.addFreshEntity(laser);
	}
}
