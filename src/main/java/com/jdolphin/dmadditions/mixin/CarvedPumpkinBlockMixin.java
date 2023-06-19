package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.entity.SnowmanEntity;
import com.jdolphin.dmadditions.init.DMAEntities;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(CarvedPumpkinBlock.class)
public abstract class CarvedPumpkinBlockMixin {
	@Shadow
	protected abstract BlockPattern getOrCreateSnowGolemFull();

	@Inject(method = "trySpawnGolem", at = @At("HEAD"))
	public void trySpawnGolem(World p_196358_1_, BlockPos p_196358_2_, CallbackInfo ci) {

		BlockPattern.PatternHelper blockpattern$patternhelper = this.getOrCreateSnowGolemFull().find(p_196358_1_, p_196358_2_);
		if (blockpattern$patternhelper != null) {
			for (int i = 0; i < this.getOrCreateSnowGolemFull().getHeight(); ++i) {
				CachedBlockInfo cachedblockinfo = blockpattern$patternhelper.getBlock(0, i, 0);
				p_196358_1_.setBlock(cachedblockinfo.getPos(), Blocks.AIR.defaultBlockState(), 2);
				p_196358_1_.levelEvent(2001, cachedblockinfo.getPos(), Block.getId(cachedblockinfo.getState()));
			}

			LivingEntity snowgolementity;
			BlockPos blockpos1 = blockpattern$patternhelper.getBlock(0, 2, 0).getPos();
			if (new Random().nextBoolean()) {
				snowgolementity = EntityType.SNOW_GOLEM.create(p_196358_1_);
				snowgolementity.moveTo((double) blockpos1.getX() + 0.5D, (double) blockpos1.getY() + 0.05D, (double) blockpos1.getZ() + 0.5D, 0.0F, 0.0F);
				p_196358_1_.addFreshEntity(snowgolementity);
			} else {
				snowgolementity = new SnowmanEntity(DMAEntities.SNOWMAN.get(), p_196358_1_);
				snowgolementity.moveTo((double) blockpos1.getX() + 0.5D, (double) blockpos1.getY() + 0.05D, (double) blockpos1.getZ() + 0.5D, 0.0F, 0.0F);

				SnowmanEntity snowman = (SnowmanEntity) snowgolementity;

				snowman.finalizeSpawn((IServerWorld) p_196358_1_,
					p_196358_1_.getCurrentDifficultyAt(snowman.blockPosition()), SpawnReason.SPAWN_EGG, null, null);

				if (!snowman.equipItemIfPossible(new ItemStack(Items.CARVED_PUMPKIN))) {
					p_196358_1_.addFreshEntity(
						new ItemEntity(p_196358_1_,
							snowman.getX(), snowman.getY(), snowman.getZ(),
							new ItemStack(Items.CARVED_PUMPKIN)));
				}

				p_196358_1_.addFreshEntity(snowgolementity);
			}


			for (ServerPlayerEntity serverplayerentity : p_196358_1_.getEntitiesOfClass(ServerPlayerEntity.class, snowgolementity.getBoundingBox().inflate(5.0D))) {
				CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayerentity, snowgolementity);
			}

			for (int l = 0; l < this.getOrCreateSnowGolemFull().getHeight(); ++l) {
				CachedBlockInfo cachedblockinfo3 = blockpattern$patternhelper.getBlock(0, l, 0);
				p_196358_1_.blockUpdated(cachedblockinfo3.getPos(), Blocks.AIR);
			}
		}
	}
}
