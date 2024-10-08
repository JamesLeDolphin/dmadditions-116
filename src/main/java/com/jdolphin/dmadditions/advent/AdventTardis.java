package com.jdolphin.dmadditions.advent;

public enum AdventTardis {
	;

	public final String name;
	public final int date;

	AdventTardis(String name, int date){
		this.name = name;
		this.date = date;
	}

	public static AdventTardis getByName(String name) {
		for (AdventTardis a : AdventTardis.values()) {
			if (a.name.equals(name)) {
				return a;
			}
		}
		return null;
	}
}
