package com.jdolphin.dmadditions.mixin.common;

import com.jdolphin.dmadditions.common.advent.AdventTardis;
import com.jdolphin.dmadditions.common.advent.TimedUnlock;
import com.swdteam.common.tardis.Data;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Data.class)
public abstract class DataMixin {
	@Inject(method = "isUnlockable", at = @At("RETURN"), remap = false, cancellable = true)
	private void isUnlockable(CallbackInfoReturnable<Boolean> cir) {
		try {
			Data data = (Data) (Object) this;

			String key = ((TranslationTextComponent) data.getExteriorName()).getKey();

			Logger logger = LogManager.getLogger();
			logger.debug("Checking if tardis has advent unlock: {}", key);

			AdventTardis adventTardis = AdventTardis.getByName(key);

			if (adventTardis == null)
				return;

			logger.debug("Tardis {} unlocks at advent day {}", key, adventTardis.date);

			if (!TimedUnlock.advent(adventTardis.date)) {
				cir.setReturnValue(false);
				cir.cancel();
			}

		} catch (ClassCastException ignored) {
		}
	}

}
