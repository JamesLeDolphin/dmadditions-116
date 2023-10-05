package com.jdolphin.dmadditions.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BaubleBlockItem extends BlockItem {
	public BaubleBlockItem() {
		super(Blocks.ANDESITE, new Item.Properties().tab(ItemGroup.TAB_MISC));
	}

	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		PlayerEntity player = context.getPlayer();
		Hand hand = context.getHand();

		if (world.isEmptyBlock(pos) || world.getBlockState(pos).getMaterial().isReplaceable()) {
			if (!world.isClientSide()) {
				world.setBlockAndUpdate(pos, Blocks.TNT.defaultBlockState());
				if (!player.isCreative()) {
					player.getItemInHand(hand).shrink(1);
				}
			}
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.FAIL;
	}
}
