package com.jdolphin.dmadditions.client;

import com.jdolphin.dmadditions.DmAdditions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
	modid = DmAdditions.MODID,
	bus = Mod.EventBusSubscriber.Bus.MOD,
	value = {Dist.CLIENT}
)
public class ClientModEvents {

}
