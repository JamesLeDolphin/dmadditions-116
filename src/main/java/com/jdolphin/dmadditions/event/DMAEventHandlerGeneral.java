package com.jdolphin.dmadditions.event;

import com.jdolphin.dmadditions.cap.IPlayerRegenCap;
import com.jdolphin.dmadditions.cap.PlayerRegenCapability;
import com.jdolphin.dmadditions.commands.HandlesCommands;
import com.jdolphin.dmadditions.entity.DalekMutantEntity;
import com.jdolphin.dmadditions.init.DMACapabilities;
import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.item.TwoDizItem;
import com.jdolphin.dmadditions.util.Helper;
import com.jdolphin.dmadditions.world.dimension.Gravity;
import com.swdteam.common.entity.dalek.DalekEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
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
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

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
	public static void playerTickEvent(TickEvent.PlayerTickEvent event) {
		PlayerEntity player = event.player;
		if (player instanceof ServerPlayerEntity) {
			ItemStack headStack = player.getItemBySlot(EquipmentSlotType.HEAD);

			if (DMAItems.SONIC_SHADES != null && headStack.getItem().equals(DMAItems.SONIC_SHADES.get())) {
				World level = player.level;
				CompoundNBT tag = headStack.getOrCreateTag();
				int energy = tag.contains("energy") ? tag.getInt("energy") : 0;
				if (energy < 100) {
					if (level.random.nextFloat() < 0.4f) {
						tag.putInt("energy", energy + 1);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void entityDeathEvent(LivingDeathEvent event) {
		LivingEntity entity = event.getEntityLiving();
		if (entity instanceof DalekEntity) {
			World world = entity.level;
			Random random = entity.getRandom();
			if (random.nextInt(10) < 2) {
				DalekMutantEntity mutant = new DalekMutantEntity(world);
				world.addFreshEntity(mutant);
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
				width = tag.getFloat(TwoDizItem.ENTITY_WIDTH);
			}
			event.setNewSize(new EntitySize(size.width * width, size.height, size.fixed));
		}
	}

	@SubscribeEvent
	public static void attachPlayerCap(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof PlayerEntity) {
			event.addCapability(PLAYER_DATA_CAP, new IPlayerRegenCap.Provider(new PlayerRegenCapability((PlayerEntity) event.getObject())));
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
