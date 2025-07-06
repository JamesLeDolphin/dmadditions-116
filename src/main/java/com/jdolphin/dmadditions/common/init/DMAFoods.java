package com.jdolphin.dmadditions.common.init;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class DMAFoods {
	public static final Food DINO_NUGGETS = (new Food.Builder()).nutrition(10).saturationMod(1.3F).build();
	public static final Food DINO_NUGGETS_CUSTARD = (new Food.Builder()).nutrition(10).saturationMod(1.3F).build();
	public static final Food KANTROFARRI = (new Food.Builder()).nutrition(2).saturationMod(1F).effect(
		() -> new EffectInstance(Effects.POISON, 100), 0.4f).build();
	public static final Food KANTROFARRI_COOKED = (new Food.Builder()).nutrition(12).saturationMod(1.3F).build();

}
