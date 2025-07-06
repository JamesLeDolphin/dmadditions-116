package com.jdolphin.dmadditions.common.entity.control;

import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.Location;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tileentity.TardisTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class DoorControl extends AbstractControlType {

	public DoorControl(ResourceLocation name) {
		super(name);
	}

	@Override
	public void onPress(ServerWorld level, PlayerEntity player, BlockPos pos, TardisData data, TardisControl control) {
		Location location = data.getCurrentLocation();


		if (!data.isInFlight()) {
			if (!data.isLocked()) {
				level.getServer();
				ServerWorld serverWorld = level.getServer().getLevel(data.getCurrentLocation().dimensionWorldKey());
				if (serverWorld != null) {
					TileEntity tile = serverWorld.getBlockEntity(location.getPosition().toBlockPos());

					if (tile instanceof TardisTileEntity) {
						TardisTileEntity tardis = (TardisTileEntity) tile;
						boolean isOpen = tardis.doorOpenLeft || tardis.doorOpenRight;
						TranslationTextComponent text = new TranslationTextComponent(isOpen ? "notice.dmadditions.close" : "notice.dmadditions.open");
						setDoors(tardis, !isOpen);
						data.setDoorOpen(!isOpen);
						player.displayClientMessage(new StringTextComponent(TextFormatting.GREEN + text.getString()), true);
					}
				}
			} else player.displayClientMessage(new StringTextComponent(TextFormatting.YELLOW + DMTranslationKeys.TARDIS_IS_LOCKED.getString()), true);
		} else player.displayClientMessage(new StringTextComponent(TextFormatting.YELLOW + DMTranslationKeys.TARDIS_IN_FLIGHT.getString()), true);
	}
}

