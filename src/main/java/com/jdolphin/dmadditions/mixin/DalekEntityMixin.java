package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.entity.dalek.IDalekEntityMixin;
import com.swdteam.common.entity.dalek.DalekEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(DalekEntity.class)
public abstract class DalekEntityMixin extends LivingEntity implements IDalekEntityMixin {
	private boolean party;
	private BlockPos jukebox;

	protected DalekEntityMixin(EntityType<? extends LivingEntity> p_i48577_1_, World p_i48577_2_) {
		super(p_i48577_1_, p_i48577_2_);
	}

	@Inject(method = "aiStep", at = @At("TAIL"))
	public void aiStep(CallbackInfo ci) {
		if (!this.level.isClientSide) return;

		if (this.jukebox == null
			|| !this.jukebox.closerThan(this.position(), 5.0D)
			|| !this.level.getBlockState(this.jukebox).is(Blocks.JUKEBOX)) {

			this.party = false;
			this.jukebox = null;
		}
	}

	@Override
	public void setRecordPlayingNearby(BlockPos p_191987_1_, boolean p_191987_2_) {
		this.jukebox = p_191987_1_;
		this.party = p_191987_2_;
	}

	public boolean isPartyDalek() {
		return this.party;
	}
}
