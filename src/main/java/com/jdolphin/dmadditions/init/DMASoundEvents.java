package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.common.RegistryHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class DMASoundEvents {
	public static final RegistryObject<SoundEvent> PISTOL_SHOOT = buildSound(RegistryHandler.SOUNDS, "item.pistol.shoot");
	public static final RegistryObject<SoundEvent> COPPER_HANDBRAKE = buildSound(RegistryHandler.SOUNDS, "block.handbrake.copper_hand_brake");
	public static final RegistryObject<SoundEvent> CORAL_HANDBRAKE = buildSound(RegistryHandler.SOUNDS, "block.handbrake.coral_hand_brake");
	public static final RegistryObject<SoundEvent> MUSIC_DISC_PFD = buildSound(RegistryHandler.SOUNDS, "music_disc.pfd");
	public static final RegistryObject<SoundEvent> CHRISTMAS_TREE_JINGLE_BELLS = buildSound(RegistryHandler.SOUNDS, "entity.christmas_tree.jingle_bells");
	public static final RegistryObject<SoundEvent> DALEK_STORM_DEATH = buildSound(RegistryHandler.SOUNDS, "entity.dalek.storm.death");
	public static final RegistryObject<SoundEvent> DALEK_STORM_EXTERMINATE = buildSound(RegistryHandler.SOUNDS, "entity.dalek.storm.exterminate");
	public static final RegistryObject<SoundEvent> LASER_SONIC_SHOOT = buildSound(RegistryHandler.SOUNDS, "item.sonic.laser.shoot");
	public static final RegistryObject<SoundEvent> BESSIE_HORN = buildSound(RegistryHandler.SOUNDS, "entity.vehicle.bessie.horn");
	public static final RegistryObject<SoundEvent> BESSIE = buildSound(RegistryHandler.SOUNDS, "entity.vehicle.bessie.drive");
	public static final RegistryObject<SoundEvent> RICKROLL = buildSound(RegistryHandler.SOUNDS, "music_disc.rick_roll");
	public static final RegistryObject<SoundEvent> V8_IDLE = buildSound(RegistryHandler.SOUNDS, "entity.vehicle.shopping_cart.v8_idle");
	public static final RegistryObject<SoundEvent> V8_REVVING = buildSound(RegistryHandler.SOUNDS, "entity.vehicle.shopping_cart.v8_revving");


	public static RegistryObject<SoundEvent> buildSound(DeferredRegister<SoundEvent> register, String registryName) {
		return register.register(registryName,
			() -> new SoundEvent(Helper.createAdditionsRL(registryName)));
	}
}
