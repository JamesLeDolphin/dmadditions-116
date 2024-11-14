package com.jdolphin.dmadditions.item;

import org.jetbrains.annotations.NotNull;

import com.jdolphin.dmadditions.init.DMAPackets;
import com.jdolphin.dmadditions.network.SBSonicBlasterInteractPacket;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.World;

public class SonicBlasterItem extends Item {


	public SonicBlasterItem(Properties properties) {
		super(properties);
	}

	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		BlockRayTraceResult rayTraceResult = getPlayerPOVHitResult(world, player, RayTraceContext.FluidMode.ANY);
		BlockPos pos = rayTraceResult.getBlockPos();
		if (!world.getBlockState(pos).is(Blocks.AIR)) {

			SBSonicBlasterInteractPacket packet = new SBSonicBlasterInteractPacket(pos);
			DMAPackets.INSTANCE.sendToServer(packet);
			return ActionResult.success(player.getItemInHand(hand));
		}

		return ActionResult.pass(player.getItemInHand(hand));
	}

	public static @NotNull BlockRayTraceResult getPlayerPOVHitResult(World world, PlayerEntity player, RayTraceContext.@NotNull FluidMode mode) {
		return Item.getPlayerPOVHitResult(world, player, mode);
	}
}
