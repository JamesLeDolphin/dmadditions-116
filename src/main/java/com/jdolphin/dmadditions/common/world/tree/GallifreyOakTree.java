package com.jdolphin.dmadditions.common.world.tree;

import com.jdolphin.dmadditions.common.init.DMAConfiguredStructures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class GallifreyOakTree extends Tree {

	@Override
	protected @Nullable ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random random, boolean b) {
		return DMAConfiguredStructures.GALLIFREY_TREE;
	}
}
