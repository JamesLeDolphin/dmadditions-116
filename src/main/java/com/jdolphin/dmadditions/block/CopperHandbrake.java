package com.jdolphin.dmadditions.block;

import com.jdolphin.dmadditions.init.DMASoundEvents;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.util.ChatUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import static com.swdteam.common.block.AbstractLeverBlock.PULLED;

public class CopperHandbrake extends BetterFlightLeverBlock {
	public CopperHandbrake(Properties properties) {
		super(properties);
	}

	public void switchLever(BlockState state, World worldIn, BlockPos pos) {
		worldIn.setBlockAndUpdate(pos, (BlockState)state.setValue(POWERED, !(Boolean)state.getValue(POWERED)));
		worldIn.playSound((PlayerEntity)null, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (SoundEvent)DMASoundEvents.COPPER_HANDBRAKE.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
	}
}
