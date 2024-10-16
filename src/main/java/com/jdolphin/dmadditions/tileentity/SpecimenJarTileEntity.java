package com.jdolphin.dmadditions.tileentity;

import com.jdolphin.dmadditions.init.DMAItems;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

import java.util.ArrayList;
import java.util.List;

import static com.jdolphin.dmadditions.init.DMABlockEntities.TILE_SPECIMEN_JAR;

public class SpecimenJarTileEntity extends DMTileEntityBase {
	public SpecimenJarTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public SpecimenJarTileEntity() {
		this(TILE_SPECIMEN_JAR.get());
	}

	private static List<Item> allowedSpecimens;

	public static List<Item> getAllowedSpecimens() {
		if (allowedSpecimens != null) return allowedSpecimens;
		List<Item> allowed = new ArrayList<>();

		allowed.add(DMAItems.KANTROFARRI_SPAWNER.get()); // 0
		allowed.add(DMAItems.KANTROFARRI.get()); // 1
		allowed.add(DMItems.RAW_DALEK_MUTANT.get()); // 2

		allowedSpecimens = allowed;
		return allowed;
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
			this.specimen.save(tag);
			nbt.put("Item", tag);
		} else {
			nbt.remove("Item");
		}
		return super.save(nbt);
	}

	public boolean hasSpecimen() {
		return !specimen.isEmpty();
	}

	public boolean acceptSpecimen(Item specimenIn) {
		return getAllowedSpecimens().contains(specimenIn);
	}

	public static int getSpecimenIndex(Item specimenIn) {
		return getAllowedSpecimens().indexOf(specimenIn);
	}

	public int getSpecimenIndex() {
		return getSpecimenIndex(specimen.getItem());
	}

	public void setSpecimen(ItemStack specimenIn) {
		if (acceptSpecimen(specimenIn.getItem())) {
			specimen = specimenIn.copy();
			specimen.setCount(1);
		} else specimen = ItemStack.EMPTY;
	}

	public ItemStack getSpecimen() {
		return specimen;
	}
}
