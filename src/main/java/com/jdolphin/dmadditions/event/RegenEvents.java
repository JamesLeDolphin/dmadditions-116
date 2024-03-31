package com.jdolphin.dmadditions.event;

import com.jdolphin.dmadditions.cap.IPlayerDataCap;
import com.jdolphin.dmadditions.init.DMACapabilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RegenEvents {

	@SubscribeEvent
	public static void playerDamageEvent(LivingDamageEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof PlayerEntity) {
			PlayerEntity player = ((PlayerEntity) entity);
			player.getCapability(DMACapabilities.PLAYER_DATA).ifPresent(cap -> {
				if (player.getHealth() - event.getAmount() <= 0.0 && cap.hasRegens() && !cap.isPreRegen()) {
					event.setCanceled(!event.getSource().isBypassInvul());
					cap.setPreRegen(true);
					cap.update();
				}
			});
		}
	}

	@SubscribeEvent
	public static void playerTickEvent(TickEvent.PlayerTickEvent event) {
		PlayerEntity player = event.player;
		player.getCapability(DMACapabilities.PLAYER_DATA).ifPresent(IPlayerDataCap::tick);
	}

	@SubscribeEvent
	public static void playerDestroyEvent(PlayerInteractEvent.LeftClickBlock event) {
		PlayerEntity player = event.getPlayer();
		player.getCapability(DMACapabilities.PLAYER_DATA).ifPresent(cap -> {
			if (cap.canPostpone() && cap.isPreRegen()) {
				cap.postpone();
				cap.update();
			}
		});
	}
}
