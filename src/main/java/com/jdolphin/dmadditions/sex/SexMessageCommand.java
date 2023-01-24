package com.jdolphin.dmadditions.sex;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.init.DMASoundEvents;
import com.jdolphin.dmadditions.init.DMASoundTypes;
import com.mojang.brigadier.CommandDispatcher;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

import net.minecraft.entity.player.ServerPlayerEntity;

import net.minecraft.network.play.server.SPlaySoundPacket;
import net.minecraft.util.SoundCategory;

import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;


import java.util.Objects;
import java.util.UUID;
import java.util.Vector;

import static com.jdolphin.dmadditions.init.DMASoundEvents.RICK_ROLL;

public class SexMessageCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> literal = Commands.literal("sex");
		literal.executes(SexMessageCommand::execute);
		dispatcher.register(literal);
	}

	private static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
		ITextComponent message = new StringTextComponent("Error 404: Bitches not found. Falling back to Rick Astley. He will never give you up")
			.withStyle(TextFormatting.RED);
		CommandSource source = context.getSource();
		if (source.getEntity() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity) source.getEntity();
			UUID uuid = player.getUUID();
			if (uuid.toString().equals("94e96cfa-02e5-4231-9b35-4fb39d0912f5") || //reddash
				uuid.toString().equals("947f2cb3-98a4-4a8a-a4d3-6896c2fbb233") || //wyld
				uuid.toString().equals("f54da43a-eedc-43cc-bccd-3337334e9a66") || //TW1
				uuid.toString().equals("380df991-f603-344c-a090-369bad2a924a")) { //Dev
				player.sendMessage(message, uuid);
				player.playSound(RICK_ROLL.get(), 1.0f, 1.0f);

				return 1;

			}
		}
		context.getSource().sendFailure(new StringTextComponent("Error: You get no bitches."));
		return 0;
	}
}
