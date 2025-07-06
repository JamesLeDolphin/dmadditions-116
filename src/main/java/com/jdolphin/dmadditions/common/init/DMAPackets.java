package com.jdolphin.dmadditions.common.init;

import com.jdolphin.dmadditions.common.network.*;
import com.jdolphin.dmadditions.common.util.Helper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;
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
		INSTANCE.messageBuilder(SBSonicBlasterInteractPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
			.encoder(SBSonicBlasterInteractPacket::encode)
			.decoder(SBSonicBlasterInteractPacket::new)
			.consumer(SBSonicBlasterInteractPacket::handle)
			.add();
		INSTANCE.messageBuilder(SBLockpickPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
			.encoder(SBLockpickPacket::encode)
			.decoder(SBLockpickPacket::new)
			.consumer(SBLockpickPacket::handle)
			.add();
		INSTANCE.messageBuilder(SBGenerateBrokenInteriorPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
			.encoder(SBGenerateBrokenInteriorPacket::encode)
			.decoder(SBGenerateBrokenInteriorPacket::new)
			.consumer(SBGenerateBrokenInteriorPacket::handle)
			.add();
		INSTANCE.registerMessage(index++, CBOpenGUIPacket.class, CBOpenGUIPacket::encode, CBOpenGUIPacket::new, CBOpenGUIPacket::handle);
		INSTANCE.registerMessage(index++, CBSyncPlayerPacket.class, CBSyncPlayerPacket::encode, CBSyncPlayerPacket::decode, CBSyncPlayerPacket::handle);
	}

	public static void send(Object packet) {
		INSTANCE.sendToServer(packet);
	}

	public static void sendToAll(ServerWorld world, Object packet) {
		MinecraftServer server = world.getServer();
		IPacket<?> iPacket = INSTANCE.toVanillaPacket(packet, NetworkDirection.PLAY_TO_CLIENT);
		server.getPlayerList().broadcastAll(iPacket, world.dimension());
	}

	public static void sendTo(ServerPlayerEntity playerEntity, Object packet) {
		INSTANCE.sendTo(packet, playerEntity.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
	}
}
