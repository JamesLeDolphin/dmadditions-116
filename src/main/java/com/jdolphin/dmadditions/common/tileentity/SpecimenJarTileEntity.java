package com.jdolphin.dmadditions.common.tileentity;

import com.jdolphin.dmadditions.common.init.DMATags;
import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

import static com.jdolphin.dmadditions.common.init.DMABlockEntities.TILE_SPECIMEN_JAR;

public class SpecimenJarTileEntity extends DMTileEntityBase {
	public SpecimenJarTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public SpecimenJarTileEntity(){
		this(TILE_SPECIMEN_JAR.get());
	}

	private ItemStack specimen;


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

	public void emptySpecimen() {
		this.specimen = ItemStack.EMPTY;
		this.setChanged();
	}

	public void setSpecimen(ItemStack specimenIn) {
		if (!specimenIn.getItem().equals(Items.AIR) && acceptSpecimen(specimenIn.getItem())) {
			specimen = specimenIn.copy();
			specimen.setCount(1);
			this.setChanged();
		}
	}

	public ItemStack getSpecimen() {
		return this.specimen == null ? ItemStack.EMPTY : this.specimen;
	}
}