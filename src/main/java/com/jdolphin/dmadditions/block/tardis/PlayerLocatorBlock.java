package com.jdolphin.dmadditions.block.tardis;

import com.jdolphin.dmadditions.DMAdditions;
import com.jdolphin.dmadditions.block.IBetterPanel;
import com.jdolphin.dmadditions.config.DMACommonConfig;
import com.jdolphin.dmadditions.init.DMAPackets;
import com.jdolphin.dmadditions.network.CBOpenGUIPacket;
import com.jdolphin.dmadditions.util.GuiHandler;
import com.swdteam.common.block.AbstractRotateableWaterLoggableBlock;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.util.ChatUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"deprecation"})
public class PlayerLocatorBlock extends AbstractRotateableWaterLoggableBlock implements IBetterPanel {

	public PlayerLocatorBlock(Properties properties) {
		super(properties);
	}

	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if (world.isClientSide || hand.equals(Hand.OFF_HAND))
			return super.use(state, world, pos, player, hand, hit);
		if (DMACommonConfig.canPlayerLocate() && (world.dimension().equals(DMDimensions.TARDIS) || (DMAdditions.hasNTM() &&
			net.tardis.mod.helper.WorldHelper.areDimensionTypesSame(world, net.tardis.mod.world.dimensions.TDimensions.DimensionTypes.TARDIS_TYPE)))) {
			DMAPackets.sendTo(((ServerPlayerEntity) player), new CBOpenGUIPacket(pos, GuiHandler.PLAYER_LOCATOR));
		}
		else if (!DMACommonConfig.canPlayerLocate()) ChatUtil.sendMessageToPlayer(player,
			new TranslationTextComponent("block.dmadditions.player_locator.json.disabled"), ChatUtil.MessageType.STATUS_BAR);
		return super.use(state, world, pos, player, hand, hit);
	}

	public @NotNull BlockRenderType getRenderShape(BlockState blockState) {
		return BlockRenderType.MODEL;
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
