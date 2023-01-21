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
		LiteralArgumentBuilder<CommandSource> literal = Commands.literal("sex");
		literal.executes(SexMessageCommand::execute);
		dispatcher.register(literal);
	}

	private static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
		ITextComponent message = new StringTextComponent("you don't get sex you get not bitches, jk");
		CommandSource source = context.getSource();
		if (source.getEntity() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity) source.getEntity();
			UUID uuid = player.getUUID();
			if (uuid.toString().equals("9d7f31b4-17cb-4ee5-bf00-1652682083ae")) {
				player.sendMessage(message, uuid);
//				context.getSource().sendSuccess(message, true);
				return 1;
			} else {
				context.getSource().sendFailure(new StringTextComponent("You cannot use this command!"));
				return 0;
			}
		} else {
			context.getSource().sendFailure(new StringTextComponent("You cannot use this command!"));
			return 0;
		}
	}
}
