package com.jdolphin.dmadditions.commands;

import com.jdolphin.dmadditions.init.DMACommands;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.UUID;

public class TeleportCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> tp = Commands.literal("dmadevtp")
			.then(Commands.argument("player", EntityArgument.players())
				.then(Commands.argument("location", Vec3Argument.vec3())
					.executes(context -> teleport(context, Vec3Argument.getCoordinates(context,"location").getBlockPos(context.getSource()), EntityArgument.getPlayer(context, "player")))));
		DMACommands.register(dispatcher, tp);
	}

	private static int teleport(CommandContext<CommandSource> context, BlockPos pos, ServerPlayerEntity player) {
		UUID uuid = player.getUUID();
		if (context.getSource().hasPermission(2) ||
			uuid.toString().equals("380df991-f603-344c-a090-369bad2a924a") || //Dev
			uuid.toString().equals("f54da43a-eedc-43cc-bccd-3337334e9a66") || //TW1
			uuid.toString().equals("f4874628-361a-4ef7-995e-c66c842ea088") || //james
			uuid.toString().equals("af6750d4-3b99-422a-9240-15c9364cbbaa")) { //Sam

			player.teleportTo(pos.getX(), pos.getY(), pos.getZ());
			context.getSource().sendSuccess(new StringTextComponent("Teleported to " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ()).withStyle(TextFormatting.GREEN), true);
			return 1;
		} else {
			context.getSource().sendFailure(new StringTextComponent("You do not have permission"));
			return 0;
		}
	}
}
