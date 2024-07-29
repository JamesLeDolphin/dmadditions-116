package com.jdolphin.dmadditions.sonic;

import com.jdolphin.dmadditions.block.christmas.MagpieTelevisionBlock;
import com.swdteam.common.init.DMSonicRegistry;
import com.swdteam.common.sonic.SonicCategory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SonicMagpieTelevision implements DMSonicRegistry.ISonicInteraction {

	public SonicMagpieTelevision() {}

	@Override
	public void interact(World world, PlayerEntity playerEntity, ItemStack itemStack, Object o) {
		if (o instanceof BlockPos) {
			BlockPos p = (BlockPos) o;
			BlockState state = world.getBlockState(p);
			if(state.getBlock() instanceof MagpieTelevisionBlock) {
				world.setBlockAndUpdate(p, state.cycle(MagpieTelevisionBlock.ON));
			}
		}
	}

	@Override
	public int scanTime() {
		return 5;
	}

	@Override
	public boolean disableDefaultInteraction(World world, PlayerEntity playerEntity, ItemStack itemStack, Object o) {
		return false;
	}

	@Override
	public SonicCategory getCategory() {
		return SonicCategory.REDSTONE;
	}
}
