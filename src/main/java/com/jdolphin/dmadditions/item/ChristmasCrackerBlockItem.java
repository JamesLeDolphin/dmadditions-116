package com.jdolphin.dmadditions.item;

import com.jdolphin.dmadditions.block.christmas.ChristmasCrackerBlock;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChristmasCrackerBlockItem extends BlockItem {

	public ChristmasCrackerBlockItem(Block type, Properties properties) {
		super(type, properties);
	}

	public ChristmasCrackerBlockItem(RegistryObject<Block> type, Item.Properties properties) {
		super(type.get(), properties);
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if(!world.isClientSide) ChristmasCrackerBlock.openCracker(world, player.getEyePosition(1).add(player.getLookAngle()));
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
