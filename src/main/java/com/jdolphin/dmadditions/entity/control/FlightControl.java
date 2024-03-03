package com.jdolphin.dmadditions.entity.control;

import com.jdolphin.dmadditions.init.DMAEntities;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.util.ChatUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class FlightControl extends TardisControl {
	public FlightControl(EntityType<?> type, World world) {
		super(type, world);
	}

	public FlightControl(World world) {
		this(DMAEntities.FLIGHT_CONTROL.get(), world);
	}

	@Override
	public ActionResultType getEffect(PlayerEntity player) {
		World level = this.level;
		BlockPos pos = Helper.vec3ToBlockPos(this.position());
		if (!level.isClientSide()) {
			if (Helper.isTardis(level)) {
				TardisData data = DMTardis.getTardisFromInteriorPos(pos);
				if (data.isInFlight()) {
					if (data.timeLeft() == 0.0D) {
						if (TardisActionList.remat(player, level, data)) {
							Helper.playSound(level, pos, DMSoundEvents.TARDIS_REMAT.get(), SoundCategory.BLOCKS);
							Helper.playSound(level, pos, DMSoundEvents.TARDIS_CONTROLS_LEVER.get(), SoundCategory.BLOCKS);
							this.cooldown = 20;
							return ActionResultType.SUCCESS;
						}
					} else {
						int seconds = (int) data.timeLeft();
						String s = seconds + "s";
						ChatUtil.sendError(player, new TranslationTextComponent("notice.dalekmod.tardis.traveling", new StringTextComponent(s)), ChatUtil.MessageType.CHAT);
						return ActionResultType.FAIL;
					}
				} else if (TardisActionList.demat(player, level, data)) {
					Helper.playSound(level, pos, DMSoundEvents.TARDIS_DEMAT.get(), SoundCategory.BLOCKS);
					Helper.playSound(level, pos, DMSoundEvents.TARDIS_CONTROLS_LEVER.get(), SoundCategory.BLOCKS);
					this.cooldown = 20;
					return ActionResultType.SUCCESS;
				}
			} else ChatUtil.sendMessageToPlayer(player, new TranslationTextComponent("entity.dmadditions.console.fail.dim"), ChatUtil.MessageType.CHAT);
			return ActionResultType.PASS;
		}
		return ActionResultType.PASS;
	}
}
