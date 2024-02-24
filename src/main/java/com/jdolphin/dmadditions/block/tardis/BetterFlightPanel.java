package com.jdolphin.dmadditions.block.tardis;

import com.jdolphin.dmadditions.block.IBetterPanel;
import com.swdteam.common.block.tardis.FlightPanelBlock;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.TeleportUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BetterFlightPanel extends FlightPanelBlock implements IBetterPanel {
	public BetterFlightPanel(Properties properties) {
		super(properties);
	}
	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
		if (handIn == Hand.MAIN_HAND && !worldIn.isClientSide) {
			if (worldIn.dimension().equals(DMDimensions.TARDIS)) {
				TardisData data = DMTardis.getTardisFromInteriorPos(pos);
				if (data != null && !data.isInFlight() && data.getCurrentLocation() != null && !DMFlightMode.isInFlight(data.getGlobalID())) {
					ServerWorld world = worldIn.getServer().getLevel(data.getCurrentLocation().dimensionWorldKey());
					world.setBlockAndUpdate(data.getCurrentLocation().getBlockPosition(), Blocks.AIR.defaultBlockState());
					DMFlightMode.addFlight(player, new DMFlightMode.FlightModeData(data.getGlobalID(), player.getX(), player.getY(), player.getZ()));
					TeleportUtil.teleportPlayer(player, data.getCurrentLocation().dimensionWorldKey(), new Vector3d(data.getCurrentLocation().getPosition().x(), data.getCurrentLocation().getPosition().y(), data.getCurrentLocation().getPosition().z()), 0.0F);
					player.abilities.mayBuild = false;
					if (data.getFuel() > 0.0) {
						player.abilities.flying = true;
						player.abilities.mayfly = true;
					} else {
						player.abilities.flying = false;
						player.abilities.mayfly = false;
					}

					player.onUpdateAbilities();
				} else if (data.isInFlight()) {
					ChatUtil.sendError(player, "TARDIS is currently in flight", ChatUtil.MessageType.CHAT);
				}
			}
		}
		return ActionResultType.CONSUME;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACE);
	}

	@Override
	public BlockState updateShape(BlockState state1, Direction dir, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2) {
		return IBetterPanel.super.updateShape(state1, dir, state2, world, pos1, pos2);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) {
		return IBetterPanel.super.canSurvive(state, reader, pos);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return IBetterPanel.super.getStateForPlacement(context, defaultBlockState());
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return IBetterPanel.super.getShape(state, worldIn, pos, context);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return IBetterPanel.super.getCollisionShape(state, worldIn, pos, context);
	}
}
