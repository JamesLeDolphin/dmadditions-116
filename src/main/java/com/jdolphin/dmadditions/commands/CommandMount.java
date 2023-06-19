package com.jdolphin.dmadditions.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class CommandMount {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> command = Commands.literal("mount");

		RequiredArgumentBuilder<CommandSource, String> entityArgument = Commands.argument("entity", StringArgumentType.word())
			.executes(context -> mountEntity(context.getSource().getPlayerOrException(), StringArgumentType.getString(context, "entity")));

		command.then(entityArgument);
		command.executes(context -> mountNearestEntity(context.getSource().getPlayerOrException()));

		dispatcher.register(command);
	}

	private static int mountEntity(PlayerEntity player, String entityName) {
		double reachDistance = 5.0; // Adjust the reach distance if needed

		BlockPos playerPos = player.blockPosition();
		AxisAlignedBB boundingBox = new AxisAlignedBB(playerPos).inflate(reachDistance);
		List<Entity> nearbyEntities = player.level.getEntities(player, boundingBox, entity -> entity instanceof LivingEntity && entity.getType().getRegistryName().toString().equalsIgnoreCase(entityName));

		if (!nearbyEntities.isEmpty()) {
			Entity entity = nearbyEntities.get(0);
			boolean success = player.startRiding(entity, true);
			if (success) {
				player.sendMessage(new StringTextComponent("You have mounted " + entity.getDisplayName().getString()).withStyle(TextFormatting.GREEN), player.getUUID());
			} else {
				player.sendMessage(new StringTextComponent("Failed to mount " + entity.getDisplayName().getString()).withStyle(TextFormatting.RED), player.getUUID());
			}
		} else {
			player.sendMessage(new StringTextComponent("No matching entity found to mount.").withStyle(TextFormatting.RED), player.getUUID());
		}

		return 1;
	}

	private static int mountNearestEntity(PlayerEntity player) {
		double reachDistance = 5.0; // Adjust the reach distance if needed

		BlockPos playerPos = player.blockPosition();
		AxisAlignedBB boundingBox = new AxisAlignedBB(playerPos).inflate(reachDistance);
		List<Entity> nearbyEntities = player.level.getEntities(player, boundingBox, entity -> entity instanceof LivingEntity);

		Entity nearestEntity = null;
		double nearestEntityDistanceSq = Double.MAX_VALUE;

		for (Entity entity : nearbyEntities) {
			double distanceSq = player.distanceToSqr(entity);
			if (distanceSq < nearestEntityDistanceSq) {
				nearestEntity = entity;
				nearestEntityDistanceSq = distanceSq;
			}
		}

		if (nearestEntity != null) {
			boolean success = player.startRiding(nearestEntity, true);
			if (success) {
				player.sendMessage(new StringTextComponent("You have mounted the nearest entity.").withStyle(TextFormatting.GREEN), player.getUUID());
			} else {
				player.sendMessage(new StringTextComponent("Failed to mount the nearest entity.").withStyle(TextFormatting.RED), player.getUUID());
			}
		} else {
			player.sendMessage(new StringTextComponent("No nearby entities found to mount.").withStyle(TextFormatting.RED), player.getUUID());
		}

		return 1;
	}
}
