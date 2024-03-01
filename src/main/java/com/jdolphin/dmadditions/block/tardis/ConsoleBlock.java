package com.jdolphin.dmadditions.block.tardis;

import com.jdolphin.dmadditions.entity.control.TardisControl;
import com.swdteam.common.block.TileEntityBaseBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ConsoleBlock extends TileEntityBaseBlock {

	public ConsoleBlock(Supplier<TileEntity> tileEntitySupplier, Properties properties) {
		super(tileEntitySupplier, properties);
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		World world = context.getLevel();

		return this.defaultBlockState();
	}

	public void destroy(IWorld world, BlockPos pos, BlockState state) {
		List<Entity> entities = world.getEntitiesOfClass(TardisControl.class, new AxisAlignedBB(0, 0, 0, 1, 1 , 1));
		System.out.print("DELEJGEIAHFUNAI");
		for (Entity control : entities) {
			control.remove(false);
		}
	}

}
