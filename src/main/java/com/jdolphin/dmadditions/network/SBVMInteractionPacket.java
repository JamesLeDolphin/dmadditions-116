package com.jdolphin.dmadditions.network;

import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.item.VortexManipulatorItem;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SBVMInteractionPacket {
	private BlockPos pos;
	private ResourceLocation dim;

	public SBVMInteractionPacket(BlockPos pos, ResourceLocation dim) {
		this.pos = pos;
		this.dim = dim;
	}

	public SBVMInteractionPacket(PacketBuffer buf) {
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
			ItemStack stack = player.getMainHandItem();
			MinecraftServer server = player.getServer();
			assert server != null;

			RegistryKey<World> key = RegistryKey.create(Registry.DIMENSION_REGISTRY, this.dim);
			ServerWorld serverlevel = server.getLevel(key);

			if (serverlevel != null) {
				if (!player.isCreative()) VortexManipulatorItem.lowerFuel(stack.getOrCreateTag(), 1);
				Helper.playSound(player.getLevel(), player.blockPosition(), DMSoundEvents.BLOCK_TELEPORT_PAD.get(), SoundCategory.PLAYERS, 2);
				player.teleportTo(serverlevel, pos.getX(), pos.getY(), pos.getZ(), player.yRot, player.xRot);
				Helper.playSound(serverlevel, pos, DMSoundEvents.BLOCK_TELEPORT_PAD.get(), SoundCategory.PLAYERS, 2);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
