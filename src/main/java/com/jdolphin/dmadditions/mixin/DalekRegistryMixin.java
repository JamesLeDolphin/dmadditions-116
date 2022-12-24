package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.entity.CustomDalekBase;
import com.swdteam.common.entity.dalek.DalekType;
import com.swdteam.common.entity.dalek.IDalek;
import com.swdteam.common.entity.dalek.types.Chocolate;
import com.swdteam.common.entity.dalek.types.Skaro;
import com.swdteam.common.init.DMDalekRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.swdteam.common.init.DMDalekRegistry.*;

@Mixin(DMDalekRegistry.class)
public class DalekRegistryMixin {

	private static List<String> dalekList = new ArrayList();
	private static Map<String, IDalek> daleks = new HashMap();
	private static IDalek DALEK_SANTA;

	@Inject(method = "init()V", at = @At("HEAD"), remap = false)
	private static void init(CallbackInfo ci) {
		if (AdventUnlock.canAdventBeUnlocked(24)) {
			DALEK_SANTA = addDalek(DalekType.OTHER, new CustomDalekBase("Dalek Santa"), "dalek_santa");
		}
	}

	private static IDalek addDalek(DalekType type, IDalek dalek, String registryKey) {
		try {
			dalek.setRegistryName(type, registryKey);
		} catch (Exception var4) {
			var4.printStackTrace();
		}

		register(registryKey, dalek);
		return dalek;
	}

	private static void register(String registryKey, IDalek dalek) {
		daleks.put(registryKey, dalek);
		dalekList.add(registryKey);
		if (!DALEK_TYPES.containsKey(dalek.getType())) {
			DALEK_TYPES.put(dalek.getType(), new ArrayList());
		}

		((List)DALEK_TYPES.get(dalek.getType())).add(dalek.getID());
	}


}
