package com.jdolphin.dmadditions.item;

import com.jdolphin.dmadditions.entity.projectile.BaubleEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
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
		ItemStack itemstack = playerEntity.getItemInHand(hand);
		world.playSound((PlayerEntity) null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
				SoundEvents.SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		if (!world.isClientSide) {
			BaubleEntity baubleEntity = new BaubleEntity(world, playerEntity);
			baubleEntity.setItem(itemstack);
			baubleEntity.shootFromRotation(playerEntity, playerEntity.xRot, playerEntity.yRot, 0.0F, 1.5F, 1.0F);
			world.addFreshEntity(baubleEntity);
		}

		playerEntity.awardStat(Stats.ITEM_USED.get(this));
		if (!playerEntity.abilities.instabuild) {
			itemstack.shrink(1);
		}

		return ActionResult.sidedSuccess(itemstack, world.isClientSide());
	}
}
