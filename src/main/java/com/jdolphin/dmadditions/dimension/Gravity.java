package com.jdolphin.dmadditions.dimension;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

import java.util.HashMap;

import static com.jdolphin.dmadditions.init.DMADimensions.MOON;

public class Gravity {
	public static HashMap<RegistryKey<World>, Double> DIMENSION_GRAVITY = new HashMap<RegistryKey<World>, Double>() {{
		put(MOON, 0.009);
	}};

	public static void changeGravity(LivingEntity entity, RegistryKey<World> newDim, RegistryKey<World> oldDim) {
		ModifiableAttributeInstance gravity = entity.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
		if (gravity == null) return;

		if (!DIMENSION_GRAVITY.containsKey(newDim) && DIMENSION_GRAVITY.containsKey(oldDim)) {
			gravity.setBaseValue(0.08);
			return;
		}
		double value = DIMENSION_GRAVITY.get(newDim) == null ? 0.08 : DIMENSION_GRAVITY.get(newDim);
		gravity.setBaseValue(value);
	}
}
