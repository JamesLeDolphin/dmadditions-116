package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.config.DMACommonConfig;
import com.swdteam.common.entity.CyberdroneEntity;
import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.init.DMParticleTypes;
import com.swdteam.common.init.DMProjectiles;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;

@Mixin(DMProjectiles.class)
public class DMProjectilesMixin {
		@Shadow(remap = false) private static List<DMProjectiles.Laser> LASERS = new ArrayList<>();
		@Shadow(remap = false) public static DMProjectiles.Laser BLUE_LASER;
		@Shadow(remap = false) public static DMProjectiles.Laser RED_LASER;
		@Shadow(remap = false) public static DMProjectiles.Laser GREEN_LASER;
		@Shadow(remap = false) public static DMProjectiles.Laser ORANGE_LASER;
		@Shadow(remap = false) public static DMProjectiles.Laser POISON;
		@Shadow(remap = false) public static DMProjectiles.Laser FLASH;
		@Shadow(remap = false) public static DMProjectiles.Laser SMOKE;
		@Shadow(remap = false) public static DMProjectiles.Laser FIRE;
		@Shadow(remap = false) public static DMProjectiles.Laser BULLET;
		@Shadow(remap = false) public static DMProjectiles.Laser NAUSEA_LASER;
		@Shadow(remap = false) public static DMProjectiles.Laser EXPLOSIVE_LASER;

	@Shadow(remap = false)
	private static DMProjectiles.Laser addLaser(int r, int g, int b) {
		DMProjectiles.Laser l = new DMProjectiles.Laser(LASERS.size(), (float)r / 255.0F, (float)g / 255.0F, (float)b / 255.0F);
		LASERS.add(l);
		return l;
	}
	@Shadow(remap = false)
	private static DMProjectiles.Laser addLaser(boolean renders) {
		DMProjectiles.Laser l = new DMProjectiles.Laser(LASERS.size(), renders);
		LASERS.add(l);
		return l;
	}

	/**
	 * @author JamesLeDolphin
	 * @reason Config
	 */
	@Overwrite(remap = false)
	public static void init() {
		BLUE_LASER = addLaser(90, 200, 255);
		RED_LASER = addLaser(255, 60, 50);
		GREEN_LASER = addLaser(60, 210, 30);
		ORANGE_LASER = addLaser(255, 140, 60);
		POISON = addLaser(false);
		FLASH = addLaser(false);
		SMOKE = addLaser(false);
		FIRE = addLaser(false);
		BULLET = addLaser(1, 1, 1);
		NAUSEA_LASER = addLaser(130, 200, 255);
		EXPLOSIVE_LASER = addLaser(255, 170, 10);
		NAUSEA_LASER.setLaserInterface(new DMProjectiles.ILaser() {
			public void onImpact(World world, RayTraceResult result, LaserEntity laser) {
				if (result.getType() == RayTraceResult.Type.ENTITY) {
					Entity entity = ((EntityRayTraceResult)result).getEntity();
					if (entity != null && entity instanceof LivingEntity) {
						if (entity instanceof PlayerEntity) {
							PlayerEntity player = (PlayerEntity)entity;
							if (player.isBlocking()) {
								return;
							}
						}

						((LivingEntity)entity).addEffect(new EffectInstance(Effects.CONFUSION, 200, 2));
					}
				}

			}

			public void tick(LaserEntity laser) {
			}
		});
		FLASH.setLaserInterface(new DMProjectiles.ILaser() {
			public void tick(LaserEntity laser) {
				if (laser.level.isClientSide) {
					for(int i = 0; i < 5; ++i) {
						float randomX = laser.level.random.nextFloat() * (float)(laser.level.random.nextBoolean() ? -1 : 1) / 2.0F;
						float randomY = laser.level.random.nextFloat() * (float)(laser.level.random.nextBoolean() ? -1 : 1) / 2.0F;
						float randomZ = laser.level.random.nextFloat() * (float)(laser.level.random.nextBoolean() ? -1 : 1) / 2.0F;
						laser.level.addParticle(ParticleTypes.POOF, laser.getX() + (double)randomX, laser.getY() + (double)randomY, laser.getZ() + (double)randomZ, 0.0, 0.0, 0.0);
					}
				}

				if (laser.tickCount > 10) {
					laser.remove();
				}

			}

			public void onImpact(World world, RayTraceResult result, LaserEntity laser) {
				if (result.getType() == RayTraceResult.Type.ENTITY) {
					Entity entity = ((EntityRayTraceResult)result).getEntity();
					if (entity != null && entity instanceof LivingEntity) {
						if (entity instanceof PlayerEntity) {
							PlayerEntity player = (PlayerEntity)entity;
							if (player.isBlocking()) {
								return;
							}
						}

						((LivingEntity)entity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 2));
					}
				}

			}
		});
		SMOKE.setLaserInterface(new DMProjectiles.ILaser() {
			public void tick(LaserEntity laser) {
				if (laser.level.isClientSide) {
					for(int i = 0; i < 5; ++i) {
						float randomX = laser.level.random.nextFloat() * (float)(laser.level.random.nextBoolean() ? -1 : 1) / 2.0F;
						float randomY = laser.level.random.nextFloat() * (float)(laser.level.random.nextBoolean() ? -1 : 1) / 2.0F;
						float randomZ = laser.level.random.nextFloat() * (float)(laser.level.random.nextBoolean() ? -1 : 1) / 2.0F;
						laser.level.addParticle(ParticleTypes.CLOUD, laser.getX() + (double)randomX, laser.getY() + (double)randomY, laser.getZ() + (double)randomZ, 0.0, 0.0, 0.0);
					}
				}

				if (laser.tickCount > 10) {
					laser.remove();
				}

			}

			public void onImpact(World world, RayTraceResult result, LaserEntity laser) {
			}
		});
		POISON.setLaserInterface(new DMProjectiles.ILaser() {
			public void tick(LaserEntity laser) {
				if (laser.level.isClientSide) {
					for(int i = 0; i < 5; ++i) {
						float ff = (float)(15 - laser.tickCount);
						float randomX = laser.level.random.nextFloat() * (float)(laser.level.random.nextBoolean() ? -1 : 1) / (ff / 10.0F);
						float randomY = laser.level.random.nextFloat() * (float)(laser.level.random.nextBoolean() ? -1 : 1) / (ff / 10.0F);
						float randomZ = laser.level.random.nextFloat() * (float)(laser.level.random.nextBoolean() ? -1 : 1) / (ff / 10.0F);
						laser.level.addParticle((IParticleData) DMParticleTypes.POISON_GAS.get(), laser.getX() + (double)randomX, laser.getY() + (double)randomY, laser.getZ() + (double)randomZ, 0.0, 0.0, 0.0);
					}
				}

				if (laser.tickCount > 15) {
					laser.remove();
				}

			}

			public void onImpact(World world, RayTraceResult result, LaserEntity laser) {
				if (result.getType() == RayTraceResult.Type.ENTITY) {
					Entity entity = ((EntityRayTraceResult)result).getEntity();
					if (entity != null && entity instanceof LivingEntity) {
						if (entity instanceof PlayerEntity) {
							PlayerEntity player = (PlayerEntity)entity;
							if (player.isBlocking()) {
								return;
							}
						}

						((LivingEntity)entity).addEffect(new EffectInstance(Effects.POISON, 200, 2));
					}
				}

			}
		});
		EXPLOSIVE_LASER.setLaserInterface(new DMProjectiles.ILaser() {
			public void onCreate(LaserEntity laser) {
				if ((DMACommonConfig.disable_cyberdrone_laser.get() && laser.getOwner() instanceof CyberdroneEntity) ||
					DMACommonConfig.disable_explosive_laser.get()) {
					laser.setExplosive(false);
					laser.setCausesFireExplosion(false);
				} else if (!DMACommonConfig.disable_explosive_laser.get()) {
					laser.setExplosive(true);
					laser.setCausesFireExplosion(true);
				}
			}
		});
		FIRE.setLaserInterface(new DMProjectiles.ILaser() {
			public void tick(LaserEntity laser) {
				if (laser.level.isClientSide) {
					for(int i = 0; i < 25; ++i) {
						float ff = (float)(10 - laser.tickCount);
						float randomX = laser.level.random.nextFloat() * (float)(laser.level.random.nextBoolean() ? -1 : 1) / (ff / 1.0F);
						float randomY = laser.level.random.nextFloat() * (float)(laser.level.random.nextBoolean() ? -1 : 1) / (ff / 1.0F);
						float randomZ = laser.level.random.nextFloat() * (float)(laser.level.random.nextBoolean() ? -1 : 1) / (ff / 1.0F);
						laser.level.addParticle((IParticleData)DMParticleTypes.FIRE_BALL.get(), laser.getX() + (double)randomX, laser.getY() + (double)randomY - 0.10000000149011612, laser.getZ() + (double)randomZ, 0.0, 0.0, 0.0);
					}
				}

				if (laser.tickCount > 10) {
					laser.remove();
				}

			}

			public void onImpact(World world, RayTraceResult result, LaserEntity laser) {
				if (result.getType() == RayTraceResult.Type.ENTITY) {
					Entity entity = ((EntityRayTraceResult)result).getEntity();
					if (entity != null) {
						entity.setSecondsOnFire(3);
					}
				} else if (result.getType() == RayTraceResult.Type.BLOCK && ForgeEventFactory.getMobGriefingEvent(world, laser.getOwner())) {
					BlockRayTraceResult block = (BlockRayTraceResult)result;
					BlockPos blockpos = block.getBlockPos();
					BlockState blockstate = world.getBlockState(blockpos);
					if (CampfireBlock.canLight(blockstate)) {
						world.setBlockAndUpdate(blockpos, (BlockState)blockstate.setValue(CampfireBlock.LIT, true));
					} else {
						blockpos = blockpos.relative(block.getDirection());
						if (AbstractFireBlock.canBePlacedAt(world, blockpos, laser.getDirection())) {
							world.setBlockAndUpdate(blockpos, AbstractFireBlock.getState(world, blockpos));
						}
					}
				}

			}
		});
	}
}
