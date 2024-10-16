package com.jdolphin.dmadditions.client.title;

public enum MenuBackGround {

	K9("bg_1"),
	XMAS("bg_2"),
	CYBER("bg_3"),
	DALEK_SNOWY("bg_4"),
	ANGELS("bg_5"),
	TARDIS("bg_6"),
	D13("bg_7"),
	TARDIS_SHADERS("bg_8"),
	FOREMAN("bg_9");

	String name;

	MenuBackGround(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
