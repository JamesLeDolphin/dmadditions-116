package com.jdolphin.dmadditions.common.network;

import com.jdolphin.dmadditions.common.init.DMAItems;
import com.jdolphin.dmadditions.common.tardis.BrokenTardisData;
import com.jdolphin.dmadditions.common.tardis.DMATardis;
import com.jdolphin.dmadditions.common.tileentity.BrokenTardisTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class SBLockpickPacket {
	boolean success;
	BlockPos pos;

	public SBLockpickPacket(boolean success, BlockPos pos) {
		this.success = success;
		this.pos = pos;
	}

	public SBLockpickPacket(PacketBuffer buf) {
		this.success = buf.readBoolean();
		this.pos = buf.readBlockPos();
	}

	public void encode(PacketBuffer buf) {
		buf.writeBoolean(this.success);
		buf.writeBlockPos(this.pos);
	}

	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();

		ServerPlayerEntity player = context.getSender();
		assert player != null;
		MinecraftServer server = player.getServer();
		ServerWorld world = (ServerWorld) player.level;
		try {
			if (success) {
				TileEntity tile = world.getBlockEntity(this.pos);
				if (tile instanceof BrokenTardisTileEntity) {
					BrokenTardisTileEntity tardis = (BrokenTardisTileEntity) tile;
					BrokenTardisData data = tardis.data;
					data.open = true;
					tardis.setOpen(true);
					DMATardis.saveData(data, server);
					player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
				}
			} else {
				ItemStack stack = player.getMainHandItem();
				Item item = stack.getItem();
				if (item.equals(DMAItems.TARDIS_LOCKPICK.get())) {
					Random random = player.getRandom();
					stack.hurt(random.nextInt(10) == 1 ? 3 : 1, random, player);
					player.playSound(SoundEvents.ITEM_BREAK, 1.0f, 1.0f);
				}
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return false;
	}
}
