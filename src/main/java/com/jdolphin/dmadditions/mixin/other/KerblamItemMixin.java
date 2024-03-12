package com.jdolphin.dmadditions.mixin.other;

import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.kerblam.IKerblamItemMixin;
import com.swdteam.common.kerblam.KerblamItem;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Date;

@Mixin(KerblamItem.class)
public class KerblamItemMixin implements IKerblamItemMixin {
	private Date unlockAt;

	public boolean isUnlocked() {
		if (unlockAt == null) return true;

		return AdventUnlock.getCalendar().toInstant().isAfter(
			unlockAt.toInstant());
	}
}
