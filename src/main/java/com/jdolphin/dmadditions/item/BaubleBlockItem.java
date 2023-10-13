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

	public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
		ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);
		p_77659_1_.playSound((PlayerEntity) null, p_77659_2_.getX(), p_77659_2_.getY(), p_77659_2_.getZ(),
				SoundEvents.SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		if (!p_77659_1_.isClientSide) {
			BaubleEntity baubleEntity = new BaubleEntity(p_77659_1_, p_77659_2_);
			baubleEntity.setItem(itemstack);
			baubleEntity.shootFromRotation(p_77659_2_, p_77659_2_.xRot, p_77659_2_.yRot, 0.0F, 1.5F, 1.0F);
			p_77659_1_.addFreshEntity(baubleEntity);
		}

		p_77659_2_.awardStat(Stats.ITEM_USED.get(this));
		if (!p_77659_2_.abilities.instabuild) {
			itemstack.shrink(1);
		}

		return ActionResult.sidedSuccess(itemstack, p_77659_1_.isClientSide());
	}
}
