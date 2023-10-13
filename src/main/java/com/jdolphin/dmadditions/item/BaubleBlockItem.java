package com.jdolphin.dmadditions.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class BaubleBlockItem extends BlockItem {
	public BaubleBlockItem(RegistryObject<Block> type, Item.Properties properties) {
		super(type.get(), properties);
	}

	public BaubleBlockItem(RegistryObject<Block> type) {
		super(type.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS));
	}

	public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		if(playerEntity.isCrouching()){
			return ActionResult.success(playerEntity.getItemInHand(hand));
		}
		ItemStack itemstack = playerEntity.getItemInHand(hand);
		world.playSound((PlayerEntity)null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		if (!world.isClientSide) {
			SnowballEntity snowballentity = new SnowballEntity(world, playerEntity){
				@Override
				protected void onHitBlock(BlockRayTraceResult blockRayTraceResult) {
					super.onHitBlock(blockRayTraceResult);
					this.level.explode(this, this.getX(), this.getY(), this.getZ(), 1, Explosion.Mode.DESTROY);
				}
			};
			snowballentity.setItem(itemstack);
			snowballentity.shootFromRotation(playerEntity, playerEntity.xRot, playerEntity.yRot, 0.0F, 1.5F, 1.0F);
			world.addFreshEntity(snowballentity);
		}

		playerEntity.awardStat(Stats.ITEM_USED.get(this));
		if (!playerEntity.abilities.instabuild) {
			itemstack.shrink(1);
		}

		return ActionResult.sidedSuccess(itemstack, world.isClientSide());
	}
}
