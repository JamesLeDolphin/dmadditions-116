package com.jdolphin.dmadditions.common.network;

import com.jdolphin.dmadditions.common.util.GuiHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CBOpenGUIPacket {
	private BlockPos pos;
	private int guiID;

	public CBOpenGUIPacket(BlockPos pos, int guiID) {
		this.pos = pos;
		this.guiID = guiID;
	}

	public CBOpenGUIPacket(PacketBuffer buffer) {
		this.pos = buffer.readBlockPos();
		this.guiID = buffer.readInt();
	}

	public static void encode(CBOpenGUIPacket msg, PacketBuffer buf) {
		buf.writeBlockPos(msg.pos);
		buf.writeInt(msg.guiID);
	}

	public static void handle(CBOpenGUIPacket msg, Supplier<NetworkEvent.Context> ctx) {
		if (ctx.get().getNetworkManager().getDirection() == PacketDirection.CLIENTBOUND) {
			clientCode(msg, ctx);
		}

	}

	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void clientCode(CBOpenGUIPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			if (msg.guiID == -1) {
				Minecraft.getInstance().setScreen(null);
			} else {
				GuiHandler.openGui(msg.guiID, msg.pos, Minecraft.getInstance().player);
			}

		});
		ctx.get().setPacketHandled(true);
	}
}
