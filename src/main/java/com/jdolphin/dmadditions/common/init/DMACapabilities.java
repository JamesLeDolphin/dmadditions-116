package com.jdolphin.dmadditions.common.init;

import com.jdolphin.dmadditions.common.cap.IPlayerRegenCap;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class DMACapabilities {

	@CapabilityInject(IPlayerRegenCap.class)
	public static final Capability<IPlayerRegenCap> REGEN_CAPABILITY = null;
}
