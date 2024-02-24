package com.jdolphin.dmadditions.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SycoraxStaffItem extends Item {
	public SycoraxStaffItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {
		this.spawnParticles(context.getPlayer().level, context.getClickLocation());
		return super.useOn(context);
	}

	private void spawnParticles(World world, Vector3d position) {
		for (int i = 0; i < 200; i++) {
			double xOffset = random.nextGaussian() * 0.02D;
			double yOffset = random.nextGaussian() * 0.02D;
			double zOffset = random.nextGaussian() * 0.02D;

			world.addParticle(ParticleTypes.SMOKE, position.x,position.y,position.z, xOffset, yOffset, zOffset);
		}
	}
}
