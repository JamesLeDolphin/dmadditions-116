package com.jdolphin.dmadditions.init;

import com.swdteam.common.RegistryHandler;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;

import static com.swdteam.common.init.DMSoundEvents.buildSound;

public class DMAdditionsSoundEvents {
	public static final RegistryObject<SoundEvent> PISTOL_SHOOT;

	static {
		PISTOL_SHOOT = buildSound(RegistryHandler.SOUNDS, "item.pistol.shoot");
	}
}
