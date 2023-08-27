package com.jdolphin.dmadditions.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
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
			ServerWorld destinationWorld = getServerWorldForDimension(targetDimensionId, player.getServer());

			if (destinationWorld != null) {
				teleportPlayerToDimension(player, destinationWorld);
			}
		}
		return ActionResultType.SUCCESS;
	}

	private ServerWorld getServerWorldForDimension(ResourceLocation dimensionId, MinecraftServer server) {
		RegistryKey<World> key = RegistryKey.create(Registry.DIMENSION_REGISTRY, dimensionId);
		return server.getLevel(key);
	}

	private void teleportPlayerToDimension(PlayerEntity player, ServerWorld destinationWorld) {
		ServerWorld originWorld = player.level.getServer().getLevel(player.level.dimension());

		if (originWorld != null && originWorld != destinationWorld) {
			player.changeDimension(destinationWorld);
		}
	}
}
