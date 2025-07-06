package com.jdolphin.dmadditions.mixin.compatibility.portals;

import com.jdolphin.dmadditions.common.block.tardis.BetterTardisDoorHitbox;
import com.jdolphin.dmadditions.common.util.Helper;
import com.qouteall.immersive_portals.McHelper;
import com.qouteall.immersive_portals.portal.Portal;
import com.qouteall.immersive_portals.portal.PortalManipulation;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisState;
import com.swdteam.common.tileentity.DMTileEntityBase;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.common.tileentity.tardis.TardisDoorHitboxTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(TardisDoorHitboxTileEntity.class)
public class SotoMixin extends DMTileEntityBase implements ITickableTileEntity {

	public SotoMixin() {
		super(DMBlockEntities.TILE_TARDIS_DOOR_HITBOX.get());
	}

	@Unique
	public Portal dma$portal = null;

	public void destroyPortal() {
		dma$portal.reloadAndSyncToClient();
		dma$portal.kill();
		dma$portal.remove(false);
		dma$portal.onRemovedFromWorld();
		dma$portal = null;
	}

	@Unique
	public RegistryKey<World> dma$TARDIS = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("dalekmod:tardis"));

	@Unique
	private static AxisAlignedBB dma$defaultAABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);

	public int getHeight(World world, BlockPos pos) {
		for (int j = 0; j < 4; j++) {
			BlockPos relativePos = pos.relative(Direction.UP, j);
			BlockState state = world.getBlockState(relativePos);
			if (!state.is(DMBlocks.TARDIS_DOOR_HITBOX.get())) {
				for (int i = 0; i < 4; i++) {
					BlockState state1 = world.getBlockState(relativePos.relative(Direction.DOWN, i));
					if (!state1.is(DMBlocks.TARDIS_DOOR_HITBOX.get())) {
						System.out.println(j + i);
						return j + i;
					}
				}
			}
		}
		return 1;
	}

	/**
	 * @author JamesLeDolphin
	 * @reason Soto
	 */
	@Overwrite
	public void tick() {
		if (!this.level.isClientSide && this.level.dimension().equals(DMDimensions.TARDIS)) {
			TardisData data = DMTardis.getTardisFromInteriorPos(this.getBlockPos());
			BlockState state = this.getBlockState();
			Direction tDir = state.getValue(BetterTardisDoorHitbox.FACING);
			TileEntity te = McHelper.getServerWorld(data.getCurrentLocation().dimensionWorldKey()).getBlockEntity(data.getCurrentLocation().getBlockPosition());
			if (te instanceof TardisTileEntity) {
				TardisTileEntity tile = (TardisTileEntity) te;
				if ((tile.doorOpenRight || tile.doorOpenLeft) && dma$portal == null) {
					BlockPos tardisPosition = data.getCurrentLocation().getBlockPosition();
					Vector3d look = Vector3d.directionFromRotation(new Vector2f(45.0F, tile.rotation + 180.0F));
					float distance = 2.0F;
					double dx = (double) tardisPosition.getX() + look.x * (double) distance;
					double dy = tardisPosition.getY() > 0 ? (double) tardisPosition.getY() : 128.0;
					double dz = (double) tardisPosition.getZ() + look.z * (double) distance;

					AxisAlignedBB bounds = dma$defaultAABB.move(this.getBlockPos()).inflate(0.14200001192092896,
						getHeight(level, worldPosition), 0.14200001192092896);

					bounds = bounds
						.move(Math.sin(Math.toRadians(tDir.toYRot())) * 0.1,
							0.0, -Math.cos(Math.toRadians(tDir.toYRot())) * 0.1);
					dma$portal = PortalManipulation.createOrthodoxPortal(
						Portal.entityType,
						McHelper.getServerWorld(dma$TARDIS),
						McHelper.getServerWorld(data.getCurrentLocation().dimensionWorldKey()),
						tDir,
						bounds,
						new Vector3d(dx + 0.5, dy, dz + 0.5));
					if (tDir == Direction.NORTH) {
						dma$portal.setRotationTransformation(new Quaternion(0, 1, 0, 0));
					} else if (tDir == Direction.WEST) {
						dma$portal.setRotationTransformation(new Quaternion(0, 0.7071f, 0, 0.7071f));
					} else if (tDir == Direction.EAST) {
						dma$portal.setRotationTransformation(new Quaternion(0, -0.7071f, 0, 0.7071f));
					}
					McHelper.spawnServerEntity(dma$portal);
				}

				McHelper.getNearbyPortals(level, Helper.blockPosToVec3(worldPosition), 1).forEach(portal -> {
					if (portal != null && portal != dma$portal) {
						if (portal.getThinAreaBox().getSize() < dma$portal.getThinAreaBox().getSize()) {
							portal.remove(false);
							portal.kill();
							portal.onRemovedFromWorld();
						}
					}
				});

				if (((tile.state != TardisState.NEUTRAL || (tile.bobTime != 0) || (!tile.doorOpenRight))
					&& (dma$portal != null && dma$portal.isAlive()))) {
					dma$portal.reloadAndSyncToClient();
					dma$portal.kill();
					dma$portal.remove(false);
					level.getChunk(this.worldPosition.getX(), this.worldPosition.getZ()).removeEntity(dma$portal);
					dma$portal.onRemovedFromWorld();
					dma$portal = null;
				}
			}
		}
	}
}
