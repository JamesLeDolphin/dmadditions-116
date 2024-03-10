package com.jdolphin.dmadditions.mixin.comp.immp;

import com.jdolphin.dmadditions.util.Helper;
import com.qouteall.immersive_portals.McHelper;
import com.qouteall.immersive_portals.my_util.DQuaternion;
import com.qouteall.immersive_portals.portal.Portal;
import com.qouteall.immersive_portals.portal.PortalExtension;
import com.qouteall.immersive_portals.portal.PortalManipulation;
import com.swdteam.client.tardis.data.ExteriorModels;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.Location;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisState;
import com.swdteam.common.teleport.TeleportRequest;
import com.swdteam.common.tileentity.ExtraRotationTileEntityBase;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelRendererWrapper;
import com.swdteam.model.javajson.ModelWrapper;
import com.swdteam.util.SWDMathUtils;
import com.swdteam.util.TeleportUtil;
import com.swdteam.util.math.Position;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Mixin(TardisTileEntity.class)
public abstract class BotiMixin extends ExtraRotationTileEntityBase implements ITickableTileEntity {

	@Shadow(remap = false)
	public TardisData tardisData;

	@Shadow(remap = false)
	boolean demat;

	@Unique
	boolean dma$portalSpawned = false;

	@Shadow(remap = false)
	protected abstract void doorAnimation();

	@Unique
	public Portal dma$portal = null;

	@Unique
	private static AxisAlignedBB dma$defaultAABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 2.0, 1.0);

	@Unique
	public RegistryKey<World> dma$TARDIS = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("dalekmod:tardis"));

	public BotiMixin() {
		super(DMBlockEntities.TILE_TARDIS.get());
	}

	/**
	 * @author Originally made by BobDude, finished by JamesLeDolphin
	 * @reason BOTI
	 */
	@Overwrite
	public void tick() {
		TardisTileEntity tile = (TardisTileEntity) ((Object) this);
		CompoundNBT tag = this.getUpdateTag();

		this.doorAnimation();
		long tickTime = System.currentTimeMillis() - tile.lastTickTime;
		tile.lastTickTime = System.currentTimeMillis();
		if (tile.state == TardisState.DEMAT) {
			this.demat = true;
			if (tile.animStartTime == 0L) {
				tile.animStartTime = System.currentTimeMillis();
			}
			if (tickTime > 100L) {
				tile.animStartTime += tickTime;
			}
			tile.dematTime = (float) ((double) (System.currentTimeMillis() - tile.animStartTime) / 10000.0);
			if (tile.dematTime >= 1.0F) {
				tile.dematTime = 1.0F;
			}
			if (tile.dematTime == 1.0F) {
				this.getLevel().setBlockAndUpdate(tile.getBlockPos(), Blocks.AIR.defaultBlockState());
				tile.animStartTime = 0L;
			}
		} else if (tile.state == TardisState.REMAT) {
			this.demat = false;
			if (tile.animStartTime == 0L) {
				tile.animStartTime = System.currentTimeMillis();
			}
			if (tickTime > 100L) {
				tile.animStartTime += tickTime;
			}
			if (System.currentTimeMillis() - tile.animStartTime > 9000L) {
				tile.dematTime = 1.0F - (float) ((double) (System.currentTimeMillis() - (tile.animStartTime + 9000L)) / 10000.0);
			}
			if (tile.dematTime <= 0.0F) {
				tile.dematTime = 0.0F;
			}
			if (tile.dematTime == 0.0F) {
				tile.setState(TardisState.NEUTRAL);
				tile.animStartTime = 0L;
			}
		}

		tile.pulses = 1.0F - tile.dematTime + MathHelper.cos(tile.dematTime * 3.141592F * 10.0F) * 0.25F * MathHelper.sin(tile.dematTime * 3.141592F);
		if (this.getLevel().getBlockState(tile.getBlockPos().offset(0, -1, 0)).getMaterial() == Material.AIR) {
			++tile.bobTime;
			++this.rotation;
			if (dma$portal != null && dma$portal.isAlive() && dma$portalSpawned) {
				dma$portal.reloadAndSyncToClient();
				dma$portal.kill();
				dma$portal.remove(false);
				level.getChunk(this.worldPosition.getX(), this.worldPosition.getZ()).removeEntity(dma$portal);
				dma$portal.onRemovedFromWorld();
				dma$portal = null;
				dma$portalSpawned = false;
			}
		} else {
			tile.bobTime = 0;
			this.rotation = SWDMathUtils.SnapRotationToCardinal(this.rotation);
		}


		if (!level.isClientSide) {
			tile.tardisData = DMTardis.getTardis(tile.globalID);
			if (tile.tardisData != null) {

				if (tile.tardisData.getInteriorSpawnPosition() != null) {
					Position vec = tile.tardisData.getInteriorSpawnPosition();
					Vector3d pos = new Vector3d(vec.x(), vec.y() + 1.05, vec.z());
					dma$defaultAABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 2.0, 1.0);
					//size of the portal
					//handles how far out the portal is from the tardis

					ResourceLocation rl = tile.tardisData.getTardisExterior().getData().getModel(tile.tardisData.getSkinID());
					JSONModel model = ExteriorModels.getModel(rl);
					ModelWrapper modelWrapper = model.getModelData().getModel();
					ModelRendererWrapper mdl = modelWrapper.getPart("portal");

					AxisAlignedBB bounds = dma$defaultAABB.move(this.getBlockPos()).inflate(mdl == null ? -0.14200001192092896 : mdl.x / 200,
						mdl == null ? 0.0 : mdl.y / 200, mdl == null ? -0.14200001192092896 : mdl.z / 200); //These aren't accurate but it somewhat works

					bounds = bounds.move(Math.sin(Math.toRadians(this.rotation)) * 0.05,
						0.02, -Math.cos(Math.toRadians(this.rotation)) * 0.05);

					Direction tDir = Direction.fromYRot(this.rotation);

					//Dont lock em out
					if (dma$portal == null) {
						List<Entity> entities = this.level.getEntitiesOfClass(Entity.class, bounds);

						entities.removeIf((entity) -> entity instanceof PlayerEntity && DMFlightMode.isInFlight((PlayerEntity) entity));
						entities.removeIf(Entity::isPassenger);
						entities.removeIf(entity -> entity instanceof Portal);
						entities.removeIf(entity -> entity.equals(dma$portal));

						if (!entities.isEmpty()) {
							Entity e = entities.get(0);
							if (!TeleportUtil.TELEPORT_REQUESTS.containsKey(e)) {
								Location loc = new Location(new Vector3d(vec.x(), vec.y(), vec.z()), DMDimensions.TARDIS);
								loc.setFacing(this.tardisData.getInteriorSpawnRotation() + e.getYHeadRot() - this.rotation);
								TeleportUtil.TELEPORT_REQUESTS.put(e, new TeleportRequest(loc));
							}
						}
					}

					//Fixes portals remaining after exiting and re-entering the world
					McHelper.getNearbyPortals(level, Helper.blockPosToVec3(worldPosition), 1).forEach(portal -> {
						if (portal != null && portal != dma$portal) {
							portal.remove(false);
							portal.kill();
							portal.onRemovedFromWorld();
							dma$portalSpawned = false;
						}
					});

					if (((tile.state == TardisState.DEMAT || tile.state.equals(TardisState.REMAT)) || (tile.bobTime != 0) || (!tile.doorOpenRight))
						&& (dma$portal != null && dma$portal.isAlive() && dma$portalSpawned)) {
						dma$portal.reloadAndSyncToClient();
						dma$portal.kill();
						dma$portal.remove(false);
						level.getChunk(this.worldPosition.getX(), this.worldPosition.getZ()).removeEntity(dma$portal);
						dma$portal.onRemovedFromWorld();
						dma$portal = null;
						dma$portalSpawned = false;
					}

					/*
					 * List of exterior registry names
					 * dalekmod:tardis_capsule
					 * dalekmod:police_box
					 * dalekmod:fridge
					 * dalekmod:block_stack
					 * dalekmod:phone_booth
					 * dalekmod:pagoda
					 * dalekmod:dalek_mod_2013
					 * dalekmod:sidrat_capsule
					 */
					if (dma$portalSpawned && (dma$portal == null) || (dma$portal != null && !dma$portal.isAlive())) {
						dma$portalSpawned = false;
					}

					if (level != null) {
						if ((tile.doorOpenLeft || tile.doorOpenRight) && !dma$portalSpawned && tile.bobTime == 0) {

							dma$portal = PortalManipulation.createOrthodoxPortal(
								Portal.entityType,
								McHelper.getServerWorld(tile.tardisData.getCurrentLocation().dimensionWorldKey()),
								McHelper.getServerWorld(dma$TARDIS),
								tDir,
								bounds,
								pos
							);
							float tileRot = tile.rotation;
							float portalRot = 0;
							BlockPos newPos = worldPosition;
							if (tileRot == 0) {
								//newPos.offset(offset);
							}
							if (tileRot == 90) {
								//newPos.offset(offset);
							}
							if (tileRot == 180) {
								newPos = new BlockPos(newPos.getX() + 0.5, newPos.getY() + 1,newPos.getZ() + 1.25);
							}
							if (tileRot == 270) {
								//newPos.offset(offset);
							}
							dma$portal.setPos(newPos.getX(), newPos.getY(), newPos.getZ());
								portalRot = 180f;
							if (tileRot == 45 || tileRot == 135 || tileRot == 225 || tileRot == 315) portalRot = 225f;

							Quaternion quater = new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F),
								portalRot, true);
							if (portalRot != 0f) PortalManipulation.rotatePortalBody(dma$portal, quater);




							if (tDir == Direction.SOUTH) {
								dma$portal.setRotationTransformation(new Quaternion(0, 1, 0, 0));
							} else if (tDir == Direction.EAST) {
								dma$portal.setRotationTransformation(new Quaternion(0, 0.7071f, 0, 0.7071f));
							} else if (tDir == Direction.WEST) {
								dma$portal.setRotationTransformation(new Quaternion(0, -0.7071f, 0, 0.7071f));
							}

							McHelper.spawnServerEntity(dma$portal);
							dma$portalSpawned = true;
						}
						if (dma$portal != null && dma$portal.isAlive()) {
							dma$portal.renderingMergable = true;
							Position position = tardisData.getInteriorSpawnPosition();
							Vector3d vec3d = new Vector3d(position.x(), position.y(), position.z());
							if (!Objects.equals(dma$portal.destination, vec3d)) {
								dma$portal.setDestination(vec3d);
							}
							if (tile.doorOpenLeft || tile.doorOpenRight) {
								dma$portal.setDestination(pos);
							}
						}
					}
				}
			}
		}
	}
}
