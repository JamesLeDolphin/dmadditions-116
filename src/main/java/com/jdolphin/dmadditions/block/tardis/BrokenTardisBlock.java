package com.jdolphin.dmadditions.block.tardis;

import com.swdteam.common.block.IHaveNoItem;
import com.swdteam.common.block.TileEntityBaseBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

import java.util.function.Supplier;

public class BrokenTardisBlock extends TileEntityBaseBlock.WaterLoggable implements IHaveNoItem {

	public BrokenTardisBlock(Supplier<TileEntity> tileEntitySupplier, Block.Properties properties) {
		super(tileEntitySupplier, properties);
	}
}
