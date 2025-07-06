package com.jdolphin.dmadditions.common.entity.control;

import com.jdolphin.dmadditions.common.tileentity.console.AbstractConsoleTileEntity;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.util.ChatUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class CoordInputControl extends AbstractControlType {
	protected final Direction.Axis axis;
	protected boolean positive;

	public CoordInputControl(ResourceLocation name, Direction.Axis axis, boolean positive) {
		super(name);
		this.axis = axis;
		this.positive = positive;
	}

	protected String formatIncrementMessage(Boolean add, Direction.Axis axis, int increment, TardisFlightData flightData) {
		return (add ? "Added " : "Subtracted ") + increment + (add ? " to " : " from ") + axis.toString().toUpperCase() + " (" + flightData.getPos(axis) + ")";
	}

	@Override
	public void onPress(ServerWorld level, PlayerEntity player, BlockPos pos, TardisData data, TardisControl control) {
		AbstractConsoleTileEntity consoleTile = control.getConsole();
		int increment = consoleTile.getControlIncrement();
		TardisFlightData flightData = TardisFlightPool.getFlightData(data);
		if (flightData != null) {
			if (player.isShiftKeyDown()) {
				flightData.incrementPos(-increment, axis);
				ChatUtil.sendMessageToPlayer(player, this.formatIncrementMessage(false, axis, -increment, flightData), ChatUtil.MessageType.STATUS_BAR);
			} else {
				flightData.incrementPos(increment, axis);
				ChatUtil.sendMessageToPlayer(player, this.formatIncrementMessage(true, axis, increment, flightData), ChatUtil.MessageType.STATUS_BAR);
			}
		}
	}
}
