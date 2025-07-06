package com.jdolphin.dmadditions.common.tileentity;

import com.jdolphin.dmadditions.common.init.DMABlockEntities;
import com.swdteam.client.render.ScannerPages;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.client.tardis.data.ClientTardisFlightCache;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;

public class BetterScannerTileEntity extends DMTileEntityBase {
	private int screen;
	private long lastTime = 0L;
	private TardisData data;
	private TardisFlightData flightData;

	public BetterScannerTileEntity() {
		super(DMABlockEntities.TILE_SCANNER.get());
	}

	public TardisData getTardisData() {
		return this.data;
	}

	public void setTardisData(TardisData data) {
		this.data = data;
	}

	public CompoundNBT save(CompoundNBT compound) {
		compound.putInt(DMNBTKeys.SCANNER_SCREEN, this.screen);
		return super.save(compound);
	}

	public void load(BlockState state, CompoundNBT compound) {
		int i = compound.getInt(DMNBTKeys.SCANNER_SCREEN);
		if (i <= ScannerPages.PAGES.length - 1 && i >= 0) {
			this.screen = i;
		} else {
			this.screen = 0;
		}

		super.load(state, compound);
	}

	public int getScreen() {
		return this.screen;
	}

	public void changeScreenRelative(int num) {
		this.screen += num;
		if (this.screen >= ScannerPages.PAGES.length) {
			this.screen = 0;
		}

		if (this.screen < 0) {
			this.screen = ScannerPages.PAGES.length - 1;
		}

	}

	public void renderCallUpdate() {
		if (this.level.dimension().equals(DMDimensions.TARDIS) && System.currentTimeMillis() / 1000L % 2L == 0L && this.lastTime != System.currentTimeMillis() / 1000L) {
			this.lastTime = System.currentTimeMillis() / 1000L;
			this.data = ClientTardisCache.getTardisData(this.getBlockPos());
			if (this.data != null) {
				this.flightData = ClientTardisFlightCache.getTardisFlightData(this.data.getGlobalID());
			}
		}

	}
}
