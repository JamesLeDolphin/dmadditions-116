package com.jdolphin.dmadditions.advent;

import java.util.Calendar;

import static com.jdolphin.dmadditions.DmAdditions.IS_DEBUG;


public class AdventUnlock {
	protected static Calendar calendar = Calendar.getInstance();

	protected static final boolean FORCE_FAKE_DATE = false;
	private static final int FAKE_YEAR = 2023;
	private static final int FAKE_MONTH = Calendar.DECEMBER;
	private static final int FAKE_DATE = 25;

	public static Calendar getCalendar() {
		return calendar;
	}

	public static boolean isDecember() {
		int month = calendar.get(Calendar.MONTH);
		return (month == 11);

	}

	public static boolean unlockAt(int day) {
		if (IS_DEBUG || FORCE_FAKE_DATE) calendar.set(FAKE_YEAR, FAKE_MONTH, FAKE_DATE);

		if (!is2023())
			return true;

		if (!isDecember())
			return false;

		return (getDate() >= day);
	}

	public static int getDate() {
		return calendar.get(Calendar.DATE);
	}

	public static boolean is2023() {
		int year = calendar.get(Calendar.YEAR);
		return (year == 2023);
	}
}
