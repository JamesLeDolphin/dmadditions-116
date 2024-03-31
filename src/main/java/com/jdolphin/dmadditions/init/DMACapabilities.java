package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.cap.IPlayerDataCap;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class DMACapabilities {

	@CapabilityInject(IPlayerDataCap.class)
	public static final Capability<IPlayerDataCap> PLAYER_DATA = null;
}
