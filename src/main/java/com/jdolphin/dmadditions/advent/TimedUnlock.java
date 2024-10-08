package com.jdolphin.dmadditions.advent;

import static com.jdolphin.dmadditions.DMAdditions.IS_DEBUG;

import java.util.Calendar;


public class TimedUnlock {
	protected static Calendar calendar = Calendar.getInstance();

	protected static final boolean FORCE_FAKE_DATE = false; // keep this on false. Only change it when building beta jars
	private static final int FAKE_YEAR = 2025;
	private static final int FAKE_MONTH = Calendar.OCTOBER;
	private static final int FAKE_DATE = 31;

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
		int currentYear = getYear();

		if (currentYear > year)
			return true;
		if (currentYear < year)
			return false;

		if (!isDecember())
			return false;

		return (getDate() >= day);
	}

	public static boolean isHalloween() {
		return getMonth() == Calendar.OCTOBER
				&& getDate() == 31;
	}

	public static boolean halloween(int year) {
		int currentYear = getYear();

		if (currentYear > year)
			return true;
		if (currentYear < year)
			return false;

		return isHalloween();
	}

	public static int getYear() {
		handleFakeStuff();
		return calendar.get(Calendar.YEAR);
	}
	public static int getMonth() {
		handleFakeStuff();
		return calendar.get(Calendar.MONTH);
	}
	public static int getDate() {
		handleFakeStuff();
		return calendar.get(Calendar.DATE);
	}

	public static boolean isDecember() {
		return getMonth() == Calendar.DECEMBER;
	}

	@Deprecated
	public static boolean is2024() {
		int year = getYear();
		return (year == 2024);
	}
}
