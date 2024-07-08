package com.jdolphin.dmadditions.event;

import com.jdolphin.dmadditions.cap.IPlayerRegenCap;
import com.jdolphin.dmadditions.init.DMACapabilities;
import com.swdteam.util.ChatUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
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
			player.getCapability(DMACapabilities.REGEN_CAP_CAPABILITY).ifPresent(cap -> {
				if (player.getHealth() - event.getAmount() <= 0.0 && cap.hasRegens() && !cap.isPreRegen() && !cap.postponed() && !cap.isRegenerating()) {
					event.setCanceled(!event.getSource().isBypassInvul());
					cap.setPreRegen(true);
					ChatUtil.sendMessageToPlayer(player, new StringTextComponent("You're about to regenerate. Punch a block to hold back"),
						ChatUtil.MessageType.CHAT);
					cap.update();
				}
			});
		}
	}

	@SubscribeEvent
	public static void playerRespawnEvent(PlayerEvent.PlayerRespawnEvent event) {
		PlayerEntity player = event.getPlayer();

		if (player instanceof ServerPlayerEntity) {
			player.getCapability(DMACapabilities.REGEN_CAP_CAPABILITY).ifPresent(cap -> {
				cap.setPreRegen(false);
				cap.setPreRegen(false);
				cap.setRegenTicks(0);
				cap.update();
			});
		}
	}

	@SubscribeEvent
	public static void playerChangeDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
		PlayerEntity player = event.getPlayer();
		if (player instanceof ServerPlayerEntity) {
			player.getCapability(DMACapabilities.REGEN_CAP_CAPABILITY).ifPresent(IPlayerRegenCap::update);
		}
	}

	@SubscribeEvent
	public static void playerCloneEvent(PlayerEvent.Clone event) {
		PlayerEntity newPlayer = event.getPlayer();
		PlayerEntity oldPlayer = event.getOriginal();
		if (newPlayer instanceof ServerPlayerEntity) {
			IPlayerRegenCap cap = oldPlayer.getCapability(DMACapabilities.REGEN_CAP_CAPABILITY).resolve().get();
			newPlayer.getCapability(DMACapabilities.REGEN_CAP_CAPABILITY).ifPresent(iPlayerRegenCap -> iPlayerRegenCap.deserializeNBT(cap.serializeNBT()));
		}
	}

	@SubscribeEvent
	public static void playerTickEvent(TickEvent.PlayerTickEvent event) {
		PlayerEntity player = event.player;
		if (player instanceof ServerPlayerEntity) {
			player.getCapability(DMACapabilities.REGEN_CAP_CAPABILITY).ifPresent(IPlayerRegenCap::tick);
		}
	}

	@SubscribeEvent
	public static void playerDestroyEvent(PlayerInteractEvent.LeftClickBlock event) {
		PlayerEntity player = event.getPlayer();
		if (player instanceof ServerPlayerEntity) {
			player.getCapability(DMACapabilities.REGEN_CAP_CAPABILITY).ifPresent(cap -> {
				if (cap.canPostpone()) {
					cap.postpone(true);
					cap.update();
				}
			});
		}
	}
}
