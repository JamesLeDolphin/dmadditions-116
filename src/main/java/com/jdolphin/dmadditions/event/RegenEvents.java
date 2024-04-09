package com.jdolphin.dmadditions.event;

import com.jdolphin.dmadditions.cap.IPlayerDataCap;
import com.jdolphin.dmadditions.init.DMACapabilities;
import com.swdteam.util.ChatUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RegenEvents {

	@SubscribeEvent
	public static void playerDamageEvent(LivingDamageEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof ServerPlayerEntity) {
			PlayerEntity player = ((PlayerEntity) entity);
			player.getCapability(DMACapabilities.PLAYER_DATA).ifPresent(cap -> {
				if (player.getHealth() - event.getAmount() <= 0.0 && cap.hasRegens() && !cap.isPreRegen()) {
					event.setCanceled(!event.getSource().isBypassInvul());
					ChatUtil.sendMessageToPlayer(player, new StringTextComponent("You're about to regenerate. Punch a block to hold back"),
						ChatUtil.MessageType.CHAT);
					cap.setPreRegen();
					cap.update();
				}
			});
		}
	}

	@SubscribeEvent
	public static void playerRespawnEvent(PlayerEvent.PlayerRespawnEvent event) {
		PlayerEntity player = event.getPlayer();
		if (player instanceof ServerPlayerEntity) {
			player.getCapability(DMACapabilities.PLAYER_DATA).ifPresent(IPlayerDataCap::update);
		}
	}

	@SubscribeEvent
	public static void playerChangeDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
		PlayerEntity player = event.getPlayer();
		if (player instanceof ServerPlayerEntity) {
			player.getCapability(DMACapabilities.PLAYER_DATA).ifPresent(IPlayerDataCap::update);
		}
	}

	@SubscribeEvent
	public static void playerCloneEvent(PlayerEvent.Clone event) {
		PlayerEntity player = event.getPlayer();
		if (player instanceof ServerPlayerEntity) {
			player.getCapability(DMACapabilities.PLAYER_DATA).ifPresent(IPlayerDataCap::update);
		}
	}

	@SubscribeEvent
	public static void playerTickEvent(TickEvent.PlayerTickEvent event) {
		PlayerEntity player = event.player;
		if (player instanceof ServerPlayerEntity) {
			player.getCapability(DMACapabilities.PLAYER_DATA).ifPresent(IPlayerDataCap::tick);
		}
	}

	@SubscribeEvent
	public static void playerDestroyEvent(PlayerInteractEvent.LeftClickBlock event) {
		PlayerEntity player = event.getPlayer();
		if (player instanceof ServerPlayerEntity) {
			player.getCapability(DMACapabilities.PLAYER_DATA).ifPresent(cap -> {
				if (cap.canPostpone()) {
					cap.postpone();
					cap.update();
				}
			});
		}
	}
}
