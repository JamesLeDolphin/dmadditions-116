package com.jdolphin.dmadditions.commands;

import com.jdolphin.dmadditions.DMAdditions;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.main.DalekMod;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

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
					   (player, handles, matcher, query) -> {
		PlayerEntity closestPlayer = player.level.getNearestPlayer(EntityPredicate.DEFAULT, player);
		if (closestPlayer != null) {
			ITextComponent n = closestPlayer.getName();
			sendHandlesMessage(player, handles, String.format("%s is the closest player to you", n.getString()));
		} else
			sendHandlesMessage(player, handles, "I am unable to find anyone near you");
		return true;
	});

	public static HandlesCommand LOCATE_TARDIS = HandlesCommand.create("(locate|find|where|wher).*(tardis|tardus|ship)\\??", (player, handles, matcher, query) -> {
		if (player.getServer() != null) {
			List<Integer> ids = DMTardis.getUserTardises(player.getServer(), player.getUUID()).getTardises();
			if (!ids.isEmpty()) {
				for (int id : ids) {
					BlockPos loc = DMTardis.getTardis(id).getCurrentLocation().getBlockPosition();
					ResourceLocation dim = DMTardis.getTardis(id).getCurrentLocation().dimensionWorldKey().location();
					sendHandlesMessage(player, handles, String.format("Your TARDIS (%s) is at %d %d %d; Dimension: %s", id, loc.getX(), loc.getY(), loc.getZ(), dim));
				}
			} else {
				sendHandlesMessage(player, handles, "Unable to find your TARDIS");
				return false;
			}
		}
		return true;
	});

	public static HandlesCommand TARDIS_DISGUISE = HandlesCommand.create("(whats|wuh|what|wah).*(tardis|tardus|ship)\\??", (player, handles, matcher, query) -> {
		if (player.getServer() != null) {
		List<Integer> ids = DMTardis.getUserTardises(player.getServer(), player.getUUID()).getTardises();
		if (!ids.isEmpty()) {
			for (int id : ids) {
				int tardis = DMTardis.getTardis(id).getGlobalID();
				TardisData data = DMTardis.getTardis(tardis);
				Tardis ext = data.getTardisExterior();
				sendHandlesMessage(player, handles, String.format("Your TARDIS (%s) is disguised as %s", id, ext.getRegName()));
			}
		} else {
			sendHandlesMessage(player, handles, "Unable to find your TARDIS");
			return false;
		}
	}
		return true;
	});

	public static HandlesCommand ECHO = HandlesCommand.create("(echo|say) (.+)", (player, handles, matcher, query) -> {
		if(matcher.find()){
			String e = matcher.group(2);
			sendHandlesMessage(player, handles, e);
		}
		return true;
	});

	public static HandlesCommand HELLO = HandlesCommand.create("(hell?o|hi)", (player, handles, matcher, query) -> {
		sendHandlesMessage(player, handles, String.format("How may I help you, %s?", player.getName().getString()));
		return true;
	});

	public static HandlesCommand LIFE = HandlesCommand.create("(whats|wuh|what|wah|what is).*(meaning|point).*(life)\\??", (player, handles, matcher, query) -> {
		if (DalekMod.RANDOM.nextInt(250) == 0) sendHandlesMessage(player, handles, "DMA is the meaning of life");
		if (DalekMod.RANDOM.nextInt(4096) == 0) sendHandlesMessage(player, handles, "The doors of hell have opened");
		else sendHandlesMessage(player, handles, "42");

		return true;
	});

	public static HandlesCommand VERSION = HandlesCommand.create("(whats|wuh|what|wah).*(version|vers|ver|v|versioon)\\??", (player, handles, matcher, query) -> {
		sendHandlesMessage(player, handles, String.format("My current version is: %s", DalekMod.VERSION + "-" + DMAdditions.VERSION));
		return true;
	});

	public static HandlesCommand NAME = HandlesCommand.create("(whats|wuh|what|wah|say|who).*(name|i)\\??", (player, handles, matcher, query) -> {
		sendHandlesMessage(player, handles, String.format("Your name is %s", player.getName().getString()));
		return true;
	});



	public static void sendHandlesMessage(PlayerEntity player, ItemStack itemStack, String message){
		IFormattableTextComponent handlesText = new StringTextComponent("<").append(itemStack.getHoverName()).append("> ").withStyle(TextFormatting.AQUA);
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
			return create(Pattern.compile(pattern, Pattern.CASE_INSENSITIVE), function);
		}

		public Pattern getPattern(){
			return pattern;
		}

		public boolean matches(String query){
			Matcher matcher = this.pattern.matcher(query);
			return matcher.matches();
		}

		public boolean execute(ItemStack itemStack, PlayerEntity player, String query){
			return function.execute(player, itemStack, pattern.matcher(query), query);
		}

	}

	@FunctionalInterface
	public interface HandlesCommandFunction {
		boolean execute(PlayerEntity player, ItemStack itemStack, Matcher matcher, String query);
	}

}
