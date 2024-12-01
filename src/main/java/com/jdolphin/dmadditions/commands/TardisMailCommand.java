package com.jdolphin.dmadditions.commands;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

import com.jdolphin.dmadditions.advent.TimedUnlock;
import com.jdolphin.dmadditions.init.DMACommands;
import com.jdolphin.dmadditions.tardismail.TardisMail;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;

public class TardisMailCommand {
	public static final SimpleCommandExceptionType ERROR_TARDIS_NOT_FOUND = new SimpleCommandExceptionType(
			new TranslationTextComponent("command.dalekmod.error.tardis.no_tardis"));

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> mail = Commands.literal("tardis-mail")
				.requires(source -> TimedUnlock.advent(19))
				.then(Commands.literal("spawn")
						.requires(source -> source.hasPermission(2))
						.then(Commands.argument("id", IntegerArgumentType.integer())
								.then(Commands.argument("item", ItemArgument.item())
										.executes(TardisMailCommand::spawn))))
				.then(Commands.literal("send")
						.requires(source -> source.getLevel().dimension().equals(DMDimensions.TARDIS))
						.then(Commands.argument("id", IntegerArgumentType.integer())
								.executes(TardisMailCommand::send)));
		DMACommands.register(dispatcher, mail);
	}

	public static TardisData getTardis(int id) throws CommandSyntaxException {
		TardisData data = DMTardis.getTardis(id);
		if (data == null)
			throw ERROR_TARDIS_NOT_FOUND.create();

		return data;
	}

	private static int send(CommandContext<CommandSource> context) throws CommandSyntaxException {
		CommandSource source = context.getSource();
		MinecraftServer server = source.getServer();
		PlayerEntity player = source.getPlayerOrException();

		int id = IntegerArgumentType.getInteger(context, "id");
		TardisData data = getTardis(id);

		ItemStack mainHandItem = player.getMainHandItem();

		if (TardisMail.send(server, data, mainHandItem)) {
			player.setItemInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
		}

		source.sendSuccess(new TranslationTextComponent("commands.tardis-mail.send.success",
				mainHandItem.getCount(), mainHandItem.getDisplayName(), id), true);

		return SINGLE_SUCCESS;
	}

	private static int spawn(CommandContext<CommandSource> context) throws CommandSyntaxException {
		CommandSource source = context.getSource();
		MinecraftServer server = source.getServer();

		int id = IntegerArgumentType.getInteger(context, "id");
		TardisData data = getTardis(id);

		ItemInput itemInput = ItemArgument.getItem(context, "item");
		ItemStack stack = itemInput.createItemStack(1, true);

		if (TardisMail.send(server, data, stack)) {
			source.sendSuccess(new TranslationTextComponent("commands.tardis-mail.spawn.success",
					stack.getCount(), stack.getDisplayName(), id), true);
		}
		return SINGLE_SUCCESS;
	}
}
