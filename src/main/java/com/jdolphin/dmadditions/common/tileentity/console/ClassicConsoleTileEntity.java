package com.jdolphin.dmadditions.common.tileentity.console;

import com.jdolphin.dmadditions.common.entity.control.AbstractControlType;
import com.jdolphin.dmadditions.common.entity.control.TardisControl;
import com.jdolphin.dmadditions.common.init.DMAControlTypes;
import net.minecraft.util.math.vector.Vector3d;

public class ClassicConsoleTileEntity extends AbstractConsoleTileEntity {


	@Override
	public int getControlAmout() {
		return DMAControlTypes.ALL.size();
	}

	@Override
	public void setControlOffset(TardisControl control) {
		AbstractControlType type = control.getControlType();

		if (type.equals(DMAControlTypes.FLIGHT)) {
			type.setOffset(new Vector3d(-0.75, 0.8, -0.3));
		}
		if (type.equals(DMAControlTypes.DOOR)) {
			type.setOffset(new Vector3d(0.6, 0.8, 0.8));
		}
		if (type.equals(DMAControlTypes.LOCK)) {
			type.setOffset(new Vector3d(0.95, 0.8, 0.2));
		}
		if (type.equals(DMAControlTypes.COORD_X)) {
			type.setOffset(new Vector3d(0.3, 0.8, -0.85));
		}
		if (type.equals(DMAControlTypes.COORD_Y)) {
			type.setOffset(new Vector3d(0, 0.8, -0.85));
		}
		if (type.equals(DMAControlTypes.COORD_Z)) {
			type.setOffset(new Vector3d(-0.3, 0.8, -0.85));
		}
		if (type.equals(DMAControlTypes.COORD_INCREMENT)) {
			type.setOffset(new Vector3d(0, 0.9, -0.5));
		}
		if (type.equals(DMAControlTypes.DIMENSION)) {
			type.setOffset(new Vector3d(0.6, 0.8, -0.5));
		}
		if (type.equals(DMAControlTypes.FAST_RETURN)) {
			type.setOffset(new Vector3d(-0.75, 0.8, 0.5));
		}
	}
}
