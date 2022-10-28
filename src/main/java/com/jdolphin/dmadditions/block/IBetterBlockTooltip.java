package com.jdolphin.dmadditions.block;

import com.swdteam.common.block.IBlockTooltip;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public interface IBetterBlockTooltip extends IBlockTooltip {
	@Override
	@Nullable
	default ITextComponent getName(BlockState blockState, BlockPos blockPos, Vector3d vector3d, PlayerEntity playerEntity){
		String key = getTooltipTranslationKey(blockState, blockPos, vector3d, playerEntity);
		if(key == null) return blockState.getBlock().getName();

		ResourceLocation resourceLocation = blockState.getBlock().getRegistryName();

		assert resourceLocation != null;
		return new TranslationTextComponent(String.format("tooltip.block.%s.%s.%s", resourceLocation.getNamespace(), resourceLocation.getPath(), key));
	}


	default String getTooltipTranslationKey(BlockState blockState, BlockPos blockPos, Vector3d vector3d, PlayerEntity playerEntity){
		return null;
	}
}
