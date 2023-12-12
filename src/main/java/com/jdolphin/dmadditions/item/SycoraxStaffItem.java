package com.jdolphin.dmadditions.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SycoraxStaffItem extends Item {
	public SycoraxStaffItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		this.spawnParticles(world, playerEntity.getEyePosition(1).add(playerEntity.getForward().multiply(3, 1, 3)));

		return super.use(world, playerEntity, hand);
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