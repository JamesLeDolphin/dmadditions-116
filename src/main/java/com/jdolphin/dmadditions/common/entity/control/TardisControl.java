package com.jdolphin.dmadditions.common.entity.control;

import com.jdolphin.dmadditions.common.init.DMAControlTypes;
import com.jdolphin.dmadditions.common.init.DMAEntities;
import com.jdolphin.dmadditions.common.tileentity.console.AbstractConsoleTileEntity;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class TardisControl extends Entity {
	private static final DataParameter<String> DATA_TYPE = EntityDataManager.defineId(TardisControl.class, DataSerializers.STRING);
	private static final DataParameter<BlockPos> DATA_MASTER_POS = EntityDataManager.defineId(TardisControl.class, DataSerializers.BLOCK_POS);

	public static final String TAG_TYPE = "ControlType";
	public static final String TAG_MASTER_POS = "MasterPos";

	public TardisControl(EntityType<?> type, World world) {
		super(type, world);
	}

	public TardisControl(World world, AbstractControlType type) {
		super(DMAEntities.CONTROL.get(), world);
		setType(type);
	}

    @OnlyIn(Dist.CLIENT)
	public boolean shouldShowName() {
		return true;
	}


	public void setMaster(BlockPos tile) {
		this.entityData.set(DATA_MASTER_POS, tile);
	}

	public AbstractConsoleTileEntity getConsole() {
		TileEntity tile = level.getBlockEntity(this.entityData.get(DATA_MASTER_POS));
		if (tile instanceof AbstractConsoleTileEntity) {
			return (AbstractConsoleTileEntity) tile;
		}
		return null;
	}

	public boolean canCollideWith(@NotNull Entity entity) {
		return true;
	}

	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	public @NotNull ActionResultType interact(@NotNull PlayerEntity player, @NotNull Hand hand) {
		if (!this.level.isClientSide()) {
			if (hand == Hand.MAIN_HAND) {
				ServerWorld world = (ServerWorld) level;
				TardisData data = DMTardis.getTardisFromInteriorPos(this.blockPosition());
				if (data != null) {
					this.getControlType().onPress(world, player, this.blockPosition(), data, this);
				}
			}
		}
		return ActionResultType.SUCCESS;
	}

	public @NotNull Vector3d offset() {
		return this.getControlType().offset;
	}

	@Override
	public void tick() {
		super.tick();
		if (getConsole() != null) {
			AbstractConsoleTileEntity console = getConsole();
			if (!console.controls.contains(this)) {
				console.controls.add(this);
			}
		} else remove();
	}

	public void setType(AbstractControlType type) {
		setType(type.getName().toString());
	}

	public void setType(String type) {
		this.entityData.set(DATA_TYPE, type);
	}

	public AbstractControlType getControlType() {
		String type = this.entityData.get(DATA_TYPE);
		ResourceLocation location = new ResourceLocation(type);
		return DMAControlTypes.ALL.get(location);
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(DATA_TYPE, DMAControlTypes.FLIGHT.getName().toString());
		this.entityData.define(DATA_MASTER_POS, BlockPos.ZERO);
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT tag) {
		if (tag.contains(TAG_TYPE)) setType(tag.getString(TAG_TYPE)); else this.remove();
		if (tag.contains(TAG_MASTER_POS)) setMaster(NBTUtil.readBlockPos((CompoundNBT) tag.get(TAG_MASTER_POS))); else this.remove();
	}


	@Override
	protected void addAdditionalSaveData(CompoundNBT tag) {
		tag.putString(TAG_TYPE, getControlType().getName().toString());
		if (getConsole() != null) tag.put(TAG_MASTER_POS, NBTUtil.writeBlockPos(getConsole().getBlockPos()));
	}

	@Override
	public @NotNull IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
