package com.jdolphin.dmadditions.event;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.entity.player.ServerPlayerEntity;


@Mod.EventBusSubscriber
public class JoinServerEvent {

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
		ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
		ITextComponent message = new StringTextComponent("Using DMA on this server may have adverse side effects.").withStyle(TextFormatting.GOLD);
		player.sendMessage(message, player.getUUID());
	}
}
