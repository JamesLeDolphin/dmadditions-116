package com.jdolphin.dmadditions.block;

import com.jdolphin.dmadditions.init.DMABlocks;
import com.jdolphin.dmadditions.init.DMADimensions;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.tardis.mod.world.dimensions.TDimensions;

public class AndrozaniminorDimensionTpBlock extends Block {

	public AndrozaniminorDimensionTpBlock(AbstractBlock.Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isClientSide() && player instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
			RegistryKey<World> key = worldIn.dimension() == DMADimensions.ANDROZANIMINOR ? World.OVERWORLD : DMADimensions.ANDROZANIMINOR;
			ServerWorld destinationWorld = worldIn.getServer().getLevel(key);
			if (destinationWorld != null && destinationWorld != worldIn) {
				BlockPos headPos = player.blockPosition().above();
				double y = player.getY();
				if (destinationWorld.getBlockState(headPos).isSuffocating(worldIn, pos)) {
					for (int i = -1; i <= 1; i++) {
						for (int j = -1; j <= 1; j++) {
							for (int l = -1; l <= 1; l++) {
								BlockPos bPos = pos.offset(i, j, l);
								destinationWorld.setBlockAndUpdate(bPos, Blocks.AIR.defaultBlockState());
							}
						}
					}
				} serverPlayer.teleportTo(destinationWorld, player.getX(), player.getY(), player.getZ(), serverPlayer.getYHeadRot(), player.xRot);
				destinationWorld.setBlockAndUpdate(pos, DMABlocks.CHRISTMAS_PRESENT.get().defaultBlockState());
			}
		}
		return ActionResultType.SUCCESS;
	}
}
