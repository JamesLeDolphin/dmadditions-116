package com.jdolphin.dmadditions.entity.control;

import com.jdolphin.dmadditions.init.DMAEntities;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.Location;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tileentity.TardisTileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class DoorControl extends TardisControl {
	public DoorControl(EntityType<?> type, World world) {
		super(type, world);
	}

	public DoorControl(World world) {
		this(DMAEntities.DOOR_CONTROL.get(), world);
	}

	private void setDoors(TardisTileEntity tardis, boolean open) {
		tardis.setDoor(TardisDoor.BOTH, open, TardisTileEntity.DoorSource.INTERIOR);
		tardis.setDoor(TardisDoor.BOTH, open, TardisTileEntity.DoorSource.TARDIS);
	}

	public ActionResultType getEffect(PlayerEntity player) {
		if (!level.isClientSide() && Helper.isTardis(level)) {
			TardisData data = DMTardis.getTardisFromInteriorPos(this.blockPosition());
			if (data != null) {
				if (data.isLocked()) {
					player.displayClientMessage(new StringTextComponent(TextFormatting.YELLOW + DMTranslationKeys.TARDIS_IS_LOCKED.getString()), true);
					this.cooldown = 20;
					return ActionResultType.FAIL;
				}

				if (data.isInFlight()) {
					player.displayClientMessage(new StringTextComponent(TextFormatting.YELLOW + DMTranslationKeys.TARDIS_IN_FLIGHT.getString()), true);
					this.cooldown = 20;
					return ActionResultType.FAIL;
				}

				Location location = data.getCurrentLocation();
				if (level.getServer() != null) {
					ServerWorld serverWorld = level.getServer().getLevel(data.getCurrentLocation().dimensionWorldKey());
					if (serverWorld != null) {
						TileEntity tile = serverWorld.getBlockEntity(location.getPosition().toBlockPos());

						if (tile instanceof TardisTileEntity) {
							TardisTileEntity tardis = (TardisTileEntity) tile;
							boolean isOpen = tardis.doorOpenLeft || tardis.doorOpenRight;
							TranslationTextComponent text = new TranslationTextComponent(isOpen ? "notice.dmadditions.close" : "notice.dmadditions.open");
							this.cooldown = 20;
							setDoors(tardis, !isOpen);
							data.setDoorOpen(!isOpen);
							player.displayClientMessage(new StringTextComponent(TextFormatting.GREEN + text.getString()), true);
							return ActionResultType.SUCCESS;
						}
					}
				}
			}
		}
        return ActionResultType.FAIL;
    }
}
