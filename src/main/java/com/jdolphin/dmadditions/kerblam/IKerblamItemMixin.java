package com.jdolphin.dmadditions.kerblam;

import java.util.Date;

public interface IKerblamItemMixin {
	Date unlockAt = null;

	boolean isUnlocked();
}
