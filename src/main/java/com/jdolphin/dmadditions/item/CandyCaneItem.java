package com.jdolphin.dmadditions.item;

import com.jdolphin.dmadditions.advent.Calender;
import com.swdteam.common.item.FoodItem;

public class CandyCaneItem extends FoodItem {

	public CandyCaneItem(Properties properties) {
		super(properties);
	}
	public static boolean Unlock() {
		return Calender.canAdventBeUnlocked(1);
	}

}
