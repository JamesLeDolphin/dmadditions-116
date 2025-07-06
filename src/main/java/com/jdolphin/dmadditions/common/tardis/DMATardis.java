package com.jdolphin.dmadditions.common.tardis;

import com.jdolphin.dmadditions.common.util.DMAData;
import com.jdolphin.dmadditions.common.util.Helper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.util.List;


public class DMATardis {

	public static BrokenTardisData getOrCreate(int id, MinecraftServer server) {
		if (id != -1) {
			DMAData data = Helper.getDMAData(server);
			List<BrokenTardisData> tardisList = data.brokenTardisData;

			BrokenTardisData tardisData = tardisList.get(id);
			if (tardisData.id == id) return tardisData;
			else {
				for (BrokenTardisData data1 : tardisList) {
					if (data1.id == id && !data1.available) {
						return data1;
					}
				}
			}
		}
		return create(server);
	}

	public static void setAvailable(BrokenTardisData data, MinecraftServer server ) {
		data.available = true;
		data.open = false;
		data.has_generated = false;
		data.worldPos = BlockPos.ZERO;
		saveData(data, server);
	}

	public static void saveData(BrokenTardisData data, MinecraftServer server) {
		DMAData dmaData = Helper.getDMAData(server);
		List<BrokenTardisData> tardisList = dmaData.brokenTardisData;

		for (BrokenTardisData tardisData : tardisList) {
			if (tardisData.id == data.id) {
				tardisData.open = data.open;
				tardisData.has_generated = data.has_generated;
				tardisData.available = data.available;
				tardisData.worldPos = data.worldPos;
				Helper.saveDMAData(server, dmaData);
				break;
			}
		}
	}

	public static BrokenTardisData create(MinecraftServer server) {
		int tardisID = generateNextFreeID(server);
		BrokenTardisData data = new BrokenTardisData(tardisID);
		saveData(data, server);
		return data;
	}

	public static int generateNextFreeID(MinecraftServer server) {
		DMAData data = Helper.getDMAData(server);
		List<BrokenTardisData> brokenTardisData = data.brokenTardisData;
		if (brokenTardisData != null) {
			for (int i = 0; i < brokenTardisData.size(); i++)  {
				BrokenTardisData tardisData = brokenTardisData.get(i);
				if (tardisData != null) {
					if (tardisData.available) {
						return tardisData.id;
					}

				} else return i;
			}

			BrokenTardisData tardisData = new BrokenTardisData(server);
			brokenTardisData.add(tardisData);
			Helper.saveDMAData(server, data);
			return tardisData.id;
		} else {
			return 0;
		}
	}
}
