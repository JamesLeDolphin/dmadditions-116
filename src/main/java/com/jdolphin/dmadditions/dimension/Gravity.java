package com.jdolphin.dmadditions.dimension;

import com.jdolphin.dmadditions.init.DMADimensions;
import com.swdteam.client.dmu.data.DMUData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import static net.minecraft.potion.Effects.SLOW_FALLING;
import static net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY;

public class Gravity {

	public static void moonGravity(LivingEntity living) {
		if (living.getEntity().level.dimension() == DMADimensions.MOON) {
			living.getAttribute(Attributes.JUMP_STRENGTH);
			living.getEffect(Effects.SLOW_FALLING).applyEffect(living); //new EffectInstance(Effects.SLOW_FALLING, 500, 70).applyEffect(living);
			new EffectInstance(Effects.JUMP, 500, 70).applyEffect(living);
		}
	}
}
