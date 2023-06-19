package com.jdolphin.dmadditions.event;

import com.jdolphin.dmadditions.dimension.Gravity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DMAEventHandlerGeneral {

	@SubscribeEvent
	public static void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
		Entity entity = event.getEntity();
		if (!(entity instanceof LivingEntity)) return;

		if (entity.level.isClientSide) return;

		Gravity.changeGravity((LivingEntity) entity, event.getDimension(), entity.level.dimension());
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (event.getWorld().isClientSide) return;

		Entity entity = event.getEntity();
		if (!(entity instanceof LivingEntity)) return;
		if (entity.tickCount > 0) return;

		RegistryKey<World> dimension = event.getWorld().dimension();
		if (!Gravity.DIMENSION_GRAVITY.containsKey(dimension)) return;

		Gravity.changeGravity((LivingEntity) entity, dimension, null);
	}
}
