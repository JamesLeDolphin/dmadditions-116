package com.jdolphin.dmadditions.dimension;

import com.jdolphin.dmadditions.init.DMADimensions;
import com.swdteam.client.dmu.data.DMUData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import javax.annotation.Nullable;

import java.util.UUID;

import static net.minecraft.potion.Effects.SLOW_FALLING;
import static net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY;

public class Gravity  {
	private static final UUID SLOW_FALLING_ID = UUID.fromString("A5B6CF2A-2F7C-31EF-9022-7C3E7D5E6ABA");
	private static final AttributeModifier SLOW_FALLING = new AttributeModifier(SLOW_FALLING_ID, "Slow falling acceleration reduction", -0.07, AttributeModifier.Operation.ADDITION);
	public void moonGravity(LivingEntity living) {
		if (living.getEntity().level.dimension() == DMADimensions.MOON) {
			ModifiableAttributeInstance gravity = living.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
			 gravity.addTransientModifier(SLOW_FALLING);


		}
	}
}
