package com.jdolphin.dmadditions.common.entity.control;

import com.jdolphin.dmadditions.common.util.Helper;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.util.ChatUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class FlightControl extends AbstractControlType {

	public FlightControl(ResourceLocation name) {
		super(name);
	}

	@Override
	public void onPress(ServerWorld level, PlayerEntity player, BlockPos pos, TardisData data, TardisControl control) {
		if (data.isInFlight()) {
			if (data.timeLeft() == 0.0D) {
				if (TardisActionList.remat(player, level, data)) {
					Helper.playSound(level, pos, DMSoundEvents.TARDIS_REMAT.get(), SoundCategory.BLOCKS);
					Helper.playSound(level, pos, DMSoundEvents.TARDIS_CONTROLS_LEVER.get(), SoundCategory.BLOCKS);
				}
			} else {
				int seconds = (int) data.timeLeft();
				String s = seconds + "s";
				ChatUtil.sendError(player, new TranslationTextComponent("notice.dalekmod.tardis.traveling", new StringTextComponent(s)), ChatUtil.MessageType.CHAT);
			}
		} else if (TardisActionList.demat(player, level, data)) {
			Helper.playSound(level, pos, DMSoundEvents.TARDIS_DEMAT.get(), SoundCategory.BLOCKS);
			Helper.playSound(level, pos, DMSoundEvents.TARDIS_CONTROLS_LEVER.get(), SoundCategory.BLOCKS);
		}
	}
}
