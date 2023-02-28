package com.jdolphin.dmadditions.commands;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;

import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;


public class ToggleModeCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("togglemode").requires(player -> player.hasPermission(2)).executes(context -> {
			CommandSource source = context.getSource();

			if (!(source.getEntity() instanceof ServerPlayerEntity)) {
				return 0;
			}

			ServerPlayerEntity player = (ServerPlayerEntity) source.getEntity();
			GameType currentMode = player.gameMode.getGameModeForPlayer();

			if (currentMode == GameType.CREATIVE) {
				player.setGameMode(GameType.SURVIVAL);
			} else {
				player.setGameMode(GameType.CREATIVE);
			}

			ITextComponent modeName = new TranslationTextComponent("gameMode." + player.gameMode.getGameModeForPlayer().getName());
			source.sendSuccess(new TranslationTextComponent("commands.gamemode.success.self", modeName), true);
			return 1;
		}));
	}
}
