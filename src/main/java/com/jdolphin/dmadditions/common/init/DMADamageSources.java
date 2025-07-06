package com.jdolphin.dmadditions.common.init;

import net.minecraft.util.DamageSource;

public class DMADamageSources {
	public static DamageSource CYBER_BEAM = new DamageSource("cyberBeam");
	public static DamageSource SHEARED = new DamageSource("sheared");
	public static DamageSource ELECTROCUTION = new DamageSource("electrocution");
	public static final DamageSource KANTROFFARI_ATTACK = new DamageSource("kantrofarri").bypassArmor();
	public static DamageSource LASER_SCREWDRIVER = new DamageSource("laser_screwdriver");
}
