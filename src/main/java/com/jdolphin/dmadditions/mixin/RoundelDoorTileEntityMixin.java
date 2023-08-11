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