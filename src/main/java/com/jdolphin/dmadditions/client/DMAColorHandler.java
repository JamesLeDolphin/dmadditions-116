package com.jdolphin.dmadditions.client;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class DMAColorHandler {

	public static int getColor(ItemStack stack, int tint){
		if(tint == 0){
			CompoundNBT compoundnbt = stack.getTagElement("display");
			return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 10511680;
		}
		return 0xFFFFFF;
	}
}
