package com.jdolphin.dmadditions.item;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.jdolphin.dmadditions.block.christmas.ChristmasCrackerBlock;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class ChristmasCrackerBlockItem extends BlockItem {

	public ChristmasCrackerBlockItem(Block type, Properties properties) {
		super(type, properties);
	}

	public ChristmasCrackerBlockItem(RegistryObject<Block> type){
		super(type.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS));
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if(!world.isClientSide) ChristmasCrackerBlock.openCracker(world, player.getEyePosition(1).add(player.getForward()));
		ItemStack item = player.getItemInHand(hand);
		item.shrink(1);
		return ActionResult.consume(item);
	}



	@Override
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslationTextComponent("tooltip.item.dmadditions.christmas_cracker").withStyle(TextFormatting.GRAY));
	}
}
