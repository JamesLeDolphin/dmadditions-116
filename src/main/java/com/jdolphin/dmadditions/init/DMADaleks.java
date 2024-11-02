package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.entity.dalek.types.*;
import com.swdteam.common.entity.dalek.DalekType;
import com.swdteam.common.entity.dalek.IDalek;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.swdteam.common.init.DMDalekRegistry.DALEK_TYPES;

public class DMADaleks {
	private static List<String> dmaDalekList = new ArrayList<>();
	private static Map<String, IDalek> dmaDaleks = new HashMap<>();

	public static IDalek DALEK_SANTA;
	public static IDalek IRONSIDE;
	public static IDalek CANDYCANE;
	public static IDalek STORM;
	public static IDalek WAFFLE;
	public static IDalek GINGERBREAD;
	public static IDalek SNOW;
	public static IDalek STEAMPUNK;
	public static IDalek SWD;
	public static IDalek GLASS;
	public static IDalek SESAME_STREET;
	public static IDalek SUPREME;

	public static void init(List<String> dalekList, Map<String, IDalek> daleks) {
		CANDYCANE = addDalek(DMADalekType.CANDYCANE, new CandycaneDalek("Candy Cane Dalek"), "lime_candycane_dalek");
		CANDYCANE.addChild("blue_candycane_dalek");
		CANDYCANE.addChild("red_candycane_dalek");
		CANDYCANE.addChild("orange_candycane_dalek");
		IRONSIDE = addDalek(DMADalekType.IRONSIDE, new IronsideDalekBase("Ironside Dalek"), "ironside_dalek");
		SNOW = addDalek(DMADalekType.SNOW, new CustomDalekBase("Snow Dalek"), "snow_dalek");
		SWD = addDalek(DMADalekType.SWD, new CustomDalekBase("SWD Team Dalek"), "1wtc_dalek");
		WAFFLE = addDalek(DMADalekType.CANDYCANE, new CustomDalekBase("Waffle Dalek"), "waffle_dalek");
		GINGERBREAD = addDalek(DMADalekType.CANDYCANE, new CustomDalekBase("Gingerbread Dalek"), "gingerbread_dalek");
		STEAMPUNK = addDalek(DMADalekType.STEAMPUNK, new SteampunkDalekBase("Steampunk Dalek"), "gold_steampunk_dalek");
		STEAMPUNK.addChild("gray_steampunk_dalek");
		DALEK_SANTA = addDalek(DMADalekType.SANTA, new DalekSantaBase("Dalek Santa"), "dalek_santa");
		GLASS = addDalek(DMADalekType.GLASS, new CustomDalekBase("Glass Dalek"), "glass_dalek_with_mutant");
		GLASS.addChild("glass_dalek_without_mutant");
		SESAME_STREET = addDalek(DMADalekType.SESAME_STREET, new CustomDalekBase("Sesame Street Dalek"), "sesame_street_dalek_red");
		SESAME_STREET.addChild("sesame_street_dalek_yellow");
		SESAME_STREET.addChild("sesame_street_dalek_emperor");

		if (AdventUnlock.unlockAt(15)) {
			SUPREME = addDalek(DalekType.TIME_WAR, new CustomDalekBase("Supreme Dalek"), "red_supreme_dalek");
			SUPREME.addChild("blue_supreme_dalek");
		}

		dalekList.addAll(dmaDalekList);
		daleks.putAll(dmaDaleks);
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
		dmaDaleks.put(registryKey, dalek);
		dmaDalekList.add(registryKey);
		if (!DALEK_TYPES.containsKey(dalek.getType())) {
			DALEK_TYPES.put(dalek.getType(), new ArrayList<>());
		}

		DALEK_TYPES.get(dalek.getType()).add(dalek.getID());
	}
}
