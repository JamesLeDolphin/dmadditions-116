package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DmAdditions;
import com.swdteam.common.RegistryHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class DMASoundEvents {
	public static final RegistryObject<SoundEvent> PISTOL_SHOOT;
	public static final RegistryObject<SoundEvent> COPPER_HANDBRAKE;
	public static final RegistryObject<SoundEvent> CORAL_HANDBRAKE;
	public static final RegistryObject<SoundEvent> MUSIC_DISC_PFD;
	public static final RegistryObject<SoundEvent> CHRISTMAS_TREE_JINGLE_BELLS;
	public static final RegistryObject<SoundEvent> DALEK_STORM_DEATH;
	public static final RegistryObject<SoundEvent> DALEK_STORM_EXTERMINATE;
	public static final RegistryObject<SoundEvent> LASER_SONIC_SHOOT;


	public static RegistryObject<SoundEvent> buildSound(DeferredRegister<SoundEvent> register, String registryName) {
		return register.register(registryName,
			() -> new SoundEvent(new ResourceLocation(DmAdditions.MODID, registryName)));
	}

	static {
		PISTOL_SHOOT = buildSound(RegistryHandler.SOUNDS, "item.pistol.shoot");
		COPPER_HANDBRAKE = buildSound(RegistryHandler.SOUNDS, "block.handbrake.copperhandbrake");
		CORAL_HANDBRAKE = buildSound(RegistryHandler.SOUNDS, "block.handbrake.coralhandbrake");
		MUSIC_DISC_PFD = buildSound(RegistryHandler.SOUNDS, "music_disc.pfd");
		CHRISTMAS_TREE_JINGLE_BELLS = buildSound(RegistryHandler.SOUNDS, "entity.christmas_tree.jingle_bells");
		DALEK_STORM_DEATH = buildSound(RegistryHandler.SOUNDS, "entity.dalek.storm.death");
		LASER_SONIC_SHOOT = buildSound(RegistryHandler.SOUNDS, "item.sonic.laser.shoot");
		DALEK_STORM_EXTERMINATE = buildSound(RegistryHandler.SOUNDS, "entity.dalek.storm.exterminate");
	}
}
