package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.entity.dalek.IDalekEntityMixin;
import com.swdteam.common.entity.dalek.DalekEntity;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(DalekEntity.class)
public class DalekEntityMixin implements IDalekEntityMixin {
	private boolean party;
	private BlockPos jukebox;

	@Inject(method = "aiStep", at = @At("HEAD"), remap = false)
	public void aiStep(CallbackInfo ci) {
		if (this.jukebox == null
			|| !this.jukebox.closerThan(((DalekEntity) (Object) this).position(), 3.46D)
			|| !((DalekEntity) (Object) this).level.getBlockState(this.jukebox).is(Blocks.JUKEBOX)) {
			this.party = false;
			this.jukebox = null;
		}
	}

	public void setRecordPlayingNearby(BlockPos p_191987_1_, boolean p_191987_2_) {
		this.jukebox = p_191987_1_;
		this.party = p_191987_2_;
	}

	public boolean isPartyDalek() {
		return this.party;
	}
}
