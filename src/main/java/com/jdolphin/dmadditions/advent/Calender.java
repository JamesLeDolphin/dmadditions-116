package com.jdolphin.dmadditions.advent;

import java.util.Calendar;

public class Calender {
	public static boolean isDecember() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		return (month == 11);
	}
	public static boolean canAdventBeUnlocked(int day) {
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
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return (year == 2022);
	}

}
