package com.jdolphin.dmadditions.common.entity.control;

import com.jdolphin.dmadditions.common.tileentity.console.AbstractConsoleTileEntity;
import com.swdteam.common.item.DimensionDataCard;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.common.tardis.data.TardisLocationRegistry;
import com.swdteam.util.ChatUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class DimensionInputControl extends AbstractControlType {
	private int index = 0;

	public DimensionInputControl(ResourceLocation name) {
		super(name);
	}

	public TardisLocationRegistry.TardisLocation getLocation() {
		return TardisLocationRegistry.getLocationRegistryAsList().get(index);
	}

	private void modIndex(int i) {
		if (this.index + i >= TardisLocationRegistry.getLocationRegistryAsList().size()) {
			this.index = 0;
		} else if (this.index + i < 0) {
			this.index = TardisLocationRegistry.getLocationRegistryAsList().size() - 1;
		} else {
			this.index += i;
		}
	}

	@Override
	public void onPress(ServerWorld level, PlayerEntity player, BlockPos pos, TardisData data, TardisControl control) {
		Hand hand = player.getUsedItemHand();
		MinecraftServer server = level.getServer();
		ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() instanceof DimensionDataCard) {
			CompoundNBT tag = stack.getOrCreateTag();
			if (tag.contains("dim_id")) {
				String dim = tag.getString("dim_id");
				if (!data.isDimensionUnlocked(dim)) {
					RegistryKey<World> worldKey = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(dim));
					ServerWorld world = server.getLevel(worldKey);
					if (world != null) {
						data.unlockDimension(dim);
						ChatUtil.sendCompletedMsg(player, "Dimension Unlocked!", ChatUtil.MessageType.STATUS_BAR);
						if (!player.isCreative()) {
							stack.shrink(1);
							player.setItemInHand(hand, stack);
						}
					}
				}
			}
		} else {

			if (player.isShiftKeyDown()) this.modIndex(-1);
			else this.modIndex(1);

			AbstractConsoleTileEntity consoleTile = control.getConsole();
			TardisLocationRegistry.TardisLocation location = getLocation();
			TardisFlightData flightData = TardisFlightPool.getFlightData(data);
			if (flightData != null) {
				flightData.setDimensionKey(location.getDimension());
				consoleTile.setDimIcon(location.getDimensionImage());
				ChatUtil.sendCompletedMsg(player, "Destination set for: " + location.getDimensionName().getString(), ChatUtil.MessageType.STATUS_BAR);
			}
		}
	}
}
