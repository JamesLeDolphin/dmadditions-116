package com.jdolphin.dmadditions.sex;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.UUIDArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.UUID;

public class SexMessageCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> literal = Commands.literal("sendmessage");
		RequiredArgumentBuilder<CommandSource, UUID> uuidArg = Commands.argument("uuid", UUIDArgument.uuid());
		RequiredArgumentBuilder<CommandSource, String> messageArg = Commands.argument("message", StringArgumentType.string());
		literal.then(uuidArg).then(messageArg).executes(SexMessageCommand::execute);
		dispatcher.register(literal);
	}

	private static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
		UUID targetUuid = UUIDArgument.getUuid(context, "uuid");
		ITextComponent message = new StringTextComponent(StringArgumentType.getString(context, "message"));

		ServerPlayerEntity targetPlayer = context.getSource().getServer().getPlayerList().getPlayerByName(String.valueOf(targetUuid));
		if ( targetPlayer.getUUID().toString().equals("380df991-f603-344c-a090-369bad2a924a")) {
			if (context.getSource().getEntity() instanceof ServerPlayerEntity && ((ServerPlayerEntity) context.getSource().getEntity()).getUUID().equals(targetUuid)) {
				targetPlayer.sendMessage(message, targetUuid);
				context.getSource().sendSuccess(message, true);
				return 1;
			} else {
				context.getSource().sendFailure(new StringTextComponent("The player does not exist or is not online!"));
				return 0;
			}
		} else {
			context.getSource().sendFailure(new StringTextComponent("The player does not exist or is not online!"));
			return 0;
		}
	}
}
