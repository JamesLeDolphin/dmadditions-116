package com.jdolphin.dmadditions.tileentity;

import com.swdteam.common.block.RotatableTileEntityBase;
import com.swdteam.common.block.tardis.CoordPanelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.ChestBlock;

import java.util.List;
import java.util.function.Supplier;

public class TimeKeeperConsole extends RotatableTileEntityBase.WaterLoggable {
	public TimeKeeperConsole(Supplier<TileEntity> tileEntitySupplier, int light) {
		super(tileEntitySupplier, light);
	}

	public static final IntegerProperty BUTTON_PRESSED = IntegerProperty.create("button_pressed", 0, 8);
	public static List<CoordPanelBlock.CoordPanelButtons> buttons;
	public Supplier<TileEntity> tileEntitySupplier;
	private Boolean didPressButton = false;
	public BlockRenderType getRenderShape(BlockState p_149645_1_) {
		return BlockRenderType.MODEL;


	}
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(new Property[]{BUTTON_PRESSED});
	}
	//We have permission to use this in the update
}
