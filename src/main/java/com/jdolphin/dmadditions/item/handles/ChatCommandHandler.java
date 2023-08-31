package com.jdolphin.dmadditions.item.handles;

import com.jdolphin.dmadditions.DmAdditions;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = DmAdditions.MODID)
public class ChatCommandHandler {
	@SubscribeEvent
	public static void onPlayerChat(ServerChatEvent event) {
		ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
		String message = event.getMessage();

		if (message.startsWith("handles ")) {
			String question = message.substring("handles ".length());
			String response = ChatGPTIntegration.askQuestion(question);

			ITextComponent chatResponse = new StringTextComponent("[Handles]: " + response);
			player.sendMessage(chatResponse, UUID.randomUUID());

			event.setCanceled(true);
		}
	}
}
