package com.jdolphin.dmadditions.block.tardis;

public interface ITardisDMAActions {
	boolean invisible = false;
	boolean forcefield = false;

	boolean isInvisible();

	void setInvisible(boolean invisible);

	boolean isForcefieldActive();

	void setForcefieldActive(boolean invisible);
}
