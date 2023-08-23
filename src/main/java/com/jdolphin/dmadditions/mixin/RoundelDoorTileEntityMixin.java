// Copyright 2023 Bug1312 (bug@bug1312.com)
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.

package com.jdolphin.dmadditions.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tileentity.DMTileEntityBase;
import com.swdteam.common.tileentity.tardis.RoundelDoorTileEntity;

import net.minecraft.tileentity.TileEntityType;

@Mixin(RoundelDoorTileEntity.class)
public abstract class RoundelDoorTileEntityMixin extends DMTileEntityBase {

	public RoundelDoorTileEntityMixin(TileEntityType<?> tileEntityTypeIn) { super(tileEntityTypeIn); }
	private RoundelDoorTileEntity _this = ((RoundelDoorTileEntity)(Object)this);

	public boolean open = false;

	@Inject(at = @At("TAIL"), method = "tick")
	public void tick(CallbackInfo ci) {
		if (!this.level.isClientSide() && _this.isMainDoor()) {
			boolean doorsOpen = _this.isOpen();
			TardisData tardisData = DMTardis.getTardisFromInteriorPos(getBlockPos());

			if (tardisData != null && doorsOpen != open) tardisData.setDoorOpen(doorsOpen);
		}
	}

}