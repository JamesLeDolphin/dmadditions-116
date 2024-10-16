package com.jdolphin.dmadditions.block;

import com.swdteam.common.block.IBlockTooltip;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.jetbrains.annotations.Nullable;

public interface IBetterBlockTooltip extends IBlockTooltip {

	@Override
	default ITextComponent getName(BlockState blockState, BlockPos blockPos, Vector3d vector3d, PlayerEntity playerEntity) {
		String key = getTooltipTranslationKey(blockState, blockPos, vector3d, playerEntity);
		if (key == null) return blockState.getBlock().getName();

		return getName(blockState.getBlock(), key);
	}

	static ITextComponent getName(Block block, @Nullable String key) {
		ResourceLocation resourceLocation = block.getRegistryName();
		if (key == null) return block.getName();

		assert resourceLocation != null;
		return new TranslationTextComponent(String.format("tooltip.block.%s.%s.%s", resourceLocation.getNamespace(), resourceLocation.getPath(), key));
	}

	// TODO: rename this method
	default String getTooltipTranslationKey(BlockState blockState, BlockPos blockPos, Vector3d vector3d, PlayerEntity playerEntity) {
		return null;
	}
}
