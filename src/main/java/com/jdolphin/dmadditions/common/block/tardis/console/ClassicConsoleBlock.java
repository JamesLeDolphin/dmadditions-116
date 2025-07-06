package com.jdolphin.dmadditions.common.block.tardis.console;

import com.jdolphin.dmadditions.common.tileentity.console.ClassicConsoleTileEntity;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class ClassicConsoleBlock extends AbstractConsoleBlock {

	public ClassicConsoleBlock(Properties properties) {
		super(ClassicConsoleTileEntity::new, properties);
	}

	@Override
	public VoxelShape getHitbox() {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.join(shape, VoxelShapes.box(0, 0, 0, 1, 0.875, 1), IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, VoxelShapes.box(-0.375, 0.875, -0.375, 1.375, 1, 1.375), IBooleanFunction.OR);
		return shape;
	}

}
