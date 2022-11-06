package com.jdolphin.dmadditions.item;

import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.item.LasergunItem;
import net.minecraft.item.IItemTier;
import net.minecraft.util.*;
import net.minecraftforge.fml.RegistryObject;

public class UnitGun extends LasergunItem {
	public UnitGun(IItemTier tier, float chargeInSeconds, DMProjectiles.Laser laserType, RegistryObject<SoundEvent> lasergunChargeSound, RegistryObject<SoundEvent> lasergunShootSound, Properties properties) {
		super(tier, chargeInSeconds, laserType, lasergunChargeSound, lasergunShootSound, properties);
	}
}
