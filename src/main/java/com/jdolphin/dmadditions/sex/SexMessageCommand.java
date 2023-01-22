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
			if (uuid.toString().equals("94e96cfa-02e5-4231-9b35-4fb39d0912f5") ||
				uuid.toString().equals("947f2cb3-98a4-4a8a-a4d3-6896c2fbb233") ||
				uuid.toString().equals("f54da43a-eedc-43cc-bccd-3337334e9a66") ||
				uuid.toString().equals("380df991-f603-344c-a090-369bad2a924a")) {
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
