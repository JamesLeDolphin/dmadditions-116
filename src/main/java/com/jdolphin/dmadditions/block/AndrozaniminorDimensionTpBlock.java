package com.jdolphin.dmadditions.block;

import com.jdolphin.dmadditions.init.DMADimensions;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.registry.Registry;

public class AndrozaniminorDimensionTpBlock extends Block {

	public AndrozaniminorDimensionTpBlock(AbstractBlock.Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isClientSide() && player instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
			RegistryKey<World> key = RegistryKey.create(Registry.DIMENSION_REGISTRY, DMADimensions.ANDROZANIMINOR.location());
			ServerWorld destinationWorld = worldIn.getServer().getLevel(key);
			if (destinationWorld != null && destinationWorld != worldIn) {
				serverPlayer.teleportTo(destinationWorld, player.getX(), player.getY(), player.getZ(), serverPlayer.getYHeadRot(), player.xRot);
			}
		}
		return ActionResultType.SUCCESS;
	}
}
