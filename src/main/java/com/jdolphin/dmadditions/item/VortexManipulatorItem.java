package com.jdolphin.dmadditions.item;

import com.jdolphin.dmadditions.init.DMAPackets;
import com.jdolphin.dmadditions.network.CBOpenGUIPacket;
import com.jdolphin.dmadditions.util.DMALocation;
import com.jdolphin.dmadditions.util.GuiHandler;
import com.swdteam.common.init.DMItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeItem;

public class VortexManipulatorItem extends Item implements IForgeItem {
	public static final String TAG_FUEL = "Fuel";
	public static final String TAG_DESTINATION = "Destination";
	public static final int MAX_FUEL = 64;
	public VortexManipulatorItem(Properties properties) {
		super(properties);
	}

	public static int getFuel(CompoundNBT tag) {
		return tag.contains(TAG_FUEL) ? tag.getInt(TAG_FUEL) : MAX_FUEL;
	}

	public static void setFuel(CompoundNBT tag, int fuel) {
		tag.putInt(TAG_FUEL, fuel);
	}

	public static void lowerFuel(CompoundNBT tag, int amount) {
		int i = getFuel(tag);
		tag.putInt(TAG_FUEL, Math.max(0, i - amount));
	}

	public boolean showDurabilityBar(ItemStack stack) {
		return getFuel(stack.getOrCreateTag()) < MAX_FUEL;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return Math.round((float)getFuel(stack.getOrCreateTag()) * 13.0F / MAX_FUEL);
	}

	public static void setup(CompoundNBT tag) {
		if (!tag.contains(TAG_FUEL)) tag.putInt(TAG_FUEL, MAX_FUEL);
		if (!tag.contains(TAG_DESTINATION)) tag.putString(TAG_DESTINATION, DMALocation.DEFAULT.getLocationString());
	}

	@Override
	public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getMainHandItem();
		CompoundNBT tag = stack.getOrCreateTag();
		if (!level.isClientSide()) {
			setup(tag);
			if (player.isShiftKeyDown()) DMAPackets.sendTo((ServerPlayerEntity) player, new CBOpenGUIPacket(player.blockPosition(), GuiHandler.VORTEX_MANIUPULATOR));
			ItemStack offHand = player.getItemInHand(Hand.OFF_HAND);
			ItemStack mainHand = player.getItemInHand(Hand.MAIN_HAND);
			if (offHand.getItem().equals(DMItems.FULL_ARTRON.get()) && mainHand.getItem().equals(this)) {
				if (getFuel(tag) < MAX_FUEL) {
					setFuel(tag, MAX_FUEL);
					offHand.shrink(1);
					return ActionResult.success(stack);
				}
			}
		}
		return ActionResult.pass(stack);
	}

}
