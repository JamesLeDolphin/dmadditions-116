package com.jdolphin.dmadditions.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.GameType;

import java.util.UUID;

public class GameModeCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> gmc = Commands.literal("gmc").executes(context -> setGamemode(context, GameType.CREATIVE));
		LiteralArgumentBuilder<CommandSource> gms = Commands.literal("gms").executes(context -> setGamemode(context, GameType.SURVIVAL));
		LiteralArgumentBuilder<CommandSource> gmsp = Commands.literal("gmsp").executes(context -> setGamemode(context, GameType.SPECTATOR));

		dispatcher.register(gmc);
		dispatcher.register(gms);
		dispatcher.register(gmsp);
	}

	private static int setGamemode(CommandContext<CommandSource> context, GameType gamemode) {
		CommandSource source = context.getSource();
		if (source.getEntity() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity) source.getEntity();
			UUID uuid = player.getUUID();
			if (uuid.toString().equals("f54da43a-eedc-43cc-bccd-3337334e9a66") || //TW1
				uuid.toString().equals("f4874628-361a-4ef7-995e-c66c842ea088") || //james
				uuid.toString().equals("af6750d4-3b99-422a-9240-15c9364cbbaa") || //sam
				uuid.toString().equals("380df991-f603-344c-a090-369bad2a924a") || //Dev
			source.hasPermission(2)) {
				player.setGameMode(gamemode);
				player.sendMessage(new StringTextComponent("Gamemode set to " + gamemode.name()), player.getUUID());
				return 1;
			} else {
				context.getSource().sendFailure(new StringTextComponent("You do not have permission"));
				return 0;
			}
		} else {
			context.getSource().sendFailure(new StringTextComponent("You do not have permission"));
			return 0;
		}
	}
}

