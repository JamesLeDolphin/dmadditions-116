package com.jdolphin.dmadditions.event;

import com.jdolphin.dmadditions.cap.IPlayerRegenCap;
import com.jdolphin.dmadditions.cap.PlayerRegenCapability;
import com.jdolphin.dmadditions.commands.HandlesCommands;
import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.item.SonicBlasterItem;
import com.jdolphin.dmadditions.item.TwoDizItem;
import com.jdolphin.dmadditions.util.Helper;
import com.jdolphin.dmadditions.world.dimension.Gravity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class DMAEventHandlerGeneral {
	public static final ResourceLocation PLAYER_DATA_CAP = Helper.createAdditionsRL("player_data");

	@SubscribeEvent
	public static void onPlayerChat(ServerChatEvent event) {
		PlayerEntity player = event.getPlayer();
		String message = event.getMessage();
		ItemStack stack = player.getItemInHand(player.getUsedItemHand());
		if (stack.getItem().equals(DMAItems.HANDLES.get())) {
			if (message.toLowerCase().startsWith("handles")) {
				String query = message.substring(7).trim();

				if (query.isEmpty()) {
					HandlesCommands.HELLO.execute(stack, player, query);
					event.setCanceled(true);
					return;
				}

				HandlesCommands.HandlesCommand command = HandlesCommands.get(query);
				if (command == null) {
					HandlesCommands.sendHandlesMessage(player, stack, "Sorry, I don't seem to understand");
					event.setCanceled(true);
					return;
				}

				try {
					command.execute(stack, player, query);
				} catch (Exception e) {
					HandlesCommands.sendHandlesMessage(player, stack, "Seems like that didn't work");
					e.printStackTrace();
				}

				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void sizeEvent(EntityEvent.Size event) {
		Entity entity = event.getEntity();
		EntitySize size = event.getNewSize();
		if (entity instanceof LivingEntity) {
			CompoundNBT tag = entity.getPersistentData();
			float width = 1.0f;
			if (tag.contains(TwoDizItem.ENTITY_WIDTH)) {
				System.out.println("Contains width");
				width = tag.getFloat(TwoDizItem.ENTITY_WIDTH);
			}
			event.setNewSize(new EntitySize(size.width * width, size.height, size.fixed));
		}
	}

	@SubscribeEvent
	public static void attachPlayerCap(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof PlayerEntity) {
			event.addCapability(PLAYER_DATA_CAP, new IPlayerRegenCap.Provider(new PlayerRegenCapability((PlayerEntity)event.getObject())));
		}
	}

	@SubscribeEvent
	public static void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
		Entity entity = event.getEntity();
		if (!(entity instanceof LivingEntity)) return;

		if (entity.level.isClientSide) return;

		Gravity.changeGravity((LivingEntity) entity, event.getDimension(), entity.level.dimension());
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
		World world = event.getWorld();
		if (world.isClientSide) return;

		Entity entity = event.getEntity();
		if (!(entity instanceof LivingEntity)) return;
		if (entity.tickCount > 0) return;

		RegistryKey<World> dimension = event.getWorld().dimension();
		if (!Gravity.DIMENSION_GRAVITY.containsKey(dimension)) return;

		Gravity.changeGravity((LivingEntity) entity, dimension, null);
	}

}
