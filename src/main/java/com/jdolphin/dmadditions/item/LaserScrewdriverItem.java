package com.jdolphin.dmadditions.item;

import com.jdolphin.dmadditions.init.DMASoundEvents;
import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.item.sonics.SonicScrewdriverItem;
import com.swdteam.util.ChatUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import java.util.Calendar;

public class LaserScrewdriverItem extends SonicScrewdriverItem {
	private RegistryObject<SoundEvent> sonicSound;
	private final DMProjectiles.Laser laserType;
	protected boolean shootMode = false;
	public float attackDamage = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ? 4.5f : 3; // torchwood_one says 3 or 4.5 on a good day :)
// if tw1 said that then sure ig
	public boolean shootMode() {
		return shootMode;
	}


	public LaserScrewdriverItem(ItemGroup itemGroup, int xpValue, DMProjectiles.Laser laserType) {
		super(itemGroup, xpValue);
		this.laserType = laserType;
		this.setSonicSound(DMASoundEvents.LASER_SONIC_SHOOT);
	}

	private SoundEvent getSonicSound() {
		return this.sonicSound.get();

	}

	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (!world.isClientSide) {
			if (player.isCrouching()) {
				shootMode = !shootMode();

				if (shootMode) {
					ChatUtil.sendError(player,
						new TranslationTextComponent("item.dmadditions.laser_screwdriver.mode.laser"),
						ChatUtil.MessageType.STATUS_BAR);
				} else {
					ChatUtil.sendCompletedMsg(player,
						new TranslationTextComponent("item.dmadditions.laser_screwdriver.mode.sonic"),
						ChatUtil.MessageType.STATUS_BAR);
				}

				world.playSound(null, player, DMSoundEvents.TARDIS_CONTROLS_BUTTON_CLICK.get(),
					SoundCategory.PLAYERS, 1, 1);

				return ActionResult.consume(stack);
			}

			if (!shootMode()) {
				return super.use(world, player, hand);
			}

			LaserEntity laser = new LaserEntity(world, player, 0.0F, this.attackDamage);
			laser.setLaserType(this.laserType);
			laser.setDamageSource(new EntityDamageSource("laser_screwdriver", player));
			laser.shoot(player, player.xRot, player.yRot, 0.0F, 2.5F, 0.0F);
			world.addFreshEntity(laser);
			if (player instanceof ServerPlayerEntity && !player.isCreative()) {
				stack.hurt(1, random, (ServerPlayerEntity) player);
				player.getCooldowns().addCooldown(this, 20);
			}
			world.playSound(null, player, DMASoundEvents.LASER_SONIC_SHOOT.get(),
				SoundCategory.PLAYERS, 1, 1);
			return ActionResult.consume(stack);
		}

		return super.use(world, player, hand);
	}
}
