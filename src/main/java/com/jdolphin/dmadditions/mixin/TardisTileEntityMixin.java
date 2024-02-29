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

import com.jdolphin.dmadditions.block.tardis.ITardisDMAActions;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tileentity.ExtraRotationTileEntityBase;
import com.swdteam.common.tileentity.TardisTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(TardisTileEntity.class)
public abstract class TardisTileEntityMixin extends ExtraRotationTileEntityBase implements ITickableTileEntity, ITardisDMAActions {

	@Shadow(remap = false)
	boolean demat;

	@Shadow(remap = false)

	public abstract CompoundNBT save(CompoundNBT compound);

	@Shadow(remap = false)
	public abstract void sendUpdates();

	@Shadow(remap = false)
	public TardisData tardisData;

	public TardisTileEntityMixin(TileEntityType<?> tileEntityTypeIn) { super(tileEntityTypeIn); }
	private TardisTileEntity _this = ((TardisTileEntity)(Object)this);
	public boolean open = false;

	@Unique private String FORCEFIELD = "Forcefield";
	@Unique private String INVISIBLE = "Invisible";
	@Unique private boolean invisible;
	@Unique private boolean ff_active;

	@Inject(at = @At("TAIL"), method = "tick")
	public void tick(CallbackInfo ci) {
		if (!this.level.isClientSide()) {
			boolean exteriorOpen = (_this.doorOpenLeft || _this.doorOpenRight);
			if (_this.tardisData != null && exteriorOpen != open) {
				this.open = exteriorOpen;
				_this.tardisData.setDoorOpen(exteriorOpen);
			}
			if (this.demat) this.sendUpdates();
			if (isForcefieldActive()) {
				tardisData.save();
				double radius = 3.0D;
				Vector3d blockPosVec = Vector3d.atBottomCenterOf(this.worldPosition);
				List<Entity> entities = this.level.getEntitiesOfClass(Entity.class, new AxisAlignedBB(blockPosVec, blockPosVec).inflate(radius), EntityPredicates.NO_SPECTATORS);
				for (Entity entity : entities) {
					if (entity instanceof ItemEntity) continue;
					if (entity instanceof ExperienceOrbEntity) continue;
					if (entity instanceof PlayerEntity) continue;

					double distance = blockPosVec.distanceTo(entity.position());
					double d = radius / distance / 10;
					Vector3d deltaMovement = entity.position().subtract(blockPosVec).multiply(d, d, d);
					entity.setDeltaMovement(deltaMovement);
				}
			}
		}
	}
	public boolean isForcefieldActive() {
		return ff_active;
	};

	@Override
	public void setForcefieldActive(boolean active) {
		this.ff_active = active;
	}

	@Override
	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
		CompoundNBT tag = this.getUpdateTag();
		tag.putBoolean(INVISIBLE, invisible);
		this.sendUpdates();
	}

	public boolean isInvisible() {
		return invisible;
	};



	@Inject(at=@At("TAIL"), method = "save", cancellable = true)
	public void save(CompoundNBT compound, CallbackInfoReturnable<CompoundNBT> cir){
		compound.putBoolean(INVISIBLE, invisible);
		compound.putBoolean(FORCEFIELD, ff_active);
		cir.setReturnValue(super.save(compound));

	}

	@Inject(at=@At("TAIL"), method = "load")
	public void load(BlockState blockstate, CompoundNBT compound, CallbackInfo ci){
		if (compound.contains(INVISIBLE)) {
			invisible = compound.getBoolean(INVISIBLE);
		}
		if (compound.contains(FORCEFIELD)) {
			ff_active = compound.getBoolean(FORCEFIELD);
		}
	}
}