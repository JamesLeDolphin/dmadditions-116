package com.jdolphin.dmadditions.block.tardis;

import com.jdolphin.dmadditions.tileentity.ConsoleTileEntity;
import com.swdteam.common.block.TileEntityBaseBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ConsoleBlock extends TileEntityBaseBlock {

	public ConsoleBlock(Supplier<TileEntity> tileEntitySupplier, Properties properties) {
		super(tileEntitySupplier, properties);
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		World world = context.getLevel();
		BlockPos pos = context.getClickedPos();

		TileEntity tile = world.getBlockEntity(pos);
		if (tile instanceof ConsoleTileEntity) {
			ConsoleTileEntity console = (ConsoleTileEntity) tile;
			//console.onLoad();
		}
		return this.defaultBlockState();
	}

	public void destroy(IWorld world, BlockPos pos, BlockState state) {
		TileEntity te = world.getBlockEntity(pos);
		if (te instanceof ConsoleTileEntity) {
			ConsoleTileEntity consoleTile = (ConsoleTileEntity) te;
			consoleTile.setRemoved();
		}
	}

}
