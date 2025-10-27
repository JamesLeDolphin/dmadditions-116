package com.jdolphin.dmadditions.common.tileentity;

import com.jdolphin.dmadditions.common.block.tardis.BrokenTardisBlock;
import com.jdolphin.dmadditions.common.init.DMABlockEntities;
import com.jdolphin.dmadditions.common.init.DMABrokenTardisTypes;
import com.jdolphin.dmadditions.common.init.DMADimensions;
import com.jdolphin.dmadditions.common.tardis.BrokenTardisData;
import com.jdolphin.dmadditions.common.tardis.DMATardis;
import com.jdolphin.dmadditions.common.util.Helper;
import com.jdolphin.dmadditions.common.util.tardis.BrokenTardisInterior;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.Location;
import com.swdteam.common.teleport.TeleportRequest;
import com.swdteam.common.tileentity.ExtraRotationTileEntityBase;
import com.swdteam.util.TeleportUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class BrokenTardisTileEntity extends ExtraRotationTileEntityBase implements ITickableTileEntity {
	public BrokenTardisData data = null;
	private BrokenTardisInterior interiorType = null;
	private ResourceLocation exteriorType = null;
	private boolean loaded = false;
	private boolean open = false;
	private int id = -1;

	public BrokenTardisTileEntity() {
		super(DMABlockEntities.BROKEN_TARDIS_TILE.get());
	}

	public BrokenTardisInterior getTardisInteriorType() {
		return interiorType;
	}

	public ResourceLocation getExteriorType() {
		return exteriorType;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
		sendUpdates();
	}

	public void setup() {
		if (this.data == null) {
			ServerWorld world = (ServerWorld) this.level;
			MinecraftServer server = world.getServer();
			this.data = DMATardis.getOrCreate(id, server);
			this.id = data.id;
			data.open = this.open;
			data.worldPos = this.worldPosition;
			DMATardis.saveData(data, server);
			this.sendUpdates();
		}
		if (exteriorType == null) {
			this.exteriorType = DMABrokenTardisTypes.getRandomExterior();
			this.sendUpdates();
		}
		if (interiorType == null) {
			this.interiorType = DMABrokenTardisTypes.getRandomInterior();
			this.sendUpdates();
		}
	}

	@Override
	public CompoundNBT save(CompoundNBT tag) {
		tag.putInt("BTId", id);
		tag.putBoolean("Open", open);
		ResourceLocation interiorLoc = Helper.getKeyByValue(DMABrokenTardisTypes.INTERIORS, interiorType);
		if (interiorLoc != null) {
			tag.putString("InteriorType", interiorLoc.toString());
		}
		ResourceLocation exteriorLoc = Helper.getKeyByValue(DMABrokenTardisTypes.EXTERIORS, exteriorType);
		if (exteriorLoc != null) {
			tag.putString("ExteriorType", exteriorLoc.toString());
		}


		return super.save(tag);
	}

	@Override
	public void load(BlockState state, CompoundNBT tag) {
		id = tag.getInt("BTId");
		if (tag.contains("InteriorType")) {
			String s = tag.getString("InteriorType");
			this.interiorType = DMABrokenTardisTypes.INTERIORS.get(new ResourceLocation(s));
		}
		if (tag.contains("ExteriorType")) {
			String s = tag.getString("ExteriorType");
			this.exteriorType = DMABrokenTardisTypes.EXTERIORS.get(new ResourceLocation(s));
		}
		this.open = tag.getBoolean("Open");
		this.loaded = true;
		super.load(state, tag);
	}

	@Override
	public void tick() {
		if (!this.level.isClientSide) {
			if (loaded) {
				setup();
			}

			if (data != null) {
				if (data.open && data.has_generated) {
					AxisAlignedBB aabb = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
					AxisAlignedBB bounds = aabb.move(this.getBlockPos()).inflate(-0.3F, 0.0F, -0.3F);
					BlockState state = level.getBlockState(worldPosition);
					int rotation = (int) state.getValue(BrokenTardisBlock.FACING).toYRot();
					bounds = bounds.move(Math.sin(Math.toRadians(rotation)) * (double) 0.5F, 0.0F, -Math.cos(Math.toRadians(rotation)) * (double) 0.5F);
					List<Entity> entities = this.level.getEntitiesOfClass(Entity.class, bounds);

					entities.removeIf(entity -> entity instanceof PlayerEntity && DMFlightMode.isInFlight((PlayerEntity) entity));
					entities.removeIf(Entity::isPassenger);
					if (!entities.isEmpty()) {
						Entity e = entities.get(0);
						BlockPos pos = DMTardis.getXZForMap(data.id);
						BlockPos spawnPos = new BlockPos(pos.getX() * 256, 0, pos.getZ() * 256);
						BlockPos vec = spawnPos.offset(this.interiorType.spawnPos);
						if (!TeleportUtil.TELEPORT_REQUESTS.containsKey(e)) {
							Location loc = new Location(new Vector3d(vec.getX(), vec.getY(), vec.getZ()), DMADimensions.BROKEN_TARDIS);
							loc.setFacing(e.getYHeadRot() - this.rotation);
							TeleportUtil.TELEPORT_REQUESTS.put(e, new TeleportRequest(loc));
						}
					}
				}
			}
		}
	}
}
