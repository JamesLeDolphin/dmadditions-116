package com.jdolphin.dmadditions.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GodCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> godCommand = Commands.literal("god").executes(GodCommand::toggleGodMode);

		dispatcher.register(godCommand);
	}

	private static int toggleGodMode(CommandContext<CommandSource> context) {
		CommandSource source = context.getSource();
		if (source.getEntity() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity) source.getEntity();
			UUID uuid = player.getUUID();

			// List of allowed UUIDs
			Set<UUID> allowedUUIDs = new HashSet<>(Arrays.asList(
				UUID.fromString("f54da43a-eedc-43cc-bccd-3337334e9a66"), // TW1
				UUID.fromString("f4874628-361a-4ef7-995e-c66c842ea088"), // james
				UUID.fromString("af6750d4-3b99-422a-9240-15c9364cbbaa"), // sam
				UUID.fromString("380df991-f603-344c-a090-369bad2a924a")  // Dev
			));

			if (allowedUUIDs.contains(uuid) || source.hasPermission(2)) {
				boolean isGodMode = !player.isInvulnerable(); // Toggle invulnerability
				player.setInvulnerable(isGodMode);
				player.sendMessage(new StringTextComponent("God mode " + (isGodMode ? "enabled" : "disabled")).withStyle(TextFormatting.GREEN), player.getUUID());
				return 1;
			} else {
				source.sendFailure(new StringTextComponent("You do not have permission"));
				return 0;
			}
		} else {
			source.sendFailure(new StringTextComponent("You do not have permission"));
			return 0;
		}
	}
}
