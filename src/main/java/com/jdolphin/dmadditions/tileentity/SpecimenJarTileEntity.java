package com.jdolphin.dmadditions.tileentity;

import com.google.common.collect.Lists;
import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.init.DMATags;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.List;

import static com.jdolphin.dmadditions.init.DMABlockEntities.TILE_SPECIMEN_JAR;

public class SpecimenJarTileEntity extends DMTileEntityBase {
	public SpecimenJarTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public SpecimenJarTileEntity(){
		this(TILE_SPECIMEN_JAR.get());
	}

	private ItemStack specimen = ItemStack.EMPTY;


	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		if (nbt.contains("Item")) {
			this.specimen = ItemStack.of(nbt.getCompound("Item"));
		}
		super.load(state, nbt);
	}
	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		if (this.specimen != null) {
			CompoundNBT tag = new CompoundNBT();
			nbt.put("Item", this.specimen.save(tag));
		}
		return super.save(nbt);
	}

	public boolean hasSpecimen() {
		return specimen != null && !specimen.isEmpty();
	}

	public boolean acceptSpecimen(Item specimenIn) {
		return DMATags.Items.SPECIMEN_JAR_ACCEPTS.contains(specimenIn);
	}

	public void setSpecimen(ItemStack specimenIn) {
		if (hasSpecimen()) return;
		if (acceptSpecimen(specimenIn.getItem())) {
			specimen = specimenIn;
			specimen.setCount(1);
			this.setChanged();
		}
		else specimen = ItemStack.EMPTY;
	}

	public ItemStack getSpecimen() {
		return this.specimen;
	}
}
