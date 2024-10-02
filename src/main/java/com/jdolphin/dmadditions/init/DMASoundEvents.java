package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.common.RegistryHandler;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

@SuppressWarnings("unused")
public class DMASoundEvents {
	public static RegistryObject<SoundEvent> PISTOL_SHOOT;
	public static RegistryObject<SoundEvent> COPPER_HANDBRAKE;
	public static RegistryObject<SoundEvent> CORAL_HANDBRAKE;
	public static RegistryObject<SoundEvent> MUSIC_DISC_PFD;
	public static RegistryObject<SoundEvent> CHRISTMAS_TREE_JINGLE_BELLS;
	public static RegistryObject<SoundEvent> DALEK_STORM_DEATH;
	public static RegistryObject<SoundEvent> DALEK_STORM_EXTERMINATE;
	public static RegistryObject<SoundEvent> LASER_SONIC_SHOOT;
	public static RegistryObject<SoundEvent> BESSIE_HORN;
	public static RegistryObject<SoundEvent> BESSIE;
	public static RegistryObject<SoundEvent> PAN;
	public static RegistryObject<SoundEvent> RICKROLL;
	public static RegistryObject<SoundEvent> V8_IDLE;
	public static RegistryObject<SoundEvent> V8_REVVING;
	public static RegistryObject<SoundEvent> MONDAS_CYBER_AMBIENT;
	public static RegistryObject<SoundEvent> MONDAS_CYBER_LASER_ATTACK;
	public static RegistryObject<SoundEvent> PRE_REGEN;
	public static RegistryObject<SoundEvent> REGEN_START;

	public static void init() {
		PISTOL_SHOOT = buildSound(RegistryHandler.SOUNDS, "item.pistol.shoot");
		COPPER_HANDBRAKE = buildSound(RegistryHandler.SOUNDS, "block.handbrake.copper_hand_brake");
		CORAL_HANDBRAKE = buildSound(RegistryHandler.SOUNDS, "block.handbrake.coral_hand_brake");
		MUSIC_DISC_PFD = buildSound(RegistryHandler.SOUNDS, "music_disc.pfd");
		CHRISTMAS_TREE_JINGLE_BELLS = buildSound(RegistryHandler.SOUNDS, "entity.christmas_tree.jingle_bells");
		DALEK_STORM_DEATH = buildSound(RegistryHandler.SOUNDS, "entity.dalek.storm.death");
		DALEK_STORM_EXTERMINATE = buildSound(RegistryHandler.SOUNDS, "entity.dalek.storm.exterminate");
		LASER_SONIC_SHOOT = buildSound(RegistryHandler.SOUNDS, "item.sonic.laser.shoot");
		BESSIE_HORN = buildSound(RegistryHandler.SOUNDS, "entity.vehicle.bessie.horn");
		BESSIE = buildSound(RegistryHandler.SOUNDS, "entity.vehicle.bessie.drive");
		PAN = buildSound(RegistryHandler.SOUNDS, "item.frying_pan.hit");
		RICKROLL = buildSound(RegistryHandler.SOUNDS, "music_disc.rick_roll");
		V8_IDLE = buildSound(RegistryHandler.SOUNDS, "entity.vehicle.shopping_cart.v8_idle");
		V8_REVVING = buildSound(RegistryHandler.SOUNDS, "entity.vehicle.shopping_cart.v8_revving");
		MONDAS_CYBER_AMBIENT = buildSound(RegistryHandler.SOUNDS, "entity.cyberman.mondas.ambient");
		MONDAS_CYBER_LASER_ATTACK = buildSound(RegistryHandler.SOUNDS, "entity.cyberman.mondas.laser_attack");
		PRE_REGEN = buildSound(RegistryHandler.SOUNDS, "entity.player.regen.pre");
		REGEN_START = buildSound(RegistryHandler.SOUNDS, "entity.player.regen.start");
	}

	public static RegistryObject<SoundEvent> buildSound(DeferredRegister<SoundEvent> register, String registryName) {
		return register.register(registryName,
			() -> new SoundEvent(Helper.createAdditionsRL(registryName)));
	}
}
