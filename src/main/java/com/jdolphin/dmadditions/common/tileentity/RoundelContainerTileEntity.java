package com.jdolphin.dmadditions.common.tileentity;

import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import static com.jdolphin.dmadditions.common.init.DMABlockEntities.TILE_ROUNDEL_CONTAINER;
import static com.jdolphin.dmadditions.common.init.DMATags.Blocks.ROUNDEL_CONTAINERS;

public class RoundelContainerTileEntity extends LockableLootTileEntity {
	private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
	private int openCount;

	public RoundelContainerTileEntity() {
		this(TILE_ROUNDEL_CONTAINER.get());
	}

	private RoundelContainerTileEntity(TileEntityType<?> tileEntityType) {
		super(tileEntityType);
	}

	public CompoundNBT save(CompoundNBT compoundNBT) {
		super.save(compoundNBT);
		if (!this.trySaveLootTable(compoundNBT)) {
			ItemStackHelper.saveAllItems(compoundNBT, this.items);
		}

		return compoundNBT;
	}

	public void load(BlockState blockState, CompoundNBT compoundNBT) {
		super.load(blockState, compoundNBT);
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(compoundNBT)) {
			ItemStackHelper.loadAllItems(compoundNBT, this.items);
		}

	}

	public int getContainerSize() {
		return 27;
	}

	protected NonNullList<ItemStack> getItems() {
		return this.items;
	}

	protected void setItems(NonNullList<ItemStack> itemStacks) {
		this.items = itemStacks;
	}

	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.dmadditions.roundel_storage");
	}

	protected Container createMenu(int i, PlayerInventory playerInventory) {
		return ChestContainer.threeRows(i, playerInventory, this);
	}

	public void startOpen(PlayerEntity playerEntity) {
		if (!playerEntity.isSpectator()) {
			if (this.openCount < 0) {
				this.openCount = 0;
			}

			++this.openCount;

			this.scheduleRecheck();
		}

	}

	private void scheduleRecheck() {
		this.level.getBlockTicks().scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 5);
	}

	public void recheckOpen() {
		int i = this.worldPosition.getX();
		int j = this.worldPosition.getY();
		int k = this.worldPosition.getZ();
		this.openCount = ChestTileEntity.getOpenCount(this.level, this, i, j, k);
		if (this.openCount > 0) {
			this.scheduleRecheck();
		} else {
			BlockState blockstate = this.getBlockState();
			if (!blockstate.is(ROUNDEL_CONTAINERS)) {
				this.setRemoved();
				return;
			}
		}

	}

	public void stopOpen(PlayerEntity playerEntity) {
		if (!playerEntity.isSpectator()) {
			--this.openCount;
		}

	}

	private void updateBlockState(BlockState blockState, boolean b) {
		this.level.setBlock(this.getBlockPos(), blockState.setValue(BarrelBlock.OPEN, Boolean.valueOf(b)), 3);
	}

	private void playSound(BlockState blockState, SoundEvent soundEvent) {
		Vector3i vector3i = blockState.getValue(BarrelBlock.FACING).getNormal();
		double d0 = (double) this.worldPosition.getX() + 0.5D + (double) vector3i.getX() / 2.0D;
		double d1 = (double) this.worldPosition.getY() + 0.5D + (double) vector3i.getY() / 2.0D;
		double d2 = (double) this.worldPosition.getZ() + 0.5D + (double) vector3i.getZ() / 2.0D;
		this.level.playSound((PlayerEntity) null, d0, d1, d2, soundEvent, SoundCategory.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
	}
}
