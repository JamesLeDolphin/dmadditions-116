package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.entity.dalek.types.CandycaneDalek;
import com.jdolphin.dmadditions.entity.dalek.types.DMADalekType;
import com.jdolphin.dmadditions.entity.dalek.types.DalekSantaBase;
import com.jdolphin.dmadditions.entity.dalek.types.IronsideDalekBase;
import com.swdteam.common.entity.dalek.DalekType;
import com.swdteam.common.entity.dalek.IDalek;
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
	private static IDalek IRONSIDE;
	private static IDalek CANDYCANE;

	@Inject(method = "init()V", at = @At("HEAD"), remap = false)
	private static void init(CallbackInfo ci) {
		if (AdventUnlock.canAdventBeUnlocked(24)) {
			DALEK_SANTA = addDalek(DMADalekType.SANTA, new DalekSantaBase("Dalek Santa"), "dalek_santa");
		}
		if (AdventUnlock.canAdventBeUnlocked(2)) {
			CANDYCANE = addDalek(DMADalekType.CANDYCANE, new CandycaneDalek("Candy Cane Dalek"), "lime_candycane_dalek");
			CANDYCANE.addChild("blue_candycane_dalek");
			CANDYCANE.addChild("red_candycane_dalek");
			CANDYCANE.addChild("orange_candycane_dalek");
		}
			if (AdventUnlock.canAdventBeUnlocked(22)) {
			//IRONSIDE = addDalek(DMADalekType.IRONSIDE, new IronsideDalekBase("Ironside Dalek"), "ironside_dalek");
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
