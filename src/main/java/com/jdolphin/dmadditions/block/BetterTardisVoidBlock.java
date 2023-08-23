package com.jdolphin.dmadditions.block;

import com.jdolphin.dmadditions.tile.BetterTardisVoidTile;
import com.qouteall.immersive_portals.McHelper;
import com.qouteall.immersive_portals.portal.Portal;
import com.qouteall.immersive_portals.portal.PortalManipulation;
import com.swdteam.common.block.tardis.TardisDoorBlock;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.util.TeleportUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.function.Supplier;

public class BetterTardisVoidBlock extends TardisDoorBlock {
	public BetterTardisVoidBlock(Supplier<TileEntity> tileEntitySupplier, Properties properties) {
		super(tileEntitySupplier, properties);
	}
	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (handIn == Hand.MAIN_HAND && worldIn.dimension().equals(DMDimensions.TARDIS) && !worldIn.isClientSide) {
			TardisData data = DMTardis.getTardis(DMTardis.getIDForXZ(pos.getX(), pos.getZ()));
			if (data != null && !DMFlightMode.isInFlight(data.getGlobalID())) {
				World world = player.getServer().getLevel(data.getCurrentLocation().dimensionWorldKey());
				TileEntity te = world.getBlockEntity(data.getCurrentLocation().getBlockPosition());
				if (te != null && te instanceof BetterTardisVoidTile) {
					BetterTardisVoidTile tard = (BetterTardisVoidTile)te;
					BlockPos tardisPosition = data.getCurrentLocation().getBlockPosition();
					TardisActionList.addForceField((ServerWorld)world, data.getCurrentLocation().getBlockPosition());
					Vector3d look = Vector3d.directionFromRotation(new Vector2f(45.0F, tard.rotation + 180.0F));
					float distance = 2.0F;
					double dx = (double)tardisPosition.getX() + look.x * (double)distance;
					double dy = tardisPosition.getY() > 0 ? (double)tardisPosition.getY() : 128.0;
					double dz = (double)tardisPosition.getZ() + look.z * (double)distance;

					AxisAlignedBB defaultAABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 2.0, 1.0);
					AxisAlignedBB bounds = defaultAABB.move(tardisPosition).inflate(-0.14200001192092896, 0.0, 0);
					Direction dir = Direction.fromYRot(((BetterTardisVoidTile) te).rotation);
					BlockPos pos1 = data.getCurrentLocation().getBlockPosition();
					Vector3d vecPos = new Vector3d(pos1.getX(), pos1.getY(), pos1.getZ());

					Portal portal = PortalManipulation.createOrthodoxPortal(
						Portal.entityType,
						McHelper.getServerWorld(DMDimensions.TARDIS),
						McHelper.getServerWorld(data.getCurrentLocation().dimensionWorldKey()),
						dir,
						bounds,
						vecPos
					);
					if(dir == Direction.NORTH)
					{
						portal.setRotationTransformation(new Quaternion(0, 1, 0, 0)); //flips it around
					}
					else if(dir == Direction.WEST)
					{
						portal.setRotationTransformation(new Quaternion(0, 0.7071f, 0,  0.7071f)); //flips it around
					}
					else if(dir == Direction.EAST)
					{
						portal.setRotationTransformation(new Quaternion(0, -0.7071f, 0,  0.7071f)); //flips it around
					}
					McHelper.spawnServerEntity(portal);
				}
			}
		}

		return ActionResultType.CONSUME;
	}
}
