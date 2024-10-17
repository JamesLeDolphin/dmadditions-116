package com.jdolphin.dmadditions.network;

import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.item.SonicShadesItem;
import com.swdteam.common.init.DMSonicRegistry;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.sonic.ISonicEntityInteraction;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SBSonicInteractPacket {

	public SBSonicInteractPacket() {
	}

	public SBSonicInteractPacket(PacketBuffer buf) {
	}

	public void encode(PacketBuffer buf) {
	}

	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		ServerPlayerEntity player = context.getSender();

		assert  player != null;

		try {
			ServerWorld world = player.getLevel();
			ItemStack stack = player.getItemBySlot(EquipmentSlotType.HEAD);
			if (DMAItems.SONIC_SHADES != null && stack.getItem().equals(DMAItems.SONIC_SHADES.get())) {
				SonicShadesItem.checkIsSetup(stack);
				world.playSound(null, player.blockPosition(), DMSoundEvents.ENTITY_SONIC_ELEVENTH_EXTEND.get(), SoundCategory.PLAYERS, 0.5F, 1.0F);

				RayTraceResult rayTraceResult = SonicShadesItem.getPlayerRayTraceResult(world, player, RayTraceContext.FluidMode.ANY);
				System.out.println(rayTraceResult.getType());
				if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK) {
					BlockRayTraceResult result = (BlockRayTraceResult) rayTraceResult;
					BlockState state = world.getBlockState(result.getBlockPos());
					if (DMSonicRegistry.SONIC_LOOKUP.containsKey(state.getBlock())) {
						DMSonicRegistry.ISonicInteraction sonic = DMSonicRegistry.SONIC_LOOKUP.get(state.getBlock());
						sonic.interact(world, player, stack, result.getBlockPos());
					}
				}
				if (rayTraceResult.getType() == RayTraceResult.Type.ENTITY) {
					EntityRayTraceResult result = (EntityRayTraceResult) rayTraceResult;
					Entity entity = result.getEntity();
					System.out.println(entity);
					if (DMSonicRegistry.SONIC_LOOKUP.containsKey(entity)) {
						DMSonicRegistry.ISonicInteraction sonic = DMSonicRegistry.SONIC_LOOKUP.get(entity.getType());
						sonic.interact(world, player, stack, entity);
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
