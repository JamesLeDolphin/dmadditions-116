package com.jdolphin.dmadditions.item;

import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.init.DMProjectiles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class UnitGun extends Item {
	private RegistryObject<SoundEvent> shootSound;
	private final DMProjectiles.Laser laserType;
	private final IItemTier tier;
	public float requiredChargeTime;
	public float attackDamage;

	public UnitGun(IItemTier tier, float chargeInSeconds, DMProjectiles.Laser laserType, RegistryObject<SoundEvent> lasergunShootSound, Item.Properties properties) {
		super(properties.defaultDurability(tier.getUses()));
		this.tier = tier;
		this.laserType = laserType;
		this.requiredChargeTime = chargeInSeconds * 20.0F;
		this.shootSound = lasergunShootSound;
	}

	public IItemTier getTier() {
		return this.tier;
	}


	private SoundEvent getShootSound() {
		return (SoundEvent)this.shootSound.get();
	}


	public void setShootSound(RegistryObject<SoundEvent> shootSound) {
		this.shootSound = shootSound;
	}

	public UseAction getUseAnimation(ItemStack stack) {
		return UseAction.BOW;
	}

	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		super.releaseUsing(stack, worldIn, entityLiving, timeLeft);
		this.attackDamage = worldIn.getDifficulty() == Difficulty.EASY ? 4.0F : (float)(worldIn.getDifficulty() == Difficulty.NORMAL ? 6 : 8);
		if (!worldIn.isClientSide) {
			int timeUsed = this.getUseDuration(stack) - timeLeft;
			if ((float)timeUsed < this.requiredChargeTime) {
				return;
			}

			worldIn.playSound(null, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), this.getShootSound(), SoundCategory.NEUTRAL, 1.0F, 1.0F);
			LaserEntity laser = new LaserEntity(worldIn, entityLiving, 0.0F, this.attackDamage);
			laser.setLaserType(this.laserType);
			laser.setDamageSource(new EntityDamageSource("unit_gun", entityLiving));
			laser.shoot(entityLiving, entityLiving.xRot, entityLiving.yRot, 0.0F, 2.5F, 0.0F);
			worldIn.addFreshEntity(laser);
			if (entityLiving instanceof ServerPlayerEntity && !((ServerPlayerEntity)entityLiving).isCreative()) {
				stack.hurt(1, random, (ServerPlayerEntity)entityLiving);
			}

		}

	}

	public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
		return super.onItemUseFirst(stack, context);
	}

	public boolean isValidRepairItem(ItemStack stack1, ItemStack stack2) {
		return this.tier.getRepairIngredient().test(stack2) || super.isValidRepairItem(stack1, stack2);
	}
}
