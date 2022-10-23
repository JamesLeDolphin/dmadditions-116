package com.jdolphin.dmadditions.block;

import com.jdolphin.dmadditions.tileentity.RoundelContainerTileEntity;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;


public class RoundelContainerBlock extends BarrelBlock {

	public RoundelContainerBlock(Properties p_i49996_1_) {
		super(p_i49996_1_);
	}

	public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
		if (world.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			TileEntity tileentity = world.getBlockEntity(blockPos);
			if (tileentity instanceof RoundelContainerTileEntity) {
				playerEntity.openMenu((RoundelContainerTileEntity)tileentity);
				playerEntity.awardStat(Stats.OPEN_BARREL);
				PiglinTasks.angerNearbyPiglins(playerEntity, true);
			}

			return ActionResultType.CONSUME;
		}
	}

	@Nullable
	public TileEntity newBlockEntity(IBlockReader blockReader) {
		return new RoundelContainerTileEntity();
	}

	public BlockState getStateForPlacement(BlockItemUseContext context) {
		double rot = context.getHorizontalDirection().getOpposite().toYRot() + (float)(context.getPlayer().isShiftKeyDown() ? 90 : 0);
		return this.defaultBlockState().setValue(FACING, Direction.fromYRot(rot));
	}
}
