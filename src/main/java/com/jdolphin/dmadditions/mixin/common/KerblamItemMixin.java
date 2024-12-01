package com.jdolphin.dmadditions.mixin.common;

import com.jdolphin.dmadditions.advent.TimedUnlock;
import com.jdolphin.dmadditions.kerblam.IKerblamItemMixin;
import com.swdteam.common.kerblam.KerblamItem;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Date;

@Mixin(KerblamItem.class)
public class KerblamItemMixin implements IKerblamItemMixin {
	private Date unlockAt;

	public boolean isUnlocked() {
		if (unlockAt == null) return true;

		return TimedUnlock.getCalendar().toInstant().isAfter(
			unlockAt.toInstant());
	}
}
