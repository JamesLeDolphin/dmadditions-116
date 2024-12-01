package com.jdolphin.dmadditions.tardismail;

import com.swdteam.common.entity.KerblamManEntity;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMEntities;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.util.math.Position;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class TardisMail {
	public static boolean send(MinecraftServer server, TardisData tardisData, ItemStack... items) {
		Position pos = tardisData.getInteriorSpawnPosition();
		float rot = tardisData.getInteriorSpawnRotation();

		KerblamManEntity entity = new KerblamManEntity(DMEntities.KERBLAM_MAN.get(),
				server.getLevel(DMDimensions.TARDIS));

		entity.teleportTo(pos.x(), pos.y(), pos.z());
		entity.setYBodyRot(rot);
		entity.addPackages(items);

		return entity.level.addFreshEntity(entity);
	}
}
