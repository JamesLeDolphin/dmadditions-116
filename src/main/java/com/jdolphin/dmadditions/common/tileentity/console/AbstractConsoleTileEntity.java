package com.jdolphin.dmadditions.common.tileentity.console;

import com.jdolphin.dmadditions.common.block.tardis.console.AbstractConsoleBlock;
import com.jdolphin.dmadditions.common.entity.control.AbstractControlType;
import com.jdolphin.dmadditions.common.entity.control.TardisControl;
import com.jdolphin.dmadditions.common.init.DMABlockEntities;
import com.jdolphin.dmadditions.common.init.DMAControlTypes;
import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class AbstractConsoleTileEntity extends DMTileEntityBase implements ITickableTileEntity {
	public final List<TardisControl> controls = new ArrayList<>();
	private int controlSpawnCooldown;
	private boolean hasControls = false;
	protected int increment = 1;
	protected ResourceLocation dimIcon;
	public static String TAG_HAS_CONTROLS = "hasControls";

	public AbstractConsoleTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public AbstractConsoleTileEntity() {
		this(DMABlockEntities.CLASSIC_CONSOLE.get());
	}

	@Override
	public void onLoad() {
		super.onLoad();
		this.hasControls = false;
		this.controlSpawnCooldown = 10;
	}

	public ResourceLocation getDimIcon() {
		return dimIcon;
	}

	public void setDimIcon(ResourceLocation dimIcon) {
		this.dimIcon = dimIcon;
		sendUpdates();
	}

	public int getControlIncrement() {
		return this.increment;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}

	public abstract int getControlAmout();

	public abstract void setControlOffset(TardisControl control);

	public void load(@NotNull BlockState state, @NotNull CompoundNBT tag) {
		super.load(state, tag);
		this.hasControls = tag.getBoolean(TAG_HAS_CONTROLS);
		this.increment = tag.getInt("Increment");
		if (tag.contains("Dim")) this.dimIcon = new ResourceLocation(tag.getString("Dim"));
	}

	public @NotNull CompoundNBT save(@NotNull CompoundNBT tag) {
		tag.putBoolean(TAG_HAS_CONTROLS, this.hasControls);
		tag.putInt("Increment", increment);
		if (dimIcon != null) tag.putString("Dim", dimIcon.toString());
		return super.save(tag);
	}

	private void getOrCreateControls() {
		if (level instanceof ServerWorld) {
			this.controls.clear();
			BlockState state = this.getBlockState();
			AbstractConsoleBlock block = (AbstractConsoleBlock) state.getBlock();
			List<TardisControl> entities = this.level.getEntitiesOfClass(TardisControl.class, block.getHitbox().bounds().move(worldPosition).inflate(1.5F));
			if (!entities.isEmpty() && entities.size() == this.getControlAmout()) {
				for (TardisControl tardisControl : entities) {
					if (tardisControl.isAlive()) {
						tardisControl.setMaster(this.worldPosition);
						this.controls.add(tardisControl);
					}
				}
			} else {
				makeControls();
			}
		}
	}

	public void makeControls() {
		if (level != null && this.level instanceof ServerWorld) {
			this.removeControls();
			ServerWorld world = (ServerWorld) level;

			BlockPos pos = this.getBlockPos();
			BlockState state = this.level.getBlockState(pos);


			for (Map.Entry<ResourceLocation, AbstractControlType> entry : DMAControlTypes.ALL.entrySet()) {
				AbstractControlType type = entry.getValue();

				TardisControl control = new TardisControl(world, type);
				this.setControlOffset(control);
				if (control.getControlType().getOffset() != Vector3d.ZERO) {
					Direction facing = state.getValue(AbstractConsoleBlock.FACING);
					Vector3d offset = type.getOffset();

					Vector3d rotOffset = offset;
					switch (facing) {
						case NORTH:
							rotOffset = new Vector3d(offset.x, offset.y, offset.z);
							break;
						case SOUTH:
							rotOffset = new Vector3d(-offset.x, offset.y, -offset.z);
							break;
						case WEST:
							rotOffset = new Vector3d(offset.z, offset.y, -offset.x);
							break;
						case EAST:
							rotOffset = new Vector3d(-offset.z, offset.y, offset.x);
							break;
						default:
							break;
					}

					control.setPos(pos.getX() + 0.5 + rotOffset.x(), pos.getY() + rotOffset.y(), pos.getZ() + 0.5 + rotOffset.z());

					control.setMaster(pos);
					world.addFreshEntity(control);
					this.controls.add(control);

					this.hasControls = true;
				}
			}
		}
	}

	public void removeControls() {
		this.controls.forEach(Entity::remove);
		this.controls.clear();
		this.hasControls = false;
	}

	public void setRemoved() {
		removeControls();
		super.setRemoved();
	}

	@Override
	public void tick() {
		if (controlSpawnCooldown > 0) {
			controlSpawnCooldown--;
			if (controlSpawnCooldown == 0 && !this.hasControls) this.getOrCreateControls();
		}
		if (!this.hasControls && controlSpawnCooldown == 0) this.getOrCreateControls();
	}
}
