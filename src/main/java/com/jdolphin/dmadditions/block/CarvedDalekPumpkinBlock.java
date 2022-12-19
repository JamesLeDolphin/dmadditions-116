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

	private static final Predicate<BlockState> PUMPKINS_PREDICATE = (p_210301_0_) ->
		p_210301_0_ != null && (p_210301_0_.is(DMABlocks.CARVED_DALEK_PUMPKIN.get()));

	public CarvedDalekPumpkinBlock(Properties p_i48432_1_) {
		super(p_i48432_1_);
	}

	@Override
	public void onPlace(BlockState p_220082_1_, World p_220082_2_, BlockPos p_220082_3_, BlockState p_220082_4_, boolean p_220082_5_) {
		if (!p_220082_4_.is(p_220082_1_.getBlock())) {
			this.trySpawnGolem(p_220082_2_, p_220082_3_);
		}
	}

	private void trySpawnGolem(World p_196358_1_, BlockPos p_196358_2_) {
		BlockPattern.PatternHelper blockpattern$patternhelper = this.getOrCreateSnowDalekFull().find(p_196358_1_, p_196358_2_);
		if (blockpattern$patternhelper != null) {
			for (int i = 0; i < this.getOrCreateSnowDalekFull().getHeight(); ++i) {
				CachedBlockInfo cachedblockinfo = blockpattern$patternhelper.getBlock(0, i, 0);
				p_196358_1_.setBlock(cachedblockinfo.getPos(), Blocks.AIR.defaultBlockState(), 2);
				p_196358_1_.levelEvent(2001, cachedblockinfo.getPos(), Block.getId(cachedblockinfo.getState()));
			}

			DalekEntity snowDalekEntity = new DalekEntity(DMEntities.DALEK_ENTITY.get(), p_196358_1_);
			snowDalekEntity.setID("snow_dalek");

			BlockPos blockpos1 = blockpattern$patternhelper.getBlock(0, 2, 0).getPos();
			snowDalekEntity.moveTo((double) blockpos1.getX() + 0.5D, (double) blockpos1.getY() + 0.05D, (double) blockpos1.getZ() + 0.5D, 0.0F, 0.0F);
			p_196358_1_.addFreshEntity(snowDalekEntity);

			for (ServerPlayerEntity serverplayerentity : p_196358_1_.getEntitiesOfClass(ServerPlayerEntity.class, snowDalekEntity.getBoundingBox().inflate(5.0D))) {
				CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayerentity, snowDalekEntity);
			}

			for (int l = 0; l < this.getOrCreateSnowDalekFull().getHeight(); ++l) {
				CachedBlockInfo cachedblockinfo3 = blockpattern$patternhelper.getBlock(0, l, 0);
				p_196358_1_.blockUpdated(cachedblockinfo3.getPos(), Blocks.AIR);
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
