package com.jdolphin.dmadditions.tileentity;

import com.jdolphin.dmadditions.init.DMABlockEntities;
import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public class ReddashStatueTileEntity extends DMTileEntityBase implements IForgeRegistryEntry<ReddashStatueTileEntity> {
	public ReddashStatueTileEntity() {
		super((TileEntityType) DMABlockEntities.TILE_REDDASH_STATUE.get());
	}

	@Override
	public ReddashStatueTileEntity setRegistryName(ResourceLocation name) {
		return null;
	}

	@Nullable
	@Override
	public ResourceLocation getRegistryName() {
		return null;
	}

	@Override
	public Class<ReddashStatueTileEntity> getRegistryType() {
		return null;
	}
}