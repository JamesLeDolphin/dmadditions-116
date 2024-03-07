package com.jdolphin.dmadditions.util;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.client.render.tileentity.control.TardisControlRenderer;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.main.DalekMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class Helper {

	public static ResourceLocation createAdditionsRL(String string) {
		return new ResourceLocation(DmAdditions.MODID, string);
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
		world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), soundEvent, category, 1.0f, 1.0f);
	}

	public static void print(Object obj) {
		System.out.println(obj);
	}

	public static void info(Object obj) {
		DmAdditions.LOGGER.info(obj);
	}

	public static void warn(Object obj) {
		DmAdditions.LOGGER.warn(obj);
	}

	public static void addEntities(World world, Entity... entities) {
		for (Entity entity : entities) {
			world.addFreshEntity(entity);
		}
	}

	public static void registerControlRenderers(EntityType<?>... entities) {
		for (EntityType entity : entities) {
			RenderingRegistry.registerEntityRenderingHandler(entity, TardisControlRenderer::new);
		}
	}
}
