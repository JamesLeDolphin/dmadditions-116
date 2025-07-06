package com.jdolphin.dmadditions.common.entity.dalek;

import net.minecraft.util.math.BlockPos;

public interface IDalekEntityMixin {
	boolean party = false;
	BlockPos jukebox = null;

	boolean isPartyDalek();

	void setRecordPlayingNearby(BlockPos p_191987_1_, boolean p_191987_2_);
}
