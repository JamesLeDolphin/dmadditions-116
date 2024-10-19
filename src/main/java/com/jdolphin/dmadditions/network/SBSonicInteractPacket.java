package com.jdolphin.dmadditions.network;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.LogManager;

import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.item.SonicShadesItem;
import com.swdteam.common.init.DMSonicRegistry;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.sonic.SonicCategory;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public class SBSonicInteractPacket {
	protected UUID uuid;
	protected BlockPos blockPos;

	public SBSonicInteractPacket(BlockPos pos, UUID entity) {
		this.blockPos = pos;
		this.uuid = entity;
	}

	public SBSonicInteractPacket(PacketBuffer buf) {
		boolean isEntity = buf.readBoolean();
		if (isEntity) {
			this.uuid = buf.readUUID();
		} else {
			blockPos = buf.readBlockPos();
		}
	}

	public void encode(PacketBuffer buf) {
		buf.writeBoolean(isEntity());
		if (isEntity()) {
			buf.writeUUID(uuid);
		} else {
			buf.writeBlockPos(blockPos);
		}
	}

	protected boolean isEntity() {
		return uuid != null;
	}

	private void sonicUse(ItemStack stack, PlayerEntity player) {
		CompoundNBT tag = stack.getOrCreateTag();
		tag.putInt("xp", tag.getInt("xp") + 1);
		tag.putInt("energy", tag.getInt("energy") - 1);
		player.getCooldowns().addCooldown(stack.getItem(), 20 * 2);
		player.level.playSound(null, player.blockPosition(), DMSoundEvents.ENTITY_SONIC_NINTH.get(), SoundCategory.PLAYERS, 0.5F, 1.0F);
		SonicCategory.checkUnlock(player, stack);
	}

	private void playFailSound(PlayerEntity player) {
		player.level.playSound(null, player.blockPosition(), DMSoundEvents.ENTITY_SONIC_ELEVENTH_EXTEND.get(), SoundCategory.PLAYERS, 0.5F, 0F);
	}

	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		ServerPlayerEntity player = context.getSender();

		assert player != null;

		try {
			ServerWorld world = player.getLevel();
			ItemStack stack = player.getItemBySlot(EquipmentSlotType.HEAD);
			if (DMAItems.SONIC_SHADES != null && stack.getItem().equals(DMAItems.SONIC_SHADES.get())) {
				SonicShadesItem.checkIsSetup(stack);

				CompoundNBT tag = stack.getOrCreateTag();
				if (!player.getCooldowns().isOnCooldown(stack.getItem())) {
					if (isEntity()) {
						Entity entity = world.getEntity(uuid);
						if (entity != null) {
							LogManager.getLogger().debug("entity: {}", entity);
							if (DMSonicRegistry.SONIC_LOOKUP.containsKey(entity.getType())) {
								DMSonicRegistry.ISonicInteraction sonic = DMSonicRegistry.SONIC_LOOKUP
									.get(entity.getType());
								if (sonic != null && tag.getInt("energy") > 0) {
									if (!SonicCategory.canExecute(stack, sonic.getCategory()) && !player.isCreative()) {
										playFailSound(player);
										player.displayClientMessage(new StringTextComponent("This ability is still locked").withStyle(TextFormatting.RED), false);
									} else {
										sonic.interact(world, player, stack, entity);
										sonicUse(stack, player);
										SonicCategory.checkUnlock(player, stack);
									}
								}
							} else playFailSound(player);
						}
					} else {
						BlockState state = world.getBlockState(blockPos);

						if (DMSonicRegistry.SONIC_LOOKUP.containsKey(state.getBlock())) {
							DMSonicRegistry.ISonicInteraction sonic = DMSonicRegistry.SONIC_LOOKUP.get(state.getBlock());
							if (sonic != null && tag.getInt("energy") > 0) {
								if (!SonicCategory.canExecute(stack, sonic.getCategory()) && !player.isCreative()) {
									playFailSound(player);
									player.displayClientMessage(new StringTextComponent("This ability is still locked")
										.withStyle(TextFormatting.RED), false);
								} else {
									sonic.interact(world, player, stack, blockPos);
									sonicUse(stack, player);
									SonicCategory.checkUnlock(player, stack);
								}
							}
						} else playFailSound(player);
					}

					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
