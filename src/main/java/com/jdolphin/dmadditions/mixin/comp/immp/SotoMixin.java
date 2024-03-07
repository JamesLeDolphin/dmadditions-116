package com.jdolphin.dmadditions.mixin.comp.immp;

import com.jdolphin.dmadditions.block.tardis.BetterTardisDoorHitbox;
import com.qouteall.immersive_portals.McHelper;
import com.qouteall.immersive_portals.portal.Portal;
import com.qouteall.immersive_portals.portal.PortalManipulation;
import com.swdteam.common.init.DMBlockEntities;
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

	@Unique
	public RegistryKey<World> dma$TARDIS = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("dalekmod:tardis"));

	@Unique
	private static AxisAlignedBB dma$defaultAABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);


	/**
	 * @author JamesLeDolphin
	 * @reason Soto
	 */
	@Overwrite
	public void tick() {
		if (!this.level.isClientSide && this.level.dimension().equals(DMDimensions.TARDIS)) {
				TardisData data = DMTardis.getTardisFromInteriorPos(this.getBlockPos());
				Direction tDir = this.getBlockState().getValue(BetterTardisDoorHitbox.FACING);
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
							BlockState state = this.getBlockState();

						AxisAlignedBB bounds = dma$defaultAABB.move(this.getBlockPos()).inflate(0.14200001192092896,
							0.0,0.14200001192092896);

							 bounds = bounds
								.move(Math.sin(Math.toRadians(state.getValue(BetterTardisDoorHitbox.FACING).toYRot())) * 0.1,
								0.0, -Math.cos(Math.toRadians(state.getValue(BetterTardisDoorHitbox.FACING).toYRot())) * 0.1);

							dma$portal = PortalManipulation.createOrthodoxPortal(
								Portal.entityType,
								McHelper.getServerWorld(dma$TARDIS),
								McHelper.getServerWorld(data.getCurrentLocation().dimensionWorldKey()),
								tDir,
								bounds,
								new Vector3d(dx + 0.5, dy, dz + 0.5));

							dma$portal.renderingMergable = false;
							if (tDir == Direction.NORTH) {
								dma$portal.setRotationTransformation(new Quaternion(0, 1, 0, 0));
							} else if (tDir == Direction.WEST) {
								dma$portal.setRotationTransformation(new Quaternion(0, 0.7071f, 0, 0.7071f));
							} else if (tDir == Direction.EAST) {
								dma$portal.setRotationTransformation(new Quaternion(0, -0.7071f, 0, 0.7071f));
							}
							McHelper.spawnServerEntity(dma$portal);
						}
					if (((tile.state.equals(TardisState.DEMAT) || tile.state.equals(TardisState.REMAT)) || (!tile.doorOpenRight && !tile.doorOpenLeft))
						&& (dma$portal != null && dma$portal.isAlive())) {

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
