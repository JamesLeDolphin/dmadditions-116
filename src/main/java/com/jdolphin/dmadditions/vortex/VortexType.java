package com.jdolphin.dmadditions.vortex;

public enum VortexType {
	Y1976("1976"),
	Y1980("1980"),
	Y2005("2005"),
	Y2005_BLUE("2005_blue"),
	Y2018("2018"),
	CUSTOM("custom"),
	DEFAULT("default"),
	EDBLUE("edblue"),
	STARS("vortex_stars"),
	NONE("custom");

	public Vortex vortex;
	VortexType(String name) {
		vortex = new Vortex(name);
	}

	VortexType(Vortex vortex) {
		this.vortex = vortex;
	}
}
