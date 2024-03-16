package com.jdolphin.dmadditions.mixin.comp.immp;

import com.jdolphin.dmadditions.util.Helper;
import com.qouteall.immersive_portals.McHelper;
import com.qouteall.immersive_portals.portal.Portal;
import com.qouteall.immersive_portals.portal.PortalManipulation;
import com.swdteam.client.tardis.data.ExteriorModels;
import com.swdteam.common.entity.rift.RiftEntity;
import com.swdteam.common.event.custom.tardis.TardisEvent;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.Location;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisFlightData;
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
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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

	@Shadow(remap = false)
	public long animStartTime;

	@Shadow(remap = false)
	public float dematTime;

	@Shadow(remap = false)
	public abstract void removeLight();

	@Shadow(remap = false)
	public int globalID;

	@Shadow(remap = false)
	public abstract void setState(TardisState state);

	@Shadow(remap = false)
	public TardisState state;

	@Shadow(remap = false)
	public abstract void pulseLight();

	@Shadow(remap = false)
	public long lastTickTime;

	@Shadow(remap = false)
	public abstract void snowCheck();

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
		if (this.level.isClientSide) {
			if (this.level.random.nextInt(100) == 50) {
				this.snowCheck();
			}
		} else {
			this.tardisData = DMTardis.getTardis(this.globalID);
		}

		this.doorAnimation();
		long tickTime = System.currentTimeMillis() - this.lastTickTime;
		this.lastTickTime = System.currentTimeMillis();
		if (this.tardisData != null) {
			this.tardisData.getRiftData().setType("");
		}

		Iterator var3;
		if (this.level.getDayTime() % 20L == 0L) {
			var3 = this.level.getEntitiesOfClass(RiftEntity.class, new AxisAlignedBB((double)(this.worldPosition.getX() - 16), (double)(this.worldPosition.getY() - 4), (double)(this.worldPosition.getZ() - 16), (double)(this.worldPosition.getX() + 16), (double)(this.worldPosition.getY() + 4), (double)(this.worldPosition.getZ() + 16)), Entity::isAlive).iterator();

			while(var3.hasNext()) {
				RiftEntity rift = (RiftEntity)var3.next();
				if (rift.getRiftData() != null) {
					rift.getRiftData().tardisUseRiftTick((((TardisTileEntity) (Object) this)), (PlayerEntity)null, this.tardisData, rift);
				}
			}
		}

		this.pulseLight();
		if (this.state == TardisState.DEMAT) {
			this.demat = true;
			if (this.animStartTime == 0L) {
				this.animStartTime = System.currentTimeMillis();
			}

			if (tickTime > 100L) {
				this.animStartTime += tickTime;
			}

			this.dematTime = (float)((double)(System.currentTimeMillis() - this.animStartTime) / 10000.0);
			if (this.dematTime >= 1.0F) {
				this.dematTime = 1.0F;
			}

			if (this.dematTime == 1.0F) {
				MinecraftForge.EVENT_BUS.post(new TardisEvent.DeMatFinish(this.tardisData, (PlayerEntity)null, this.level, false, (TardisFlightData)null, this.worldPosition));
				this.removeLight();
				this.level.setBlockAndUpdate(this.getBlockPos(), Blocks.AIR.defaultBlockState());
				this.animStartTime = 0L;
			}
		} else if (this.state == TardisState.REMAT) {
			this.demat = false;
			if (this.animStartTime == 0L) {
				this.animStartTime = System.currentTimeMillis();
			}

			if (tickTime > 100L) {
				this.animStartTime += tickTime;
			}

			if (System.currentTimeMillis() - this.animStartTime > 9000L) {
				this.dematTime = 1.0F - (float)((double)(System.currentTimeMillis() - (this.animStartTime + 9000L)) / 10000.0);
			}

			if (this.dematTime <= 0.0F) {
				this.dematTime = 0.0F;
			}

			if (this.dematTime == 0.0F) {
				MinecraftForge.EVENT_BUS.post(new TardisEvent.MatFinish(this.tardisData, (PlayerEntity)null, this.level, (TardisFlightData)null, this.worldPosition));
				this.tardisData = DMTardis.getTardis(this.globalID);
				if (this.tardisData != null) {
					var3 = this.level.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(this.worldPosition)).iterator();

					while(var3.hasNext()) {
						LivingEntity livingEntity = (LivingEntity)var3.next();
						TeleportUtil.teleportPlayer(livingEntity, DMDimensions.TARDIS, new Vector3d(this.tardisData.getInteriorSpawnPosition().x(), this.tardisData.getInteriorSpawnPosition().y(), this.tardisData.getInteriorSpawnPosition().z()), 90.0F);
					}
				}

				this.setState(TardisState.NEUTRAL);
				this.animStartTime = 0L;
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

					if (dma$portalSpawned && (dma$portal == null) || (dma$portal != null && !dma$portal.isAlive())) {
						dma$portalSpawned = false;
					}

					//Dont lock em out
					if (dma$portal == null && (tile.doorOpenLeft || tile.doorOpenRight)) {
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
							BlockPos wPos = worldPosition;

							if (tileRot == 0) {
								dma$portal.setPos(wPos.getX() + 0.5, wPos.getY() + 1, wPos.getZ() + 0.01);
								portalRot = 180f;
							}
							if (tileRot == 90) {
								dma$portal.setPos(wPos.getX() + 0.99, wPos.getY() + 1, wPos.getZ() + 0.5);
								portalRot = 180f;
							}
							if (tileRot == 180) {
								dma$portal.setPos(wPos.getX() + 0.5, wPos.getY() + 1, wPos.getZ() + 0.99);
								portalRot = 180f;
							}
							if (tileRot == 270) {
								dma$portal.setPos(wPos.getX() + 0.01, wPos.getY() + 1, wPos.getZ() + 0.5);
								portalRot = 180f;
							}


							//Diagonal
							float f = 225;
							if (tileRot == 45) {
								dma$portal.setPos(wPos.getX() + 0.78, wPos.getY() + 1, wPos.getZ() + 0.22);
								portalRot = f;
							}
							if (tileRot == 135) {
								dma$portal.setPos(wPos.getX() + 0.78, wPos.getY() + 1, wPos.getZ() + 0.78);
								portalRot = f;
							}
							if (tileRot == 225) {
								dma$portal.setPos(wPos.getX() + 0.22, wPos.getY() + 1, wPos.getZ() + 0.78);
								portalRot = f;
							}
							if (tileRot == 315) {
								dma$portal.setPos(wPos.getX() + 0.22, wPos.getY() + 1, wPos.getZ() + 0.22);
								portalRot = f;
							}

							Quaternion quater = new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F),
								portalRot, true);
							if (portalRot != 0f) PortalManipulation.rotatePortalBody(dma$portal, quater);


							Vector3f f3 = new Vector3f(0, 1, 0);
							if (tileRot == 0) { //S
								dma$portal.setRotationTransformation(new Quaternion(0, 1, 0, 0));
							}
							if (tileRot == 45) { //NE
								dma$portal.setRotationTransformation(new Quaternion(f3, 225, true));
							}
							if (tileRot == 135) { //SE
								dma$portal.setRotationTransformation(new Quaternion(f3, -45, true));
							}
							if (tileRot == 225) { //SW
								dma$portal.setRotationTransformation(new Quaternion(f3, 45, true));
							}
							if (tileRot == 315) { //NW
								dma$portal.setRotationTransformation(new Quaternion(f3, -225, true));
							}
							if (tileRot == 270) { //E
								dma$portal.setRotationTransformation(new Quaternion(0, 0.7071f, 0, 0.7071f));
							}
							if (tileRot == 90) { //W
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
