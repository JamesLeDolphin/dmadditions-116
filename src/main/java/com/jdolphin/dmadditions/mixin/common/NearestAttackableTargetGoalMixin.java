package com.jdolphin.dmadditions.mixin.common;

import com.jdolphin.dmadditions.common.entity.ZygonEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.IronGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NearestAttackableTargetGoal.class)
public abstract class NearestAttackableTargetGoalMixin extends TargetGoal {

	public NearestAttackableTargetGoalMixin(MobEntity p_i50308_1_, boolean p_i50308_2_) {
		super(p_i50308_1_, p_i50308_2_);
	}

	@Shadow
	protected LivingEntity target;

	@Inject(method = "canUse", cancellable = true, at = @At("TAIL"))
	public void canUse(CallbackInfoReturnable<Boolean> cir) {
		if (target instanceof ZygonEntity && mob instanceof IronGolemEntity) {
			ZygonEntity zygonEntity = (ZygonEntity) target;
			if (zygonEntity.isDisguised()) { // sus imposter
				cir.setReturnValue(false);
				cir.cancel();
			}
		}
	}
}
