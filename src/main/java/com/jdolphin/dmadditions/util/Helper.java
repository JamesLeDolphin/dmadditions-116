package com.jdolphin.dmadditions.util;

import com.jdolphin.dmadditions.DMAdditions;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.main.DalekMod;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class Helper {

	public static ResourceLocation createAdditionsRL(String string) {
		return new ResourceLocation(DMAdditions.MODID, string);
	}

	public static ResourceLocation createDMRL(String string) {
		return new ResourceLocation(DalekMod.MODID, string);
	}

	public static boolean isTardis(World world) {
		return world.dimension().equals(DMDimensions.TARDIS);
	}

	public static Vector3d blockPosToVec3(BlockPos pos) {
		return new Vector3d(pos.getX(), pos.getY(), pos.getZ());
	}

	public static BlockPos vec3ToBlockPos(Vector3d vec) {
		return new BlockPos(vec.x, vec.y, vec.z);
	}

	public static void playSound(World world, BlockPos pos, SoundEvent soundEvent, SoundCategory category) {
		playSound(world, pos, soundEvent, category, 1.0f);
	}

	public static void playSound(World world, BlockPos pos, SoundEvent soundEvent, SoundCategory category, float pitch) {
		world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), soundEvent, category, 1.0f, pitch);
	}

	public static int minutes(int min) {
		return seconds(60) * min;
	}

	public static int seconds(int s) {
		return 20 * s;
	}

	public static void print(Object obj) {
		System.out.println(obj);
	}

	public static void info(Object obj) {
		DMAdditions.LOGGER.info(obj);
	}

	public static void warn(Object obj) {
		DMAdditions.LOGGER.warn(obj);
	}

	public static void addEntities(World world, Entity... entities) {
		for (Entity entity : entities) {
			world.addFreshEntity(entity);
		}
	}
}
