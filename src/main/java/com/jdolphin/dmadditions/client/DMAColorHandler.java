package com.jdolphin.dmadditions.client;

import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.item.ChristmasHatItem;
import net.minecraftforge.client.event.ColorHandlerEvent;

public class DMAColorHandler {
	public static void registerItemColor(ColorHandlerEvent.Item event) {
		event.getItemColors().register(ChristmasHatItem::hatColor, DMAItems.CHRISTMAS_HAT.get());
	}
}
