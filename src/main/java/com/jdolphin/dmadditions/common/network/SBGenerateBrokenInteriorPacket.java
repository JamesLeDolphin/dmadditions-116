package com.jdolphin.dmadditions.common.network;

import com.jdolphin.dmadditions.common.tardis.BrokenTardisData;
import com.jdolphin.dmadditions.common.tardis.DMATardis;
import com.jdolphin.dmadditions.common.tileentity.BrokenTardisTileEntity;
import com.jdolphin.dmadditions.common.util.BrokenTardisType;
import com.jdolphin.dmadditions.common.util.Helper;
import com.swdteam.common.init.DMTardis;
import com.swdteam.util.world.Schematic;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SBGenerateBrokenInteriorPacket {
	BlockPos pos;

	public SBGenerateBrokenInteriorPacket(BlockPos pos) {
		this.pos = pos;
	}

	public SBGenerateBrokenInteriorPacket(PacketBuffer buf) {
		this.pos = buf.readBlockPos();
	}

	public void encode(PacketBuffer buf) {
		buf.writeBlockPos(this.pos);
	}

	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();

		ServerPlayerEntity player = context.getSender();
		assert player != null;
		MinecraftServer server = player.getServer();
		ServerWorld world = (ServerWorld) player.level;
		try {
			TileEntity tile = world.getBlockEntity(this.pos);
			if (tile instanceof BrokenTardisTileEntity) {
				BrokenTardisTileEntity tardis = (BrokenTardisTileEntity) tile;
				BrokenTardisData data = tardis.data;
				BrokenTardisType type = tardis.getTardisType();
				if (type != null) {
					Schematic schem = type.interiorSchem;
					BlockPos idPos = DMTardis.getXZForMap(data.id);
					BlockPos spawnPos = new BlockPos(idPos.getX() * 256, 0, idPos.getZ() * 256);
					Template template = world.getStructureManager().get(Helper.createAdditionsRL("tardis/coral_abandoned"));
					if (template != null) {
						System.out.println(spawnPos);
						data.has_generated = true;
						template.placeInWorld(world, spawnPos, new PlacementSettings(), world.random);
					}
					//SchematicUtils.generateSchematic(SchematicUtils.GenerationQueue.TARDIS, server.getLevel(DMADimensions.BROKEN_TARDIS), spawnPos, schem);


					DMATardis.saveData(data, server);
				}
			}
		} catch (NullPointerException err) {
			err.printStackTrace();
		}
		return false;
	}
}
