package com.jdolphin.dmadditions.init;

import com.swdteam.common.init.DMItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import static com.swdteam.common.init.DMTabs.DM_TARDIS;

public class DMATabs {
	public static final HashMap<Integer, Item> MIXIN_ICONS = new HashMap<>();
	private static boolean mixinIconsReady = false;

	public static boolean isMixinIconsReady() {
		return mixinIconsReady;
	}

	public static void initMixinIcons() {
		mixinIconsReady = true;

		MIXIN_ICONS.put(DM_TARDIS.getId(), DMItems.TARDIS_SPAWNER.get());
	}

	public static final ItemGroup DMA_ROUNDEL_CONTAINERS = new ItemGroup("DMA_Roundel_Containers") {
		public @NotNull ItemStack makeIcon() {
			return DMABlocks.DALEKANIUM_ROUNDEL_CONTAINER.isPresent() ? new ItemStack(DMABlocks.DALEKANIUM_ROUNDEL_CONTAINER.get()) :
				new ItemStack(DMItems.ROUNDEL_MOLD.get());
		}

		public void fillItemList(NonNullList<ItemStack> stacks) {
			super.fillItemList(stacks);
		}
	};

	public DMATabs() {
	}
}
