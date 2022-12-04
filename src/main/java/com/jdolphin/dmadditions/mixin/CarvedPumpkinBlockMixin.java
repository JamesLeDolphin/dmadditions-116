package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.entity.SnowmanEntity;
import com.jdolphin.dmadditions.init.DMAEntities;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CarvedPumpkinBlock.class)
public class CarvedPumpkinBlockMixin {
	@Redirect(method = "trySpawnGolem", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/world/World;addFreshEntity(Lnet/minecraft/entity/Entity;)Z"
	))
	boolean addFreshEntity(World instance, Entity entity) {
		if (instance.isClientSide) return false;

		if (!(entity instanceof SnowGolemEntity)
			|| instance.getRandom().nextFloat() > 0.25
			|| DMAEntities.SNOWMAN == null)
			return instance.addFreshEntity(entity);

		SnowmanEntity snowman = DMAEntities.SNOWMAN.get().create(instance);
		if (snowman == null) return instance.addFreshEntity(entity);

		snowman.teleportTo(entity.getX(), entity.getY(), entity.getZ());
		snowman.finalizeSpawn((IServerWorld) instance,
			instance.getCurrentDifficultyAt(snowman.blockPosition()), SpawnReason.SPAWN_EGG, null, null);

		if (!snowman.equipItemIfPossible(new ItemStack(Items.CARVED_PUMPKIN))) {
			instance.addFreshEntity(
				new ItemEntity(instance,
					snowman.getX(), snowman.getY(), snowman.getZ(),
					new ItemStack(Items.CARVED_PUMPKIN)));
		}

		entity.remove();
		return instance.addFreshEntity(snowman);
	}
}
