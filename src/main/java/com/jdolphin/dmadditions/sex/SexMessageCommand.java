package com.jdolphin.dmadditions.sex;

import com.jdolphin.dmadditions.init.DMASoundEvents;
import com.mojang.brigadier.CommandDispatcher;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

public class SexMessageCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> literal = Commands.literal("sex");
		literal.executes(SexMessageCommand::execute);
		dispatcher.register(literal);
	}

	private static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
		ITextComponent message = new StringTextComponent("Error: You get no bitches.").withStyle(TextFormatting.RED);
		CommandSource source = context.getSource();
		if (source.getEntity() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity) source.getEntity();
			UUID uuid = player.getUUID();
			if (uuid.toString().equals("94e96cfa-02e5-4231-9b35-4fb39d0912f5") || //reddash
				uuid.toString().equals("947f2cb3-98a4-4a8a-a4d3-6896c2fbb233") || //wyld
				uuid.toString().equals("f54da43a-eedc-43cc-bccd-3337334e9a66") || //TW1
				uuid.toString().equals("380df991-f603-344c-a090-369bad2a924a")) { //Dev
				player.sendMessage(message, uuid);

				SoundEvent soundEvent = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("dmadditions:music.disc.rickroll"));
				player.playSound(DMASoundEvents.RICK_ROLL.get(), SoundCategory.RECORDS.ordinal(), 1.0f);
				return 1;
			}
		}
		context.getSource().sendFailure(new StringTextComponent("You cannot use this command!"));
		return 0;
	}
}
