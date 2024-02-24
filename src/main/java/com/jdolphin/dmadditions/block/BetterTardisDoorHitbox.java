package com.jdolphin.dmadditions.block;

import com.jdolphin.dmadditions.DmAdditions;
import com.swdteam.common.block.AbstractRotateableWaterLoggableBlock;
import com.swdteam.common.block.TileEntityBaseBlock;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.util.TeleportUtil;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BetterTardisDoorHitbox extends TileEntityBaseBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public RegistryKey<World> TARDIS = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("dalekmod:tardis"));

	public BetterTardisDoorHitbox(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
		super(tileEntitySupplier, properties);
	}

	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context){
			BlockState blockstate = this.defaultBlockState().getBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());

			if (blockstate.canSurvive(context.getLevel(), context.getClickedPos())) {
				return blockstate;
			}
		return this.defaultBlockState();
	}

	public @NotNull ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (handIn == Hand.MAIN_HAND && worldIn.dimension().equals(DMDimensions.TARDIS) && !worldIn.isClientSide()) {
			TardisData data = DMTardis.getTardis(DMTardis.getIDForXZ(pos.getX(), pos.getZ()));
			if (data != null && !DMFlightMode.isInFlight(data.getGlobalID())) {
				ServerWorld world = player.getServer().getLevel(data.getCurrentLocation().dimensionWorldKey());
				TileEntity te = world.getBlockEntity(data.getCurrentLocation().getBlockPosition());
				if (te instanceof TardisTileEntity) {
					TardisTileEntity tile = (TardisTileEntity) te;
					BlockPos tardisPosition = data.getCurrentLocation().getBlockPosition();
					TardisActionList.addForceField(world, data.getCurrentLocation().getBlockPosition());
					Vector3d look = Vector3d.directionFromRotation(new Vector2f(45.0F, tile.rotation + 180.0F));
					float distance = 2.0F;
					double dx = (double) tardisPosition.getX() + look.x * (double) distance;
					double dy = tardisPosition.getY() > 0 ? (double) tardisPosition.getY() : 128.0;
					double dz = (double) tardisPosition.getZ() + look.z * (double) distance;

					if (DmAdditions.hasIMMP()) {
						Direction tDir = Direction.fromYRot(data.getCurrentLocation().getFacing());

						com.qouteall.immersive_portals.portal.Portal portal = com.qouteall.immersive_portals.portal.PortalManipulation.createOrthodoxPortal(
							com.qouteall.immersive_portals.portal.Portal.entityType,
							com.qouteall.immersive_portals.McHelper.getServerWorld(TARDIS),
							com.qouteall.immersive_portals.McHelper.getServerWorld(tile.tardisData.getCurrentLocation().dimensionWorldKey()),
							tDir,
							new AxisAlignedBB(0, 0, 0, 16, 16, 2),
							new Vector3d(dx + 0.5, dy, dz + 0.5)
						);
						portal.renderingMergable = false;
						if (tDir == Direction.NORTH) {
							portal.setRotationTransformation(new Quaternion(0, 1, 0, 0));
						} else if (tDir == Direction.WEST) {
							portal.setRotationTransformation(new Quaternion(0, 0.7071f, 0, 0.7071f));
						} else if (tDir == Direction.EAST) {
							portal.setRotationTransformation(new Quaternion(0, -0.7071f, 0, 0.7071f));
						}
					} else {
						TeleportUtil.teleportPlayer(player, world.dimension(), new Vector3d(dx + 0.5, dy, dz + 0.5), tile.rotation + 180.0F);
					}
				}
			}
		}

		return ActionResultType.CONSUME;
	}

	public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.empty();
	}

	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}

	public boolean isPathfindable(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
		return true;
	}
}
