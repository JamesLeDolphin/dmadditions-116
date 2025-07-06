package com.jdolphin.dmadditions.common.entity.control;

import com.swdteam.common.tardis.Location;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.data.TardisFlightPool;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class FastReturnControl extends AbstractControlType {

	public FastReturnControl(ResourceLocation name) {
		super(name);
	}

	@Override
	public void onPress(ServerWorld level, PlayerEntity player, BlockPos pos, TardisData data, TardisControl control) {
		Location location = data.getPreviousLocation();
		TardisFlightPool.updateFlight(data, location);
	}
}
