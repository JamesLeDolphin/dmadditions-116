package com.jdolphin.dmadditions;


import com.jdolphin.dmadditions.advent.Advent;

public class UnlockItem implements Advent {
	private int day;

	public UnlockItem(Class c, int id, int day) {
		super();
		this.day = day;
	}

	public int getDay() {
		return this.day;
	}
}
