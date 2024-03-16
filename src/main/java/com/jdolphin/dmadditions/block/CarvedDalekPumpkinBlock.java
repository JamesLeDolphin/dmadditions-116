package com.jdolphin.dmadditions.block;

import com.jdolphin.dmadditions.init.DMABlocks;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.init.DMEntities;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class CarvedDalekPumpkinBlock extends CarvedPumpkinBlock {
	@Nullable
	private BlockPattern snowGolemBase;
	@Nullable
	private BlockPattern snowGolemFull;

	private static final Predicate<BlockState> PUMPKINS_PREDICATE = (blockState) ->
		blockState != null && (blockState.is(DMABlocks.CARVED_DALEK_PUMPKIN.get()));

	public CarvedDalekPumpkinBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void onPlace(BlockState blockState, World world, BlockPos blockPos, BlockState blockState1, boolean b) {
		if (!blockState1.is(blockState.getBlock())) {
			this.trySpawnGolem(world, blockPos);
		}
	}

	private void trySpawnGolem(World world, BlockPos blockPos) {
		BlockPattern.PatternHelper blockpattern$patternhelper = this.getOrCreateSnowDalekFull().find(world, blockPos);
		if (blockpattern$patternhelper != null) {
			for (int i = 0; i < this.getOrCreateSnowDalekFull().getHeight(); ++i) {
				CachedBlockInfo cachedblockinfo = blockpattern$patternhelper.getBlock(0, i, 0);
				world.setBlock(cachedblockinfo.getPos(), Blocks.AIR.defaultBlockState(), 2);
				world.levelEvent(2001, cachedblockinfo.getPos(), Block.getId(cachedblockinfo.getState()));
			}

			DalekEntity snowDalekEntity = new DalekEntity(DMEntities.DALEK_ENTITY.get(), world);
			snowDalekEntity.setID("snow_dalek");

			BlockPos blockpos1 = blockpattern$patternhelper.getBlock(0, 2, 0).getPos();
			snowDalekEntity.moveTo((double) blockpos1.getX() + 0.5D, (double) blockpos1.getY() + 0.05D, (double) blockpos1.getZ() + 0.5D, 0.0F, 0.0F);
			world.addFreshEntity(snowDalekEntity);

			for (ServerPlayerEntity serverplayerentity : world.getEntitiesOfClass(ServerPlayerEntity.class, snowDalekEntity.getBoundingBox().inflate(5.0D))) {
				CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayerentity, snowDalekEntity);
			}

			for (int l = 0; l < this.getOrCreateSnowDalekFull().getHeight(); ++l) {
				CachedBlockInfo cachedblockinfo3 = blockpattern$patternhelper.getBlock(0, l, 0);
				world.blockUpdated(cachedblockinfo3.getPos(), Blocks.AIR);
			}
		}
	}

	private BlockPattern getOrCreateSnowGolemBase() {
		if (this.snowGolemBase == null) {
			this.snowGolemBase = BlockPatternBuilder.start().aisle(" ", "#", "#").where('#',
				CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.SNOW_BLOCK))).build();
		}

		return this.snowGolemBase;
	}

	private BlockPattern getOrCreateSnowDalekFull() {
		if (this.snowGolemFull == null) {
			this.snowGolemFull = BlockPatternBuilder.start().aisle("^", "#", "#")
				.where('^', CachedBlockInfo.hasState(PUMPKINS_PREDICATE)).where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.SNOW_BLOCK))).build();
		}

		return this.snowGolemFull;
	}
}
