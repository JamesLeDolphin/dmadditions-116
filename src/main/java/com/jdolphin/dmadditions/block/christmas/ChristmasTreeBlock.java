package com.jdolphin.dmadditions.block.christmas;

import com.jdolphin.dmadditions.entity.ChristmasTreeEntity;
import com.jdolphin.dmadditions.init.DMAEntities;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ChristmasTreeBlock extends Block {

	public ChristmasTreeBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState1, IWorld iWorld, BlockPos blockPos, BlockPos blockPos1) {
		return direction == Direction.DOWN && !this.canSurvive(blockState, iWorld, blockPos)
			? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, blockState1, iWorld, blockPos, blockPos1);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos blockPos) {
		return canSupportCenter(reader, blockPos.below(), Direction.UP);
	}

	@Override
	public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
		return VoxelShapes.or(VoxelShapes.box(0.1d, 0d, 0.1d, 0.9d, 1d, 0.9d),
			VoxelShapes.box(0.3d, 1d, 0.3d, 0.7d, 1.8d, 0.7d));
	}

	@Override
	public void spawnAfterBreak(BlockState state, ServerWorld world, BlockPos blockPos, ItemStack itemStack) {
		super.spawnAfterBreak(state, world, blockPos, itemStack);

		if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, itemStack) > 0) return;

		ChristmasTreeEntity entity = new ChristmasTreeEntity(DMAEntities.CHRISTMAS_TREE.get(), world);
		entity.moveTo(blockPos, 0, 0);
		entity.finalizeSpawn(world, world.getCurrentDifficultyAt(blockPos), SpawnReason.CONVERSION, null, null);
		world.addFreshEntity(entity);
	}

}
