package com.jdolphin.dmadditions.common.tardis;

import com.jdolphin.dmadditions.common.util.DMAData;
import com.jdolphin.dmadditions.common.util.Helper;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.io.Serializable;

public class BrokenTardisData implements Serializable {
	private static final long serialVersionUID = 1654931627846942582L;
	public boolean has_generated = false;
	public int id;
	public boolean available = false;
	public boolean open = false;
	public BlockPos worldPos = BlockPos.ZERO;

	public BrokenTardisData(int id) {
		this.id = id;
	}

	public BrokenTardisData(MinecraftServer server) {
		DMAData data = Helper.getDMAData(server);
		this.id = data.brokenTardisData.size();
	}

	public void toNetwork(PacketBuffer buffer) {
		buffer.writeBoolean(this.open);
		buffer.writeInt(id);
		buffer.writeBlockPos(this.worldPos);
	}

	public static BrokenTardisData fromNetwork(PacketBuffer buffer) {
		int id = buffer.readInt();
		boolean open = buffer.readBoolean();
		BlockPos pos = buffer.readBlockPos();
		BrokenTardisData data = new BrokenTardisData(id);
		data.open = open;
		data.worldPos = pos;
		return data;
	}
}
