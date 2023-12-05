package com.jdolphin.dmadditions.client;

import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.item.ChristmasHatItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.client.event.ColorHandlerEvent;

public class DMAColorHandler {
	public static void registerItemColor(ColorHandlerEvent.Item event) {
		event.getItemColors().register(DMAColorHandler::getColor, DMAItems.CHRISTMAS_HAT.get());
	}

	public static int getColor(ItemStack stack, int tint){
		if(tint == 0){
			CompoundNBT compoundnbt = stack.getTagElement("display");
			return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 10511680;
		}
		return 0xFFFFFF;
	}
}
