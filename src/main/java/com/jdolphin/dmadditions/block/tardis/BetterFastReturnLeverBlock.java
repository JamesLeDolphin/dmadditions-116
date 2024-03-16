package com.jdolphin.dmadditions.block.tardis;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.util.ChatUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
public class BetterFastReturnLeverBlock extends BetterTardisLeverBlock {
	public BetterFastReturnLeverBlock(Properties properties) {
		super(properties);
	}

	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult blockRayTraceResult) {
		if (handIn == Hand.MAIN_HAND) {
			worldIn.setBlockAndUpdate(pos, state.setValue(POWERED, !(Boolean) state.getValue(POWERED)));
			worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), DMSoundEvents.TARDIS_CONTROLS_LEVER.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (Helper.isTardis(worldIn) && !worldIn.isClientSide) {
				TardisData data = DMTardis.getTardisFromInteriorPos(pos);
				if (data != null && data.getPreviousLocation() != null) {
					TardisFlightPool.updateFlight(data, data.getPreviousLocation());
				}

				ChatUtil.sendCompletedMsg(player, DMTranslationKeys.TARDIS_FAST_RETURN_SET, ChatUtil.MessageType.STATUS_BAR);
			}

			if (DmAdditions.hasNTM()) {
				if (net.tardis.mod.helper.WorldHelper.areDimensionTypesSame(worldIn, net.tardis.mod.world.dimensions.TDimensions.DimensionTypes.TARDIS_TYPE)) {
					net.tardis.mod.helper.TardisHelper.getConsoleInWorld(worldIn).ifPresent(tile -> {
						net.tardis.mod.misc.SpaceTimeCoord coord = tile.getReturnLocation();
						RegistryKey<World> worldKey = RegistryKey.create(Registry.DIMENSION_REGISTRY, coord.getDimRL());
						tile.setDestination(worldKey, coord.getPos());
						tile.setExteriorFacingDirection(coord.getFacing());
					});
				}
			}


			this.updateNeighbours(state, worldIn, pos);

		} return ActionResultType.CONSUME;
	}
}
