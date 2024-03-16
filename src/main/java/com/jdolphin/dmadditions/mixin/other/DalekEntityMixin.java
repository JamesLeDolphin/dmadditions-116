package com.jdolphin.dmadditions.mixin.other;

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

	protected DalekEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
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
	public void setRecordPlayingNearby(BlockPos blockPos, boolean b) {
		this.jukebox = blockPos;
		this.party = b;
	}

	public boolean isPartyDalek() {
		return this.party;
	}
}
