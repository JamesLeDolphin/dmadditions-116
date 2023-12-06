package com.jdolphin.dmadditions.item;

import com.jdolphin.dmadditions.block.christmas.ChristmasCrackerBlock;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.util.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.registration.ItemProperties;

import java.util.List;

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
		ItemUtils.addText(tooltip, "Open to reveal a surprise!", TextFormatting.GRAY);
	}
}
