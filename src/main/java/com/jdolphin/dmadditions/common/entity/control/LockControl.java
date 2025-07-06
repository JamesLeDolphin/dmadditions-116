package com.jdolphin.dmadditions.common.entity.control;

import com.swdteam.common.tardis.Location;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tileentity.TardisTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class LockControl extends AbstractControlType {

	public LockControl(ResourceLocation name) {
		super(name);
	}

	@Override
	public void onPress(ServerWorld level, PlayerEntity player, BlockPos pos, TardisData data, TardisControl control) {
		Location location = data.getCurrentLocation();
		data.setLocked(!data.isLocked());
		data.save();
		player.displayClientMessage(new TranslationTextComponent(data.isLocked() ?
			"notice.dmadditions.lock" : "notice.dmadditions.unlock").withStyle(TextFormatting.GREEN), true);
		if (data.isLocked()) {
			level.getServer();
			ServerWorld serverWorld = level.getServer().getLevel(data.getCurrentLocation().dimensionWorldKey());
			if (serverWorld != null) {
				TileEntity tile = serverWorld.getBlockEntity(location.getPosition().toBlockPos());

				if (tile instanceof TardisTileEntity) {
					TardisTileEntity tardis = (TardisTileEntity) tile;
					setDoors(tardis, false);
				}
			}
		}
	}
}
