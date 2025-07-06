package com.jdolphin.dmadditions.common.entity.control;

import com.jdolphin.dmadditions.common.tileentity.console.AbstractConsoleTileEntity;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.util.ChatUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class CoordIncrementControl extends AbstractControlType {

	public CoordIncrementControl(ResourceLocation name) {
		super(name);
	}

	@Override
	public void onPress(ServerWorld level, PlayerEntity player, BlockPos pos, TardisData data, TardisControl control) {
		AbstractConsoleTileEntity tile = control.getConsole();
		int increment = tile.getControlIncrement();
		if (player.isShiftKeyDown()) {
			if (increment == 1) {
				increment = 10000;
			} else {
				increment /= 10;
			}
		} else if (increment == 10000) {
			increment = 1;
		} else {
			increment *= 10;
		}
		tile.setIncrement(increment);
		ChatUtil.sendMessageToPlayer(player, "Coordinate Increment: " + increment, ChatUtil.MessageType.STATUS_BAR);
	}
}
