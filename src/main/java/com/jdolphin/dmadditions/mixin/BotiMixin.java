package com.jdolphin.dmadditions.mixin;

import com.qouteall.immersive_portals.McHelper;
import com.qouteall.immersive_portals.portal.Portal;
import com.qouteall.immersive_portals.portal.PortalManipulation;
import com.swdteam.client.tardis.data.ExteriorModels;
import com.swdteam.common.init.*;
import com.swdteam.common.tardis.*;
import com.swdteam.common.teleport.TeleportRequest;
import com.swdteam.common.tileentity.ExtraRotationTileEntityBase;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelRendererWrapper;
import com.swdteam.model.javajson.ModelWrapper;
import com.swdteam.util.SWDMathUtils;
import com.swdteam.util.TeleportUtil;
import com.swdteam.util.math.Position;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static com.swdteam.common.init.DMDimensions.TARDIS;

@Mixin(TardisTileEntity.class)
public abstract class BotiMixin extends ExtraRotationTileEntityBase implements ITickableTileEntity  {

	@Shadow
	public TardisData tardisData;
	@Shadow boolean demat;
	boolean spawned = true;
	boolean removed = true;
	boolean canRemove = false;

	public Portal portal = null;

	boolean isPortalSpawned = false;

	private static AxisAlignedBB defaultAABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 2.0, 1.0);
	public RegistryKey<World> TARDIS = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("dalekmod:tardis"));
	public RegistryKey<World> OVERWORLD = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("overworld"));


	public BotiMixin() {
		super((TileEntityType) DMBlockEntities.TILE_TARDIS.get());
	}

	/**
	 * @author Made by BobDude, modified by JamesLeDolphin to actually make it work
	 * @reason BOTI
	 */
	@Overwrite
	public void tick() {
		TardisTileEntity t = (TardisTileEntity) ((Object) this);

		this.doorAnimation();
		long tickTime = System.currentTimeMillis() - t.lastTickTime;
		t.lastTickTime = System.currentTimeMillis();
		if (t.state == TardisState.DEMAT) {
			this.demat = true;
			if (t.animStartTime == 0L) {
				t.animStartTime = System.currentTimeMillis();
			}

			if (tickTime > 100L) {
				t.animStartTime += tickTime;
			}

			t.dematTime = (float)((double)(System.currentTimeMillis() - t.animStartTime) / 10000.0);
			if (t.dematTime >= 1.0F) {
				t.dematTime = 1.0F;
			}

			if (t.dematTime == 1.0F) {
				this.level.setBlockAndUpdate(t.getBlockPos(), Blocks.AIR.defaultBlockState());
				t.animStartTime = 0L;
			}
		} else if (t.state == TardisState.REMAT) {
			this.demat = false;
			if (t.animStartTime == 0L) {
				t.animStartTime = System.currentTimeMillis();
			}

			if (tickTime > 100L) {
				t.animStartTime += tickTime;
			}

			if (System.currentTimeMillis() - t.animStartTime > 9000L) {
				t.dematTime = 1.0F - (float)((double)(System.currentTimeMillis() - (t.animStartTime + 9000L)) / 10000.0);
			}

			if (t.dematTime <= 0.0F) {
				t.dematTime = 0.0F;
			}

			if (t.dematTime == 0.0F) {
				t.setState(TardisState.NEUTRAL);
				t.animStartTime = 0L;
			}
		}

		t.pulses = 1.0F - t.dematTime + MathHelper.cos(t.dematTime * 3.141592F * 10.0F) * 0.25F * MathHelper.sin(t.dematTime * 3.141592F);
		if (this.getLevel().getBlockState(t.getBlockPos().offset(0, -1, 0)).getMaterial() == Material.AIR) {
			++t.bobTime;
			++this.rotation;
		} else {
			t.bobTime = 0;
			this.rotation = SWDMathUtils.SnapRotationToCardinal(this.rotation);
		}


		if (!this.level.isClientSide) {
			t.tardisData = DMTardis.getTardis(t.globalID);
                    /*
                    TODO
                    Change portal size based on exterior
                    add SOTO
                    //Sometimes you can walk through the door
                    //The portal still bugs and doesn't update the position sometimes
                     */
			if (t.tardisData != null) {

				if (t.tardisData.getInteriorSpawnPosition() != null) {
					Position vec = t.tardisData.getInteriorSpawnPosition();
					Vector3d pos = new Vector3d(vec.x(), vec.y() + 1.05, vec.z());
					defaultAABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 2.0, 1.0);
					//size of the portal
					AxisAlignedBB bounds = defaultAABB.move(this.getBlockPos()).inflate(-0.14200001192092896, 0.0, -0.14200001192092896);
					//handles how far out the portal is from the tardis
					bounds = bounds.move(Math.sin(Math.toRadians((double) this.rotation)) * 0.1, 0.0, -Math.cos(Math.toRadians((double) this.rotation)) * 0.1);

					Direction tDir = Direction.byName(SWDMathUtils.rotationToCardinal(t.rotation));
					if (!t.doorOpenLeft && !t.doorOpenRight) {
						//System.out.println(SWDMathUtils.rotationToCardinal(flightData.getRotationAngle()));
						if (((t.state == TardisState.DEMAT || t.state.equals(TardisState.REMAT)) || this.level.getBlockState(t.getBlockPos().below()).is(Blocks.AIR))
							&& (portal != null && portal.isAlive())) {
							portal.kill();
							portal.remove(false);
							level.getChunk(this.worldPosition.getX(), this.worldPosition.getZ()).removeEntity(portal);
							portal.onRemovedFromWorld();
							portal = null;
							isPortalSpawned = false;
						}
					}
					/**
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

					if((t.doorOpenLeft || t.doorOpenRight) && !isPortalSpawned && tDir != null) {
						portal = PortalManipulation.createOrthodoxPortal(
							Portal.entityType,
							McHelper.getServerWorld(t.tardisData.getCurrentLocation().dimensionWorldKey()),
							McHelper.getServerWorld(TARDIS),
							tDir,
							bounds,
							pos
						);
						//portal.renderingMergable = true; //Recommended to check if overlapping portals, so doesn't have visual bugs
						if(tDir == Direction.NORTH) {
							portal.setRotationTransformation(new Quaternion(0, 1, 0, 0)); //flips it around
						}
						else if(tDir == Direction.WEST) {
							portal.setRotationTransformation(new Quaternion(0, 0.7071f, 0,  0.7071f)); //flips it around
						}
						else if(tDir == Direction.EAST) {
							portal.setRotationTransformation(new Quaternion(0, -0.7071f, 0,  0.7071f)); //flips it around
						}
						McHelper.spawnServerEntity(portal);
						isPortalSpawned = true;
					}

					if(portal != null && portal.isAlive()) {
						if (t.doorOpenLeft || t.doorOpenRight) {
							portal.setDestination(pos);
							if(!portal.level.isClientSide) {

							}
						}

					}
				}

				return;
			}

		}
	}


	public Direction rotationToCardinal(float rotation) {
		int a = Math.round(rotation / 45.0F);

		while (a < 0 || a > 7) {
			if (a < 0) {
				a += 8;
			}

			if (a > 7) {
				a -= 8;
			}
		}

		switch (a) {
			case 0:
				return Direction.NORTH;
			case 1:
				//return "NORTHEAST";
			case 2:
				return Direction.EAST;
			case 3:
				//return "SOUTHEAST";
			case 4:
				return Direction.SOUTH;
			case 5:
				//return "SOUTHWEST";
			case 6:
				return Direction.WEST;
			case 7:
				//return "NORTHWEST";
			default:
				return Direction.NORTH;
		}
	}

	@Shadow public abstract void doorAnimation();


}
