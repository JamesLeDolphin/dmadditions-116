package com.jdolphin.dmadditions.advent;

import java.util.Calendar;

import static com.jdolphin.dmadditions.DmAdditions.IS_DEBUG;


public class AdventUnlock {
	public static boolean isDecember() {
		if(IS_DEBUG) return true;

		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		return (month == 11);
	}
	public static boolean canAdventBeUnlocked(int day) {
		if(IS_DEBUG) return true;

		if (is2022()) {
			if (isDecember())
				return (day <= getDate());
		} else {
			return true;
		}
		return false;
	}

	public static int getDate() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DATE);
	}

	public static boolean is2022() {
		if(IS_DEBUG) return true;

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return (year == 2022);
	}

}
