package com.jdolphin.dmadditions.common.init;

import com.jdolphin.dmadditions.common.entity.control.*;
import com.jdolphin.dmadditions.common.util.Helper;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class DMAControlTypes {

	public static final Map<ResourceLocation, AbstractControlType> ALL = new HashMap<>();

	public static final AbstractControlType COORD_X = register(new CoordInputControl(Helper.createAdditionsRL("positive_x"), Direction.Axis.X, true));
	public static final AbstractControlType COORD_Y = register(new CoordInputControl(Helper.createAdditionsRL("positive_y"), Direction.Axis.Y, true));
	public static final AbstractControlType COORD_Z = register(new CoordInputControl(Helper.createAdditionsRL("positive_z"), Direction.Axis.Z, true));
	public static final AbstractControlType COORD_INCREMENT = register(new CoordIncrementControl(Helper.createAdditionsRL("coord_increment")));

	public static final AbstractControlType DOOR = register(new DoorControl(Helper.createAdditionsRL("door")));
	public static final AbstractControlType LOCK = register(new LockControl(Helper.createAdditionsRL("lock")));
	public static final AbstractControlType FLIGHT = register(new FlightControl(Helper.createAdditionsRL("flight")));
	public static final AbstractControlType FAST_RETURN = register(new FastReturnControl(Helper.createAdditionsRL("fast_return")));
	public static final AbstractControlType DIMENSION = register(new DimensionInputControl(Helper.createAdditionsRL("dimension")));

	private static <T extends AbstractControlType> T register(T control) {
		try {
			ResourceLocation location = control.getName();
			if (!ALL.containsKey(location)) {
				ALL.put(location, control);
				return control;
			} else throw new IllegalArgumentException("Duplicate registration: " + location.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
