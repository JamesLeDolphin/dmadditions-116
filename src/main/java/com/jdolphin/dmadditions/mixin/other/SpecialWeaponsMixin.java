package com.jdolphin.dmadditions.mixin.other;

import com.jdolphin.dmadditions.config.DMACommonConfig;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.dalek.types.SpecialWeapons;
import com.swdteam.common.init.DMProjectiles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(SpecialWeapons.class)
public class SpecialWeaponsMixin {


	@Inject(method = "getLaser(Lcom/swdteam/common/entity/dalek/DalekEntity;)Lcom/swdteam/common/init/DMProjectiles$Laser;", at = @At("HEAD"), remap = false, cancellable = true)
	public void dma_getLaser(DalekEntity dalek, CallbackInfoReturnable<DMProjectiles.Laser> cir) {
		if (DMACommonConfig.disable_swd_laser.get()) {
			cir.setReturnValue(DMProjectiles.TIME_WAR_SWD_LASER);
		}
		else cir.setReturnValue(DMProjectiles.EXPLOSIVE_LASER);
	}
}
