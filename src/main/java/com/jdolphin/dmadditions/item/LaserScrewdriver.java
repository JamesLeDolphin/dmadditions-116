package com.jdolphin.dmadditions.item;

import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.item.sonics.SonicScrewdriverCustomizedItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class LaserScrewdriver extends SonicScrewdriverCustomizedItem {
	private RegistryObject<SoundEvent> sonicSound;
	private final DMProjectiles.Laser laserType;
	public float attackDamage;

	public boolean shootMode() {
		return true;
	}


	public LaserScrewdriver(ItemGroup itemGroup, int xpValue, DMProjectiles.Laser laserType) {
		super(itemGroup, xpValue);
		this.laserType = laserType;
	}

	private SoundEvent getSonicSound() {
		return (SoundEvent)this.sonicSound.get();

	}

	public ActionResultType use(World worldIn, PlayerEntity playerIn, Hand handIn, LivingEntity entityLiving, ItemStack stack) {
		if (!worldIn.isClientSide) {
			if (!shootMode()) {
				this.checkIsSetup(playerIn.getItemInHand(handIn));
				worldIn.playSound(playerIn, playerIn.blockPosition(), getSonicSound(), SoundCategory.PLAYERS, 0.5F, 1.0F);
			}

			if (shootMode()) {
				LaserEntity laser = new LaserEntity(worldIn, entityLiving, 0.0F, this.attackDamage);
				laser.setLaserType(this.laserType);
				laser.setDamageSource(new EntityDamageSource("laser_screwdriver", entityLiving));
				laser.shoot(entityLiving, entityLiving.xRot, entityLiving.yRot, 0.0F, 2.5F, 0.0F);
				worldIn.addFreshEntity(laser);
				if (entityLiving instanceof ServerPlayerEntity && !((ServerPlayerEntity) entityLiving).isCreative()) {
					stack.hurt(1, random, (ServerPlayerEntity) entityLiving);
				}
			}

			return ActionResultType.SUCCESS;
		}
		return ActionResultType.SUCCESS;
	}
	@Override
	public void setSonicSound(RegistryObject<SoundEvent> sonicSound) {
		this.sonicSound = DMSoundEvents.ENTITY_SONIC_ELEVENTH;
	}
}
