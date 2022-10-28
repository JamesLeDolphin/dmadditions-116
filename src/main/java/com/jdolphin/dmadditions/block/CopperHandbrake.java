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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class CopperHandbrake extends BetterFlightLeverBlock{
	public CopperHandbrake(Properties properties) {
		super(properties);
	}
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
		if (handIn == Hand.MAIN_HAND && !worldIn.isClientSide) {
			if (worldIn.dimension().equals(DMDimensions.TARDIS)) {
				TardisData data = DMTardis.getTardisFromInteriorPos(pos);
				if (data.isInFlight()) {
					if (data.timeLeft() == 0.0D) {
						if (TardisActionList.remat(player, worldIn, data)) {
							worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), DMSoundEvents.TARDIS_REMAT.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
							this.switchLever(state, worldIn, pos);
						}
					} else {
						int seconds = (int) data.timeLeft();
						String s = seconds + "s";
						ChatUtil.sendError(player, new TranslationTextComponent("notice.dalekmod.tardis.traveling", new StringTextComponent(s)), ChatUtil.MessageType.CHAT);
					}
				} else if (TardisActionList.demat(player, worldIn, data)) {
					worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), DMASoundEvents.COPPER_HANDBRAKE.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
					this.switchLever(state, worldIn, pos);
				}
			} else {
				this.switchLever(state, worldIn, pos);
			}
		}

		return ActionResultType.CONSUME;
	}
}
