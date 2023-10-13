package com.jdolphin.dmadditions.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BaubleBlock extends Block {
	public BaubleBlock(Properties p_i48440_1_) {
		super(p_i48440_1_);
	}

	public BaubleBlock(){
		this(AbstractBlock.Properties.of(Material.GLASS).noOcclusion());
	}

	@Override
	public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
		return Block.box(5.0D, 10.0D, 5.0D, 11.0D, 16.0D, 11.0D);
	}
}
