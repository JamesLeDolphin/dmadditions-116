package com.jdolphin.dmadditions.network;

import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.item.SonicShadesItem;
import com.swdteam.common.init.DMSonicRegistry;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.sonic.ISonicEntityInteraction;
import com.swdteam.common.sonic.SonicCategory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.extensions.IForgeEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SBSonicInteractPacket {

	public SBSonicInteractPacket() {
	}

	public SBSonicInteractPacket(PacketBuffer buf) {
	}

	public void encode(PacketBuffer buf) {
	}

	private void sonicUse(CompoundNBT tag) {
		tag.putInt("xp", tag.getInt("xp") + 1);
		tag.putInt("energy", tag.getInt("energy") - 1);
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

				world.playSound(null, player.blockPosition(), DMSoundEvents.ENTITY_SONIC_ELEVENTH_EXTEND.get(), SoundCategory.PLAYERS, 0.5F, 1.0F);

				RayTraceResult rayTraceResult = SonicShadesItem.getPlayerRayTraceResult(world, player, RayTraceContext.FluidMode.ANY);
				CompoundNBT tag = stack.getOrCreateTag();
				if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK) {
					BlockRayTraceResult result = (BlockRayTraceResult) rayTraceResult;
					BlockState state = world.getBlockState(result.getBlockPos());

					if (DMSonicRegistry.SONIC_LOOKUP.containsKey(state.getBlock())) {
						DMSonicRegistry.ISonicInteraction sonic = DMSonicRegistry.SONIC_LOOKUP.get(state.getBlock());
						if (sonic != null && tag.getInt("energy") > 0) {
							if (!SonicCategory.canExecute(stack, sonic.getCategory()) && !player.isCreative()) {
								player.displayClientMessage((new StringTextComponent("This ability is still locked")).withStyle(TextFormatting.RED), false);
							} else {
								sonic.interact(world, player, stack, result.getBlockPos());
								sonicUse(tag);
								SonicCategory.checkUnlock(player, stack);
							}
						}
					}
				}
				if (rayTraceResult.getType() == RayTraceResult.Type.MISS) {
					EntityRayTraceResult result = (EntityRayTraceResult) rayTraceResult;
					Entity entity = result.getEntity();
					if (entity != null) {
						System.out.println(entity);
						if (DMSonicRegistry.SONIC_LOOKUP.containsKey(entity)) {
							DMSonicRegistry.ISonicInteraction sonic = DMSonicRegistry.SONIC_LOOKUP.get(entity.getType());
							if (sonic != null && tag.getInt("energy") > 0) {
								if (!SonicCategory.canExecute(stack, sonic.getCategory()) && !player.isCreative()) {
									player.displayClientMessage((new StringTextComponent("This ability is still locked")).withStyle(TextFormatting.RED), false);
								} else {
									sonic.interact(world, player, stack, entity);
									sonicUse(tag);
									SonicCategory.checkUnlock(player, stack);
								}
							}
						}
					}
				}

				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return false;
	}
}
