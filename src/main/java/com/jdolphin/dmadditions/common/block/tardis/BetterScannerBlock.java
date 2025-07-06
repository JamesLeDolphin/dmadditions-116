package com.jdolphin.dmadditions.common.block.tardis;

import com.jdolphin.dmadditions.common.block.IBetterPanel;
import com.jdolphin.dmadditions.common.tileentity.BetterScannerTileEntity;
import com.swdteam.common.block.tardis.ScannerBlock;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class BetterScannerBlock extends ScannerBlock implements IBetterPanel {

	public BetterScannerBlock(Supplier<TileEntity> tileEntitySupplier, Properties properties) {
		super(tileEntitySupplier, properties);
	}

	public Vector3d getScreenTranslate() {
		return Vector3d.ZERO;
	}

	public Vector3f getScreenRotate() {
		return new Vector3f(0, 0, 0);
	}

	public Vector3f getScreenScale() {
		return new Vector3f(1, 1, 1);
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (handIn == Hand.MAIN_HAND && !worldIn.isClientSide && worldIn.dimension() == DMDimensions.TARDIS) {
			TileEntity te = worldIn.getBlockEntity(pos);
			if (te instanceof BetterScannerTileEntity) {
				if (player.isShiftKeyDown()) {
					((BetterScannerTileEntity) te).changeScreenRelative(-1);
				} else {
					((BetterScannerTileEntity) te).changeScreenRelative(1);
				}

				((BetterScannerTileEntity) te).sendUpdates();
				worldIn.playSound(null, pos, DMSoundEvents.TARDIS_CONTROLS_SCANNER.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
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
