package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.network.*;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.entity.player.ServerPlayerEntity;
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
		INSTANCE.messageBuilder(SBLocatePlayerPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
			.encoder(SBLocatePlayerPacket::encode)
			.decoder(SBLocatePlayerPacket::new)
			.consumer(SBLocatePlayerPacket::handle)
			.add();
		INSTANCE.messageBuilder(SBSonicInteractPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
			.encoder(SBSonicInteractPacket::encode)
			.decoder(SBSonicInteractPacket::new)
			.consumer(SBSonicInteractPacket::handle)
			.add();
		INSTANCE.messageBuilder(SBVMInteractionPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
			.encoder(SBVMInteractionPacket::encode)
			.decoder(SBVMInteractionPacket::new)
			.consumer(SBVMInteractionPacket::handle)
			.add();
		INSTANCE.messageBuilder(SBSonicBlasterInteractPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
			.encoder(SBSonicBlasterInteractPacket::encode)
			.decoder(SBSonicBlasterInteractPacket::new)
			.consumer(SBSonicBlasterInteractPacket::handle)
			.add();
		INSTANCE.registerMessage(index++, CBOpenGUIPacket.class, CBOpenGUIPacket::encode, CBOpenGUIPacket::decode, CBOpenGUIPacket::handle);
		INSTANCE.registerMessage(index++, CBSyncPlayerPacket.class, CBSyncPlayerPacket::encode, CBSyncPlayerPacket::decode, CBSyncPlayerPacket::handle);
	}

	public static void sendTo(ServerPlayerEntity playerEntity, Object packet) {
		INSTANCE.sendTo(packet, playerEntity.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
	}
}
