package com.jdolphin.dmadditions.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class DimensionTpBlock extends Block {
	private final ResourceLocation targetDimensionId;

	public DimensionTpBlock(ResourceLocation targetDimensionId, AbstractBlock.Properties properties) {
		super(properties);
		this.targetDimensionId = targetDimensionId;
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isClientSide()) {
			ServerWorld destinationWorld = getServerWorldForDimension(targetDimensionId, worldIn.getServer());

			if (destinationWorld != null) {
				Vector3d targetPos = player.position();
				teleportPlayerToDimension(player, targetPos, destinationWorld);
			}
		}
		return ActionResultType.SUCCESS;
	}

	private ServerWorld getServerWorldForDimension(ResourceLocation dimensionId, MinecraftServer server) {
		RegistryKey<World> key = RegistryKey.create(Registry.DIMENSION_REGISTRY, dimensionId);
		return server.getLevel(key);
	}

	private void teleportPlayerToDimension(PlayerEntity player, Vector3d targetPos, ServerWorld destinationWorld) {
		// Teleport the player to the target dimension and location
		Entity entity = player.changeDimension(destinationWorld);

		if (entity != null) {
			entity.teleportTo(targetPos.x, targetPos.y, targetPos.z);
		}
	}
}
