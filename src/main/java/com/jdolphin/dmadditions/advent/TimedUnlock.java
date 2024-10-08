package com.jdolphin.dmadditions.advent;

import java.time.Month;
import java.util.Calendar;

import static com.jdolphin.dmadditions.DMAdditions.IS_DEBUG;


public class TimedUnlock {
	protected static Calendar calendar = Calendar.getInstance();

	protected static final boolean FORCE_FAKE_DATE = false; // keep this on false. Only change it when building beta jars
	private static final int FAKE_YEAR = 2024;
	private static final int FAKE_MONTH = Calendar.DECEMBER;
	private static final int FAKE_DATE = 25;

	protected static void handleFakeStuff(){
		if (IS_DEBUG || FORCE_FAKE_DATE)
			calendar.set(FAKE_YEAR, FAKE_MONTH, FAKE_DATE);
	}

	public static Calendar getCalendar() {
		return calendar;
	}

	@Deprecated
	public static boolean advent(int day) {
		return advent(2024, day);
	}

	public static boolean advent(int year, int day) {
		handleFakeStuff();

		int currentYear = getYear();

		if (currentYear > year)
			return true;
		if (currentYear < year)
			return false;

		if (!isDecember())
			return false;

		return (getDate() >= day);
	}

	public static boolean isHalloween(int year) {
		handleFakeStuff();

		int currentYear = getYear();

		if (currentYear > year)
			return true;
		if (currentYear < year)
			return false;

		return getMonth() == Month.OCTOBER
				&& getDate() == 31;
	}

	public static int getYear() {
		return calendar.get(Calendar.YEAR);
	}
	public static Month getMonth() {
		return Month.of(calendar.get(Calendar.MONTH));
	}
	public static int getDate() {
		return calendar.get(Calendar.DATE);
	}

	public static boolean isDecember() {
		return getMonth() == Month.DECEMBER;
	}

	@Deprecated
	public static boolean is2024() {
		int year = getYear();
		return (year == 2024);
	}
}
