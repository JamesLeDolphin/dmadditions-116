package com.jdolphin.dmadditions.mixin.other;

import com.jdolphin.dmadditions.advent.AdventTardis;
import com.jdolphin.dmadditions.advent.TimedUnlock;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.tardis.TardisInterior;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DMTardisRegistry.class)
public class DMTardisRegistryMixin{
	@Inject(at=@At("RETURN"), method="getInteriorRegistryAsList", remap = false, cancellable = true)
	private static void getInteriorRegistryAsList(CallbackInfoReturnable<List<TardisInterior>> cir){
		Logger logger = LogManager.getLogger();

		List<TardisInterior> list = cir.getReturnValue();
		if(list != null){
			list.removeIf(interior -> {
				String key = ((TranslationTextComponent) interior.getInteriorName()).getKey();

				AdventTardis adventTardis = AdventTardis.getByName(key);

				logger.debug("Checking if tardis interior has advent unlock: {}", key);
				if(adventTardis == null) return false;

				logger.debug("Interior {} unlocks at {}", key, adventTardis.date);

				return !TimedUnlock.advent(adventTardis.date);
			});

			cir.setReturnValue(list);
			cir.cancel();
		}else logger.error("list is null for some reason");

	}
}
