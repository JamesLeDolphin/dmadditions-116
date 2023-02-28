package com.jdolphin.dmadditions.event;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.Objects;


@Mod.EventBusSubscriber
public class JoinServerEvent {

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
		ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
		if (Objects.requireNonNull(player.getServer()).getLocalIp().equals("dmu.swdteam.co.uk:25565")) {
			player.connection.disconnect(new StringTextComponent("Please remove DMA before joining DMU"));
		} else {
			ITextComponent message = new StringTextComponent("Using DMA on this server may have adverse side effects.").withStyle(TextFormatting.BOLD).withStyle(TextFormatting.RED);
			player.sendMessage(message, player.getUUID());
		}

		}
}