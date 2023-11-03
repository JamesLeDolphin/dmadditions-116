package com.jdolphin.dmadditions.item;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class BaubleBlockItem extends BlockItem {
	public BaubleBlockItem(Item.Properties properties, Block block) {
		super(block, properties);
	}

	public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
		ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);
		p_77659_1_.playSound((PlayerEntity)null, p_77659_2_.getX(), p_77659_2_.getY(), p_77659_2_.getZ(), SoundEvents.SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		if (!p_77659_1_.isClientSide) {
			SnowballEntity snowballentity = new SnowballEntity(p_77659_1_, p_77659_2_);
			snowballentity.setItem(itemstack);
			snowballentity.shootFromRotation(p_77659_2_, p_77659_2_.xRot, p_77659_2_.yRot, 0.0F, 1.5F, 1.0F);
			p_77659_1_.addFreshEntity(snowballentity);
		}

		p_77659_2_.awardStat(Stats.ITEM_USED.get(this));
		if (!p_77659_2_.abilities.instabuild) {
			itemstack.shrink(1);
		}

		return ActionResult.sidedSuccess(itemstack, p_77659_1_.isClientSide());
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {
		if (!context.getLevel().getBlockState(context.getClickedPos()).is(Blocks.AIR)) {
			this.use(context.getLevel(), context.getPlayer(), context.getHand());
		}

		ActionResultType resultType = this.place(new BlockItemUseContext(context));
		return !resultType.consumesAction() && this.isEdible() ? this.use(context.getLevel(), context.getPlayer(), context.getHand()).getResult() : resultType;
	}
}
