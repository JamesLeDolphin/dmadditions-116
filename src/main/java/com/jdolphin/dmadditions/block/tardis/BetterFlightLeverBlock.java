package com.jdolphin.dmadditions.block.tardis;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.util.Helper;
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

public class BetterFlightLeverBlock extends BetterTardisLeverBlock {
	public BetterFlightLeverBlock(Properties properties) {
		super(properties);
	}

	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult blockRayTraceResult) {
		if (handIn == Hand.MAIN_HAND && !worldIn.isClientSide) {
			if (Helper.isTardis(worldIn)) {
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
					worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), DMSoundEvents.TARDIS_DEMAT.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
					this.switchLever(state, worldIn, pos);
				}
			} else this.switchLever(state, worldIn, pos);

			if (DmAdditions.hasNTM()) {
				if (net.tardis.mod.helper.WorldHelper.areDimensionTypesSame(worldIn, net.tardis.mod.world.dimensions.TDimensions.DimensionTypes.TARDIS_TYPE)) {
					net.tardis.mod.helper.TardisHelper.getConsole(worldIn.getServer(), worldIn).ifPresent(tile -> {
						tile.getControl(net.tardis.mod.controls.HandbrakeControl.class).ifPresent(brake -> {
							if (brake.isFree()) {
								if (!tile.isInFlight() || tile.isLanding()) {
									tile.getControl(net.tardis.mod.controls.ThrottleControl.class).ifPresent(sys -> {
										sys.setAmount(1.0f);
									});
									tile.takeoff();
									tile.getSubsystem(net.tardis.mod.subsystem.StabilizerSubsystem.class).ifPresent(sys -> {
										if (!sys.isControlActivated()) {
											sys.setControlActivated(true);
										}
									});
								}
							}else this.switchLever(state, worldIn, pos);
						});
						if (tile.isInFlight()) {
							tile.land();
						}
					});
					this.switchLever(state, worldIn, pos);
				}
				else {
					this.switchLever(state, worldIn, pos);
				}
			}
		}
		this.updateNeighbours(state, worldIn, pos);
		return ActionResultType.CONSUME;
	}

}
