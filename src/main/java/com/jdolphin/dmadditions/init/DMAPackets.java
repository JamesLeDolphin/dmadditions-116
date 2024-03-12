package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.network.SBToggleLaserScrewdriverMode;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class DMAPackets {
	static int index = 0;

	private static final String PROTOCOL_VERSION = "1";

	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
		Helper.createAdditionsRL("main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals,
		PROTOCOL_VERSION::equals);

	public static void init() {
		INSTANCE.messageBuilder(SBToggleLaserScrewdriverMode.class, index++, NetworkDirection.PLAY_TO_SERVER)
			.encoder(SBToggleLaserScrewdriverMode::encode)
			.decoder(SBToggleLaserScrewdriverMode::new)
			.consumer(SBToggleLaserScrewdriverMode::handle)
			.add();
	}
}
