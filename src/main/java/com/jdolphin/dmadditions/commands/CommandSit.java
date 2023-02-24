package com.jdolphin.dmadditions.commands;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;

import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import net.minecraft.inventory.EquipmentSlotType;

public class CommandSit {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("sit").executes(context -> {
			Entity entity = context.getSource().getEntity();
			if (entity instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) entity;
				MinecraftServer server = context.getSource().getServer();
				World world = player.getEntity().getCommandSenderWorld();
				Vector3d pos = player.position();
				ArmorStandEntity armorStand = new ArmorStandEntity(world, pos.x, pos.y - 1.7, pos.z);
				armorStand.setInvisible(true);
				armorStand.setNoGravity(true);
				armorStand.setInvulnerable(true);
				armorStand.getPersistentData().putBoolean("Marker", true);
				armorStand.getPersistentData().putBoolean("Small", true);
				armorStand.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(Items.AIR));
				armorStand.setItemSlot(EquipmentSlotType.CHEST, new ItemStack(Items.AIR));
				armorStand.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(Items.AIR));
				armorStand.setItemSlot(EquipmentSlotType.FEET, new ItemStack(Items.AIR));
				world.addFreshEntity(armorStand);
				player.startRiding(armorStand, true);
				player.sendMessage(new StringTextComponent("You have sat down."), player.getUUID());
				return 1;
			} else {
				context.getSource().sendFailure(new StringTextComponent("Only players can use this command!"));
				return 0;
			}
		}));
	}
}
