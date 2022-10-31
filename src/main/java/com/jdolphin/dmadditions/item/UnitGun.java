package com.jdolphin.dmadditions.item;

import com.jdolphin.dmadditions.entity.BulletEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class UnitGun extends Item {
	public UnitGun(Item.Properties properties) {
		super(properties);
	}

	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerEntity, Hand handIn) {
		ItemStack itemstack = playerEntity.getItemInHand(handIn);
		worldIn.playSound((PlayerEntity)null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		if (!worldIn.isClientSide) {
			BulletEntity bulletEntity = new BulletEntity(worldIn, playerEntity);
			bulletEntity.setItem(itemstack);
			bulletEntity.shootFromRotation(playerEntity, playerEntity.xRot, playerEntity.yRot, 0.0F, 1.5F, 1.0F);
			worldIn.addFreshEntity(bulletEntity);
		}

		playerEntity.awardStat(Stats.ITEM_USED.get(this));
		if (!playerEntity.abilities.instabuild) {
			itemstack.shrink(1);
		}

		return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide());
	}
}
