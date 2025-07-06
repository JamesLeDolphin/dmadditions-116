package com.jdolphin.dmadditions.common.block.tardis.console;

import com.jdolphin.dmadditions.common.tileentity.console.AbstractConsoleTileEntity;
import com.swdteam.common.block.TileEntityBaseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public abstract class AbstractConsoleBlock extends TileEntityBaseBlock {
	public static final DirectionProperty FACING = HorizontalBlock.FACING;

	public AbstractConsoleBlock(Supplier<TileEntity> tileEntitySupplier, Properties properties) {
		super(tileEntitySupplier, properties);
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return getHitbox();
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
		super.createBlockStateDefinition(builder);
	}

	public abstract VoxelShape getHitbox();

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	public BlockRenderType getRenderShape(BlockState p_149645_1_) {
		return BlockRenderType.INVISIBLE;
	}

	public void destroy(IWorld world, BlockPos pos, BlockState state) {
		TileEntity te = world.getBlockEntity(pos);
		if (te instanceof AbstractConsoleTileEntity) {
			AbstractConsoleTileEntity consoleTile = (AbstractConsoleTileEntity) te;
			consoleTile.setRemoved();
		}
	}
}
