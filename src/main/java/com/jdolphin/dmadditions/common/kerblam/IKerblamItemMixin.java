package com.jdolphin.dmadditions.common.kerblam;

import java.util.Date;

public interface IKerblamItemMixin {
	Date unlockAt = null;

	boolean isUnlocked();
}
