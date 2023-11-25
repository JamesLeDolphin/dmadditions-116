package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.config.DMACommonConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.CommandSource;

public class DMACommands{
	public static void register(CommandDispatcher<CommandSource> dispatcher, LiteralArgumentBuilder<CommandSource> command){
		if (!DMACommonConfig.disable_dma_commands.get().contains(command.getLiteral()))
			dispatcher.register(command);
	}
}
