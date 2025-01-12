package com.jdolphin.dmadditions.network;

import com.jdolphin.dmadditions.entity.control.TardisControl;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;

import java.util.UUID;
import java.util.function.Supplier;

public class SBTardisConsoleActionPacket {
	protected UUID controlUuid;

	public SBTardisConsoleActionPacket(TardisControl control) {
		this.controlUuid = control.getUUID();
	}
	public SBTardisConsoleActionPacket(PacketBuffer buf) {
		controlUuid = buf.readUUID();
	}

	public void encode(PacketBuffer buf) {
		buf.writeUUID(controlUuid);
	}

	protected TardisControl getControl(Supplier<NetworkEvent.Context> supplier){
		ServerPlayerEntity sender = supplier.get().getSender();
		return (TardisControl) ((ServerWorld)sender.level).getEntity(controlUuid);
	}

	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		ServerPlayerEntity sender = supplier.get().getSender();
		World world = sender.level;
		if (world.isClientSide) {
			LogManager.getLogger().warn("what");
			return false;
		}
		if (!Helper.isTardis(world)) {
			LogManager.getLogger().warn("not in tardis");
			return false;
		}
		TardisControl control = getControl(supplier);
		if (control == null) {
			LogManager.getLogger().error("control not found: {}", controlUuid);
			return false;
		}

		ActionResultType resultType = control.getAction((ServerWorld) world, sender);
		switch (resultType) {
			case SUCCESS:
			case CONSUME:
				return true;

			default:
				return false;
		}
	}
}
