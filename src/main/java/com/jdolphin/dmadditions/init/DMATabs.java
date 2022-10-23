package com.jdolphin.dmadditions.init;

import com.swdteam.common.init.DMItems;
import net.minecraft.item.Item;

import java.util.HashMap;

import static com.swdteam.common.init.DMTabs.DM_TARDIS;

public class DMATabs {
	public static HashMap<Integer, Item> MIXIN_ICONS = new HashMap<Integer, Item>() {{
		put(DM_TARDIS.getId(), DMItems.TARDIS_SPAWNER.get());
	}};

	public DMATabs() {
	}
}
