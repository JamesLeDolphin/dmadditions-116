package com.jdolphin.dmadditions.common.network;

import com.jdolphin.dmadditions.common.item.LaserScrewdriverItem;
import com.swdteam.util.ChatUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SBToggleLaserScrewdriverMode {

	public SBToggleLaserScrewdriverMode() {
	}

	public SBToggleLaserScrewdriverMode(PacketBuffer buf) {
	}

	public void encode(PacketBuffer buf) {
	}

	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		ServerPlayerEntity player = supplier.get().getSender();
		assert player != null;
		try {
			ItemStack gun = player.getMainHandItem();
			if (gun.getItem() instanceof LaserScrewdriverItem) {
				LaserScrewdriverItem item = (LaserScrewdriverItem) gun.getItem();
				item.toggleShootMode();

				if (item.shootMode()) {
					ChatUtil.sendError(player,
						new TranslationTextComponent("item.dmadditions.laser_screwdriver.mode.laser"),
						ChatUtil.MessageType.STATUS_BAR);
				} else {
					ChatUtil.sendCompletedMsg(player,
						new TranslationTextComponent("item.dmadditions.laser_screwdriver.mode.sonic"),
						ChatUtil.MessageType.STATUS_BAR);
				}

				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
