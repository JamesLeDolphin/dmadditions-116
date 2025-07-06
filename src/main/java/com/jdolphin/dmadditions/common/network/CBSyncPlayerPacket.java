package com.jdolphin.dmadditions.common.network;

import com.jdolphin.dmadditions.common.init.DMACapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CBSyncPlayerPacket {
	public int id;
	public CompoundNBT data;

	public CBSyncPlayerPacket(int id, CompoundNBT data) {
		this.id = id;
		this.data = data;
	}

	public static void encode(CBSyncPlayerPacket mes, PacketBuffer buf) {
		buf.writeInt(mes.id);
		buf.writeNbt(mes.data);
	}

	public static CBSyncPlayerPacket decode(PacketBuffer buf) {
		return new CBSyncPlayerPacket(buf.readInt(), buf.readNbt());
	}

	public static void handle(CBSyncPlayerPacket mes, Supplier<NetworkEvent.Context> cont) {
		cont.get().enqueueWork(() -> {
			@SuppressWarnings("resource")
			Entity result = Minecraft.getInstance().level.getEntity(mes.id);
			if(result != null)
				result.getCapability(DMACapabilities.REGEN_CAPABILITY).ifPresent(cap -> cap.deserializeNBT(mes.data));
		});
		cont.get().setPacketHandled(true);
	}
}
