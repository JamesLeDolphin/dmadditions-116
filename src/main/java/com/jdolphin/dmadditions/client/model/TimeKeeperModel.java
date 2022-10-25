package com.jdolphin.dmadditions.client.model;


import com.mojang.datafixers.util.Either;
import net.minecraft.client.renderer.model.*;
import net.minecraft.util.ResourceLocation;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class TimeKeeperModel extends BlockModel {

	public TimeKeeperModel(@Nullable ResourceLocation resourceLocation, List<BlockPart> blockParts, Map<String, Either<RenderMaterial, String>> map, boolean b, @Nullable GuiLight guiLight, ItemCameraTransforms transforms, List<ItemOverride> list) {
		super(resourceLocation, blockParts, map, false, guiLight, transforms, list);
	}
}
