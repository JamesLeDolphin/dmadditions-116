package com.jdolphin.dmadditions.item;

import com.jdolphin.dmadditions.DmAdditions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class JokeItem extends Item {
	public JokeItem(Properties properties) {
		super(properties);
	}

	public JokeItem(){
		super(new Properties().stacksTo(1));
	}

	public static String[] getJoke(ItemStack stack){
		CompoundNBT compoundnbt = stack.getTagElement("joke");
		if(compoundnbt != null && compoundnbt.contains("question") && compoundnbt.contains("answer")){
			return new String[] {
				compoundnbt.get("question").toString(),
				compoundnbt.get("answer").toString()
			};
		}
		return new String[] {
			"Why did the customer refund their christmas crackers?",
			"Because they didn't have any jokes."
		};
	}

	public static void setJoke(ItemStack stack, String question, String answer){
		stack.getOrCreateTagElement("joke").putString("question", question);
		stack.getTagElement("joke").putString("answer", answer);
	}

	public static void setJoke(ItemStack stack, String[] joke){
		setJoke(stack, joke[0], joke[1]);
	}

	public static String[] randomJoke(World world){
		return new String[] {"Q","A"};
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if(world.isClientSide) {
			String[] joke = getJoke(player.getItemInHand(hand));
			StringTextComponent text = new StringTextComponent(player.isShiftKeyDown() ? joke[1] : joke[0]);
			TextFormatting format = player.isShiftKeyDown() ? TextFormatting.GOLD : TextFormatting.LIGHT_PURPLE;
			player.displayClientMessage(text.withStyle(format).withStyle(TextFormatting.BOLD), true);
			player.getCooldowns().addCooldown(this, 10);
			return ActionResult.success(player.getItemInHand(hand));
		}
		return ActionResult.fail(player.getItemInHand(hand));
	}
}
