package com.jdolphin.dmadditions.blocks;

import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.Location;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.ChatUtil.MessageType;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class RandomizerBlock extends Block {
	private String dimensionKey;

	public RandomizerBlock(AbstractBlock.Properties properties) {
		super(properties);
	}

	public RegistryKey<World> dimensionWorldKey() {
		return RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(this.dimensionKey));
	}

	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
			BlockRayTraceResult rayTraceResult) {
		System.out.println("Randomizer Clicked!");
		if (worldIn.isClientSide)
			return ActionResultType.PASS;

		if (handIn == Hand.MAIN_HAND) {
			if (ServerLifecycleHooks.getCurrentServer() == null)
				return ActionResultType.CONSUME;

			ServerWorld level = (ServerWorld) worldIn;
			WorldBorder border = level.getWorldBorder();
			TardisData tardis = DMTardis.getTardisFromInteriorPos(pos);

			Location currentLocation = tardis.getCurrentLocation();
			BlockPos currentPos = currentLocation.getBlockPosition();

			double maxDistance = 10_000; // TODO: put this in server config file or something idk

			double maxX = Math.min(border.getMaxX(), currentPos.getX() + maxDistance);
			double minX = Math.max(border.getMinX(), currentPos.getX() - maxDistance);

			double maxZ = Math.min(border.getMaxZ(), currentPos.getZ() + maxDistance);
			double minZ = Math.max(border.getMinZ(), currentPos.getZ() - maxDistance);

			double xPos = Math.floor(Math.random() * (maxX - minX + 1) + minX);
			double zPos = Math.floor(Math.random() * (maxZ - minZ + 1) + minZ);
			double yPos = currentPos.getY();

			System.out.printf("Randomizer going to %s %s %s%n", xPos, yPos, zPos);

			BlockPos newPos = new BlockPos(xPos, yPos, zPos);

			TardisFlightData flight = TardisFlightPool.getFlightData(tardis);

			flight.setPos(newPos);
			flight.recalculateLanding(true);
			TardisFlightPool.updateFlight(tardis, new Location(newPos, flight.dimensionWorldKey()));

			ChatUtil.sendCompletedMsg(player,
					new TranslationTextComponent("notice.dalekmod.tardis.randomiser_set", newPos.getX(), newPos.getZ()),
					MessageType.STATUS_BAR);
		}
		return ActionResultType.SUCCESS;
	}
}
