package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.entity.dalek.types.CandycaneDalek;
import com.jdolphin.dmadditions.entity.dalek.types.DMADalekType;
import com.jdolphin.dmadditions.entity.dalek.types.DalekSantaBase;
import com.swdteam.common.entity.dalek.DalekType;
import com.swdteam.common.entity.dalek.IDalek;
import com.swdteam.common.init.DMDalekRegistry;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import javax.annotation.CheckForNull;
import java.util.*;

import static com.swdteam.common.init.DMDalekRegistry.SKARO_DALEK;

public class DMADalekRegistry {
	public static Map<DalekType, List<String>> DALEK_TYPES = new HashMap();
	public static Map<ResourceLocation, List<IDalek>> DALEK_SPAWNS = new HashMap();
	private static List<String> dalekList = new ArrayList();
	private static Map<String, IDalek> daleks = new HashMap();
	public static IDalek DALEK_SANTA;
	public static IDalek IRONSIDE;
	public static IDalek CANDYCANE;

	public static void init() {

		if (AdventUnlock.unlockAt(24)) {
			DALEK_SANTA = addDalek(DMADalekType.SANTA, new DalekSantaBase("Dalek Santa"), "dalek_santa");
		}
		if (AdventUnlock.unlockAt(2)) {
			CANDYCANE = addDalek(DMADalekType.CANDYCANE, new CandycaneDalek("Candy Cane Dalek"), "lime_candycane_dalek");
			CANDYCANE.addChild("blue_candycane_dalek");
			CANDYCANE.addChild("red_candycane_dalek");
			CANDYCANE.addChild("orange_candycane_dalek");
		}

		if (AdventUnlock.unlockAt(22)) {
			//IRONSIDE = addDalek(DMADalekType.IRONSIDE, new IronsideDalekBase("Ironside Dalek"), "ironside_dalek");
		}
	}

	public DMADalekRegistry() {
	}


	public static void addDalek(IDalek dalek) {
		if (dalek.getID() != null) {
			register(dalek.getID(), dalek);
		}

	}

	private static IDalek addDalek(IDalek dalek, String registryKey) {
		return addDalek(DalekType.OTHER, dalek, registryKey);
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

	public static IDalek getRandomDalek(Random rand, DalekType type) {
		List<String> dalekList = (List)DALEK_TYPES.get(type);
		return getDalek((String)dalekList.get(rand.nextInt(dalekList.size())));
	}

	public static Map<String, IDalek> getDaleks() {
		return daleks;
	}

	@CheckForNull
	public static IDalek getDalekForBiome(World world, Biome biomeIn) {
		if (DALEK_SPAWNS.containsKey(biomeIn.getRegistryName())) {
			List<IDalek> daleks = (List)DALEK_SPAWNS.get(biomeIn.getRegistryName());
			IDalek dalek = (IDalek)daleks.get(world.random.nextInt(daleks.size()));
			if (dalek.getChildren() != null && dalek.getChildren().size() > 0) {
				return world.random.nextBoolean() ? dalek : (IDalek)dalek.getChildren().get(world.random.nextInt(dalek.getChildren().size()));
			} else {
				return dalek;
			}
		} else {
			return null;
		}
	}

	public static String getRandomDalek(Random rand) {
		return dalekList.get(rand.nextInt(dalekList.size()));
	}

	public static IDalek getDalek(String identifier) {
		return daleks.containsKey(identifier) ? daleks.get(identifier) : SKARO_DALEK;
	}

	public static List<String> getDalekList() {
		return dalekList;
	}

	@SafeVarargs
	public static void addSpawn(IDalek dalek, RegistryKey<Biome>... biomes) {
		RegistryKey[] var2 = biomes;
		int var3 = biomes.length;

		for(int var4 = 0; var4 < var3; ++var4) {
			RegistryKey<Biome> biome = var2[var4];
			if (DALEK_SPAWNS.containsKey(biome.location())) {
				((List)DALEK_SPAWNS.get(biome.location())).add(dalek);
			} else {
				List<IDalek> l = new ArrayList();
				l.add(dalek);
				DALEK_SPAWNS.put(biome.location(), l);
			}
		}

	}

	public static void addSpawn(RegistryKey<Biome> biome, IDalek... daleks) {
		Biome b = DynamicRegistries.builtin().registryOrThrow(Registry.BIOME_REGISTRY).getOrThrow(biome);
		IDalek[] var3 = daleks;
		int var4 = daleks.length;

		for(int var5 = 0; var5 < var4; ++var5) {
			IDalek dalek = var3[var5];
			if (DALEK_SPAWNS.containsKey(b.getRegistryName())) {
				DALEK_SPAWNS.get(b.getRegistryName()).add(dalek);
			} else {
				List<IDalek> l = new ArrayList();
				l.add(dalek);
				DALEK_SPAWNS.put(b.getRegistryName(), l);
			}
		}

	}

	public static class ArmTypes {
		public static final String DEFAULT_GUN = "GunArm";
		public static final String DEFAULT_PLUNGER = "SuctionArm";
		public static final String CLAW_ARM = "ClawArm";
		public static final String FLAME_THROWER = "FlameThrowerArm";

		public ArmTypes() {
		}
	}
}
