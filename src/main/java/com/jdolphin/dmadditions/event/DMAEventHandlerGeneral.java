package com.jdolphin.dmadditions.event;

import com.jdolphin.dmadditions.dimension.Gravity;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisSaveHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.awt.*;
import java.util.List;

public class DMAEventHandlerGeneral {

	@SubscribeEvent
	public static void onPlayerChat(ServerChatEvent event) {
		PlayerEntity player = event.getPlayer();
		String message = event.getMessage().toLowerCase();
		if (message.contains("handles")) {
			String q = message.substring(7).toLowerCase();
			{
			if (q.isEmpty()) {
				player.displayClientMessage(new StringTextComponent("<Handles> How may I help you " + player.getName().getString() + "?"), false);
				event.setCanceled(true);
			}
				if ((q.startsWith("echo"))) {
					String e = q.substring(4);
					player.displayClientMessage(new StringTextComponent("<Handles> " + e), false);
					event.setCanceled(true);
				}
				if ((q.startsWith("say"))) {
					String e = q.substring(3);
					player.displayClientMessage(new StringTextComponent("<Handles> " + e), false);
					event.setCanceled(true);
				}
				if ((q.contains("who") || q.contains("whos") || q.contains("who's") || q.contains("who is")) && (q.contains("near")
					|| q.contains("nearest") || q.contains("close"))) {
					PlayerEntity closestPlayer = player.level.getNearestPlayer(EntityPredicate.DEFAULT, player);
					if (closestPlayer != null) {
						ITextComponent n = closestPlayer.getName();
						player.displayClientMessage(new StringTextComponent("<Handles> " + n + " is the closest player to you"), false);
					} else
						player.displayClientMessage(new StringTextComponent("<Handles> I am unable to find anyone near you"), false);
					event.setCanceled(true);
				}
				if ((q.contains("where") || q.contains("wheres") || q.contains("where's") || q.contains("where is") || q.contains("wher ma")) && (q.contains("tardis")
					|| q.contains("tardus") || q.contains("ship"))) {
					List<Integer> ids = DMTardis.getUserTardises(player.getUUID()).getTardises();
					if (!ids.isEmpty()) {
						for (int id : ids) {
							int tardis = DMTardis.getTardis(id).getGlobalID();
							TardisData data = DMTardis.getTardis(tardis);
							BlockPos loc = DMTardis.getTardis(id).getCurrentLocation().getBlockPosition();
							ResourceLocation dim = DMTardis.getTardis(id).getCurrentLocation().dimensionWorldKey().location();
							player.displayClientMessage(new StringTextComponent("<Handles> Your TARDIS (" + id + ") is at " + loc.getX() + "; " + loc.getY() + "; " + loc.getZ() +
									"; Dimension: " + dim),
								false);
							event.setCanceled(true);
						}
					} else {
						player.displayClientMessage(new StringTextComponent("<Handles> Unable to find your TARDIS"), false);
						event.setCanceled(true);
					}
				}

			}

		}
	}

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
