package com.jdolphin.dmadditions.network;

import com.jdolphin.dmadditions.util.DMALocation;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class SBSetDestinationPacket {
	private BlockPos pos;
	private ResourceLocation dim;

	public SBSetDestinationPacket(BlockPos pos, ResourceLocation dim) {
		this.pos = pos;
		this.dim = dim;
	}

	public SBSetDestinationPacket(PacketBuffer buf) {
		this.pos = buf.readBlockPos();
		this.dim = buf.readResourceLocation();
	}

	public void encode(PacketBuffer buf) {
		buf.writeBlockPos(pos);
		buf.writeResourceLocation(dim);
	}

	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();

		try {
			ServerPlayerEntity player = context.getSender();

			assert player != null;
			MinecraftServer server = player.getServer();
			assert server != null;

			RegistryKey<World> key = RegistryKey.create(Registry.DIMENSION_REGISTRY, this.dim);
			ServerWorld serverlevel = server.getLevel(key);

			if (serverlevel != null) {
				player.teleportTo(serverlevel, pos.getX(), pos.getY(), pos.getZ(), player.yRot, player.xRot);
				return true;
			} return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
