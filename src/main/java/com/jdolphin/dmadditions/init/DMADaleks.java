package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.entity.dalek.types.*;
import com.swdteam.common.entity.dalek.DalekType;
import com.swdteam.common.entity.dalek.IDalek;
import com.swdteam.common.entity.dalek.types.Classic;
import com.swdteam.common.init.DMDalekRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.swdteam.common.init.DMDalekRegistry.DALEK_TYPES;

public class DMADaleks {
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
	public static IDalek PROTO_DALEK;

	public static void init() {
		CANDYCANE = DMDalekRegistry.addDalek(DMADalekType.CANDYCANE, new CandycaneDalek("Candy Cane Dalek"), "lime_candycane_dalek");
		CANDYCANE.addChild("blue_candycane_dalek");
		CANDYCANE.addChild("red_candycane_dalek");
		CANDYCANE.addChild("orange_candycane_dalek");
		IRONSIDE = DMDalekRegistry.addDalek(DMADalekType.IRONSIDE, new IronsideDalekBase("Ironside Dalek"), "ironside_dalek");
		SNOW = DMDalekRegistry.addDalek(DMADalekType.SNOW, new CustomDalekBase("Snow Dalek"), "snow_dalek");
		SWD = DMDalekRegistry.addDalek(DMADalekType.SWD, new CustomDalekBase("SWD Team Dalek"), "1wtc_dalek");
		WAFFLE = DMDalekRegistry.addDalek(DMADalekType.CANDYCANE, new CustomDalekBase("Waffle Dalek"), "waffle_dalek");
		GINGERBREAD = DMDalekRegistry.addDalek(DMADalekType.CANDYCANE, new CustomDalekBase("Gingerbread Dalek"), "gingerbread_dalek");
		STEAMPUNK = DMDalekRegistry.addDalek(DMADalekType.STEAMPUNK, new SteampunkDalekBase("Steampunk Dalek"), "gold_steampunk_dalek");
		STEAMPUNK.addChild("gray_steampunk_dalek");
		DALEK_SANTA = DMDalekRegistry.addDalek(DMADalekType.SANTA, new DalekSantaBase("Dalek Santa"), "dalek_santa");
		GLASS = DMDalekRegistry.addDalek(DMADalekType.GLASS, new CustomDalekBase("Glass Dalek"), "glass_dalek_with_mutant");
		GLASS.addChild("glass_dalek_without_mutant");
		SESAME_STREET = DMDalekRegistry.addDalek(DMADalekType.SESAME_STREET, new CustomDalekBase("Sesame Street Dalek"), "sesame_street_dalek_red");
		SESAME_STREET.addChild("sesame_street_dalek_yellow");
		SESAME_STREET.addChild("sesame_street_dalek_emperor");

		PROTO_DALEK = DMDalekRegistry.addDalek(DalekType.CLASSIC, new Classic("Proto Dalek"), "proto_dalek");

	}
}
