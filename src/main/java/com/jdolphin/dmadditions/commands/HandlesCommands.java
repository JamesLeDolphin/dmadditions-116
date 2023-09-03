package com.jdolphin.dmadditions.commands;

import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandlesCommands {
	protected static HashSet<HandlesCommand> commands = new HashSet<>();

	public static HandlesCommand get(String query){
		Optional<HandlesCommand> optionalCommand = commands.stream().filter(c -> c.matches(query)).findFirst();
		return optionalCommand.orElse(null);
	}


	public static HandlesCommand NEAREST_PLAYER = HandlesCommand.create("(.*)((near|close)(\\b|e?st|by) (player|people)s?|(player|people)s? (near|close)(\\b| to) me)\\??",
					   (player, matcher, query) -> {
		PlayerEntity closestPlayer = player.level.getNearestPlayer(EntityPredicate.DEFAULT, player);
		if (closestPlayer != null) {
			ITextComponent n = closestPlayer.getName();
			sendHandlesMessage(player, String.format("%s is the closest player to you", n.getString()));
		} else
			sendHandlesMessage(player, "I am unable to find anyone near you");
		return true;
	});

	public static HandlesCommand LOCATE_TARDIS = HandlesCommand.create("(locate|find|wher).*(tardis|ship)\\??", (player, matcher, query) -> {
		List<Integer> ids = DMTardis.getUserTardises(player.getUUID()).getTardises();
		if (!ids.isEmpty()) {
			for (int id : ids) {
				int tardis = DMTardis.getTardis(id).getGlobalID();
				TardisData data = DMTardis.getTardis(tardis);
				BlockPos loc = DMTardis.getTardis(id).getCurrentLocation().getBlockPosition();
				ResourceLocation dim = DMTardis.getTardis(id).getCurrentLocation().dimensionWorldKey().location();
				sendHandlesMessage(player, String.format("Your TARDIS (\"%s\") is at %d %d %d; Dimension: %s", id, loc.getX(), loc.getY(), loc.getZ(), dim));
			}
		} else {
			sendHandlesMessage(player, "Unable to find your TARDIS");
			return false;
		}

		return true;
	});

	public static HandlesCommand ECHO = HandlesCommand.create("(echo|say) (.+)", (player, matcher, query) -> {
		if(matcher.find()){
			String e = matcher.group(2);
			sendHandlesMessage(player, e);
		}
		return true;
	});

	public static HandlesCommand HELLO = HandlesCommand.create("(hell?o|hi)", (player, matcher, query) -> {
		sendHandlesMessage(player, String.format("How may I help you, %s?", player.getName().getString()));

		return true;
	} );


	public static void sendHandlesMessage(PlayerEntity player, String message){
		IFormattableTextComponent handlesText = new StringTextComponent("<Handles> ").withStyle(TextFormatting.RED);
		IFormattableTextComponent messageText = new StringTextComponent(message).withStyle(TextFormatting.RESET);

		player.displayClientMessage(new StringTextComponent("").append(handlesText).append(messageText), false);
	}

	public static class HandlesCommand {
		private Pattern pattern;
		private HandlesCommandFunction function;

		HandlesCommand(Pattern pattern, HandlesCommandFunction function) {
			this.pattern = pattern;
			this.function = function;
		}

		public static HandlesCommand create(Pattern pattern, HandlesCommandFunction function){
			HandlesCommand command = new HandlesCommand(pattern, function);
			commands.add(command);
			return command;
		}
		public static HandlesCommand create(String pattern, HandlesCommandFunction function){
			return create(Pattern.compile(pattern), function);
		}

		public Pattern getPattern(){
			return pattern;
		}

		public boolean matches(String query){
			Matcher matcher = this.pattern.matcher(query);
			return matcher.matches();
		}

		public boolean execute(PlayerEntity player, String query){
			return function.execute(player, pattern.matcher(query), query);
		}

	}

	@FunctionalInterface
	public interface HandlesCommandFunction {
		boolean execute(PlayerEntity player, Matcher matcher, String query);
	}

}
