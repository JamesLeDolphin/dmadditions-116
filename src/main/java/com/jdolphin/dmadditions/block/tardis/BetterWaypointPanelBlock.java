package com.jdolphin.dmadditions.block.tardis;

import com.jdolphin.dmadditions.block.IBetterPanel;
import com.jdolphin.dmadditions.init.DMATags;
import com.swdteam.common.block.tardis.WaypointPanelBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class BetterWaypointPanelBlock extends WaypointPanelBlock implements IBetterPanel {
	public final String LOAD_CARTRIDGE = "load_data_module";
	public final String EJECT_CARTRIDGE = "eject_data_module";
	public final String APPLY_WAYPOINT = "apply_waypoint";

	public BetterWaypointPanelBlock(Properties properties) {
		super(properties);
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
		return IBetterPanel.super.getStateForPlacement(context, this.defaultBlockState());
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return IBetterPanel.super.getShape(state, worldIn, pos, context);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return IBetterPanel.super.getCollisionShape(state, worldIn, pos, context);
	}

	@Override
	public ITextComponent getName(BlockState state, BlockPos pos, Vector3d hitVec, PlayerEntity player) {
		return IBetterPanel.super.getName(state, pos, hitVec, player);
	}

	@Override
	public String getTooltipTranslationKey(BlockState blockState, BlockPos blockPos, Vector3d vector3d, PlayerEntity player) {
		boolean hasCartridge = blockState.getValue(CARTRIDGE_TYPE) != 0;
		if (hasCartridge) {
			return player.isShiftKeyDown() ? this.EJECT_CARTRIDGE : this.APPLY_WAYPOINT;
		}
		Item item = player.getMainHandItem().getItem();
		if (item.is(DMATags.Items.DATA_MODULES))
			return this.LOAD_CARTRIDGE;

		return null;
	}
}
