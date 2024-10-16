package com.jdolphin.dmadditions.block.christmas;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class BaubleBlock extends Block {
	public BaubleBlock(Properties properties) {
		super(properties);
	}

	public BaubleBlock() {
		this(AbstractBlock.Properties.of(Material.GLASS).noOcclusion().noCollission());
	}

	@SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState1, IWorld world, BlockPos pos, BlockPos pos1) {
		return !this.canSurvive(blockState, world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, blockState1, world, pos, pos1);
	}

	public boolean canSurvive(BlockState blockState, IWorldReader reader, BlockPos pos) {
		BlockPos abovePos = pos.above();
		BlockState aboveState = reader.getBlockState(abovePos);

		return aboveState.isFaceSturdy(reader, abovePos, Direction.DOWN) || aboveState.is(BlockTags.LEAVES);
	}

	@Override
	public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
		return Block.box(5.0D, 10.0D, 5.0D, 11.0D, 16.0D, 11.0D);
	}
}
