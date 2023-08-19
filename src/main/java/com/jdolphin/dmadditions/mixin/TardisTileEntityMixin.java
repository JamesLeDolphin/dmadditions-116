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

import com.swdteam.common.tileentity.ExtraRotationTileEntityBase;
import com.swdteam.common.tileentity.TardisTileEntity;

import net.minecraft.tileentity.TileEntityType;

@Mixin(TardisTileEntity.class)
public abstract class TardisTileEntityMixin extends ExtraRotationTileEntityBase {

	public TardisTileEntityMixin(TileEntityType<?> tileEntityTypeIn) { super(tileEntityTypeIn); }
	private TardisTileEntity _this = ((TardisTileEntity)(Object)this);

	public boolean open = false;

	@Inject(at = @At("TAIL"), method = "tick")
	public void tick(CallbackInfo ci) {
		if (!this.level.isClientSide()) {
			boolean exteriorOpen = (_this.doorOpenLeft || _this.doorOpenRight);
			if (_this.tardisData != null && exteriorOpen != open) {
				this.open = exteriorOpen;
				_this.tardisData.setDoorOpen(exteriorOpen);
			}
		}
	}
}