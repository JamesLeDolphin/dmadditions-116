package com.jdolphin.dmadditions.event;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class JoinServerEvent {

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
		ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
		MinecraftServer server = player.getServer();

		if(server == null || server.getLocalIp() == null) return;

		if (server.getLocalIp().equals("dmu.swdteam.co.uk:25565")) {
			player.connection.disconnect(new StringTextComponent("Don't connect to DMU with DMA installed!"));
			player.connection.connection.handleDisconnection();
			event.setCanceled(true);
			event.setResult(Event.Result.DENY);
		}
	}

}
