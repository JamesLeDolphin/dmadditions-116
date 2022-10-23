package com.jdolphin.dmadditions.init;

import com.swdteam.common.init.DMProjectiles;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class DMAProjectiles extends DMProjectiles {
	public static List<Laser> LASERS;
	public static DMProjectiles.Laser PURPLE_LASER;
	public static DMProjectiles.Laser PINK_LASER;
	public static DMProjectiles.Laser GOLD_LASER;
	public static DMProjectiles.Laser METALLIC_GOLD_LASER;

	public static void init() {
		PURPLE_LASER = addLaser(48, 25, 52);
		PINK_LASER = addLaser(255, 0, 255);
		GOLD_LASER = addLaser(255, 215, 0);
		METALLIC_GOLD_LASER = addLaser(212, 175, 55);

	}

	private static DMProjectiles.Laser addLaser(int r, int g, int b) {
		try {
			Method addLaser = DMProjectiles.class.getDeclaredMethod("addLaser", int.class, int.class, int.class);
			addLaser.setAccessible(true);

			return (Laser) addLaser.invoke(DMProjectiles.class, r, g, b);
		} catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}
}
