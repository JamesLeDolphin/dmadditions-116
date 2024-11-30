package com.jdolphin.dmadditions.network;

import com.jdolphin.dmadditions.init.DMAItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;

import java.util.function.Supplier;

public class SBSonicBlasterInteractPacket {
	BlockPos pos;

	public SBSonicBlasterInteractPacket(BlockPos pos) {
		this.pos = pos;
	}

	public SBSonicBlasterInteractPacket(PacketBuffer buf) {
		this.pos = buf.readBlockPos();
	}

	public void encode(PacketBuffer buf) {
		buf.writeBlockPos(this.pos);
	}

	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();

		ServerPlayerEntity player = context.getSender();
		BlockPos bPos = this.pos;
		assert player != null;
		ServerWorld world = player.getLevel();
		MinecraftServer server = player.getServer();
		assert server != null;
		try {
			int i = server.getTickCount();
			BlockState state = world.getBlockState(bPos);
			Block block = state.getBlock();

			// Check if the block is bedrock before attempting to destroy it
			if (block != Blocks.BEDROCK) {
				world.destroyBlock(bPos, false);
				if (!block.isAir(state, world, bPos)) {
					server.addTickable(() -> {
						if (server.getTickCount() == i + 20 * 6) {
							if (world.setBlock(bPos, state, Constants.BlockFlags.DEFAULT)) {
								LogManager.getLogger().debug("Set block back {} {}", state, bPos);
							}
						}
					});
				}
			} else {
				LogManager.getLogger().debug("Attempted to break bedrock at {}. Action ignored.", bPos);
			}

			player.getCooldowns().addCooldown(DMAItems.SONIC_BLASTER.get(), 20 * 2);
			return true;
		} catch (NullPointerException err) {
			err.printStackTrace();
			return false;
		}
	}
}