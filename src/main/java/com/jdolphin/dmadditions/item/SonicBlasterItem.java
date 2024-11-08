package com.jdolphin.dmadditions.item;

import com.jdolphin.dmadditions.init.DMAPackets;
import com.jdolphin.dmadditions.network.SBSonicBlasterInteractPacket;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.item.gun.GunItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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