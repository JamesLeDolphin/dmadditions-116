package com.jdolphin.dmadditions.block;

import com.jdolphin.dmadditions.DmAdditions;
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
import net.tardis.mod.helper.TardisHelper;
import net.tardis.mod.helper.WorldHelper;
import net.tardis.mod.subsystem.StabilizerSubsystem;
import net.tardis.mod.tileentities.ConsoleTile;
import net.tardis.mod.world.dimensions.TDimensions;

public class BetterFlightLeverBlock extends BetterTardisLeverBlock {
	public BetterFlightLeverBlock(Properties properties) {
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
					worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), DMSoundEvents.TARDIS_DEMAT.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
					this.switchLever(state, worldIn, pos);
				}
			}
			if (DmAdditions.hasNTM()) {
				if (WorldHelper.areDimensionTypesSame(worldIn, TDimensions.DimensionTypes.TARDIS_TYPE)) {
					TardisHelper.getConsole(worldIn.getServer(), worldIn).ifPresent(tile -> {
						if (!tile.isInFlight() || tile.isLanding()) {
							tile.takeoff();
							tile.getSubsystem(StabilizerSubsystem.class).ifPresent(sys -> {
								if (!sys.isControlActivated()) {
								sys.setControlActivated(true);
							}});
						}
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
