package com.jdolphin.dmadditions.network;

import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.tardis.data.TardisDataPool;
import com.swdteam.common.tardis.data.TardisFlightPool;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SBLocatePlayerPacket {
	String name;
	BlockPos pos;

	public SBLocatePlayerPacket(String playerName, BlockPos pos) {
		this.name = playerName;
		this.pos = pos;
	}

	public SBLocatePlayerPacket(PacketBuffer buf) {
		this.name = buf.readUtf();
		this.pos = buf.readBlockPos();
	}

	public void encode(PacketBuffer buf) {
		buf.writeUtf(this.name);
		buf.writeBlockPos(this.pos);
	}

	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();

		ServerPlayerEntity player = context.getSender();
		assert player != null;
		MinecraftServer server = player.getServer();

		try {
			ServerPlayerEntity pl1 = server.getPlayerList().getPlayerByName(this.name);
			if (pl1 != null) {
				if (!Helper.isTardis(pl1.getLevel()) || pl1.inventory.contains(DMAItems.BIO_DAMPNER.get().getDefaultInstance())) {
					TardisData data = DMTardis.getTardisFromInteriorPos(this.pos);
					TardisFlightData flight = TardisFlightPool.getFlightData(data);
					flight.setPos(this.pos);
					player.sendMessage(new StringTextComponent("Coordinates set!").withStyle(TextFormatting.GREEN), player.getUUID());
				} else player.sendMessage(new StringTextComponent("Error 403: Cannot access player!").withStyle(TextFormatting.RED), player.getUUID());
			} else player.sendMessage(new StringTextComponent("Error 404: Player not found!").withStyle(TextFormatting.RED), player.getUUID());
			return true;
		} catch (NullPointerException err) {
			err.printStackTrace();
			return false;
		}
	}
}
