package com.jdolphin.dmadditions.entity.control;

import com.jdolphin.dmadditions.init.DMAEntities;
import com.jdolphin.dmadditions.tileentity.ConsoleTileEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.Location;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.util.ChatUtil;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class TardisControl extends Entity {
	private static final DataParameter<Integer> DATA_ID_HURT = EntityDataManager.defineId(TardisControl.class, DataSerializers.INT);
	private static final DataParameter<String> DATA_TYPE = EntityDataManager.defineId(TardisControl.class, DataSerializers.STRING);
	private static final DataParameter<BlockPos> DATA_MASTER_POS = EntityDataManager.defineId(TardisControl.class, DataSerializers.BLOCK_POS);

	public static final String TAG_TYPE = "ControlType";
	public static final String TAG_MASTER_POS = "MasterPos";
	protected int cooldown;

	public TardisControl(EntityType<?> type, World world) {
		super(type, world);
	}

	@OnlyIn(Dist.CLIENT)
	public boolean shouldShowName() {
		return true;
	}

	public TardisControl(World world, ControlType type, ConsoleTileEntity master) {
		super(DMAEntities.CONTROL.get(), world);
		setType(type);
		setMaster(master);

	}

	public void setMaster(ConsoleTileEntity tile) {
		this.entityData.set(DATA_MASTER_POS, tile.getBlockPos());
	}

	public ConsoleTileEntity getMaster() {
		TileEntity tile = level.getBlockEntity(this.entityData.get(DATA_MASTER_POS));
		if (tile instanceof ConsoleTileEntity) {
			return (ConsoleTileEntity) tile;
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
		return (hand == Hand.MAIN_HAND && cooldown == 0) ? this.getAction(player) : ActionResultType.FAIL;
	}

	@Override
	public @NotNull Vector3d position() {
		return this.getControlType().offset;
	}

	@Override
	public void tick() {
		super.tick();
		if (getMaster() != null) {
			if (!getMaster().controls.contains(this)) getMaster().controls.add(this);
		} else this.remove();
		cooldown--;
	}

	public void setType(ControlType type) {
		setType(type.getName());
	}

	public void setType(String type) {
		this.entityData.set(DATA_TYPE, type);
	}

	public ControlType getControlType() {
		return ControlType.getByName(this.entityData.get(DATA_TYPE));
	}


	@Override
	protected void defineSynchedData() {
		this.entityData.define(DATA_ID_HURT, 0);
		this.entityData.define(DATA_TYPE, ControlType.FLIGHT.getName());
		this.entityData.define(DATA_MASTER_POS, BlockPos.ZERO);
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT tag) {
		setType(tag.getString(TAG_TYPE));
		setMaster((ConsoleTileEntity) level.getBlockEntity(NBTUtil.readBlockPos((CompoundNBT) tag.get(TAG_MASTER_POS))));
	}


	@Override
	protected void addAdditionalSaveData(CompoundNBT tag) {
		tag.putString(TAG_TYPE, getControlType().getName());
		tag.put(TAG_MASTER_POS, NBTUtil.writeBlockPos(getMaster() == null ? BlockPos.ZERO : getMaster().getBlockPos()));
	}

	@Override
	public @NotNull IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	private void setDoors(TardisTileEntity tardis, boolean open) {
		tardis.setDoor(TardisDoor.BOTH, open, TardisTileEntity.DoorSource.INTERIOR);
		tardis.setDoor(TardisDoor.BOTH, open, TardisTileEntity.DoorSource.TARDIS);
	}

	private void setCooldown() {
		this.cooldown = 20;
	}

	public ActionResultType getAction(PlayerEntity player) {
		if (!level.isClientSide() && Helper.isTardis(level)) {
			TardisData data = DMTardis.getTardisFromInteriorPos(this.blockPosition());
			if (data != null) {
				Location location = data.getCurrentLocation();
				switch (this.getControlType()) {
					case DOOR: {
							if (data.isLocked()) {
								setCooldown();
								player.displayClientMessage(new StringTextComponent(TextFormatting.YELLOW + DMTranslationKeys.TARDIS_IS_LOCKED.getString()), true);
								return ActionResultType.FAIL;
							}

							if (data.isInFlight()) {
								setCooldown();
								player.displayClientMessage(new StringTextComponent(TextFormatting.YELLOW + DMTranslationKeys.TARDIS_IN_FLIGHT.getString()), true);
								return ActionResultType.FAIL;
							}

							if (level.getServer() != null) {
								ServerWorld serverWorld = level.getServer().getLevel(data.getCurrentLocation().dimensionWorldKey());
								if (serverWorld != null) {
									TileEntity tile = serverWorld.getBlockEntity(location.getPosition().toBlockPos());

									if (tile instanceof TardisTileEntity) {
										TardisTileEntity tardis = (TardisTileEntity) tile;
										boolean isOpen = tardis.doorOpenLeft || tardis.doorOpenRight;
										TranslationTextComponent text = new TranslationTextComponent(isOpen ? "notice.dmadditions.close" : "notice.dmadditions.open");
										setCooldown();
										setDoors(tardis, !isOpen);
										data.setDoorOpen(!isOpen);
										player.displayClientMessage(new StringTextComponent(TextFormatting.GREEN + text.getString()), true);
										return ActionResultType.SUCCESS;
									}
								}
							}
					}
					case FLIGHT: {
						BlockPos pos = Helper.vec3ToBlockPos(this.position());
						if (data.isInFlight()) {
							if (data.timeLeft() == 0.0D) {
								if (TardisActionList.remat(player, level, data)) {
									Helper.playSound(level, pos, DMSoundEvents.TARDIS_REMAT.get(), SoundCategory.BLOCKS);
									Helper.playSound(level, pos, DMSoundEvents.TARDIS_CONTROLS_LEVER.get(), SoundCategory.BLOCKS);
									setCooldown();
									return ActionResultType.SUCCESS;
								}
							} else {
								int seconds = (int) data.timeLeft();
								String s = seconds + "s";
								ChatUtil.sendError(player, new TranslationTextComponent("notice.dalekmod.tardis.traveling", new StringTextComponent(s)), ChatUtil.MessageType.CHAT);
								return ActionResultType.FAIL;
							}
						} else if (TardisActionList.demat(player, level, data)) {
							Helper.playSound(level, pos, DMSoundEvents.TARDIS_DEMAT.get(), SoundCategory.BLOCKS);
							Helper.playSound(level, pos, DMSoundEvents.TARDIS_CONTROLS_LEVER.get(), SoundCategory.BLOCKS);
							setCooldown();
							return ActionResultType.SUCCESS;
						}
					}
					case LOCK: {
						data.setLocked(!data.isLocked());
						data.save();
						if (data.isLocked()) {
							if (level.getServer() != null) {
								ServerWorld serverWorld = level.getServer().getLevel(data.getCurrentLocation().dimensionWorldKey());
								if (serverWorld != null) {
									TileEntity tile = serverWorld.getBlockEntity(location.getPosition().toBlockPos());

									if (tile instanceof TardisTileEntity) {
										TardisTileEntity tardis = (TardisTileEntity) tile;
										setCooldown();
										setDoors(tardis, false);
										player.displayClientMessage(new TranslationTextComponent(data.isLocked() ?
											"notice.dmadditions.lock" : "notice.dmadditions.unlock").withStyle(TextFormatting.GREEN), true);
										return ActionResultType.SUCCESS;
									}
								}
							}
						}
					}
					default: {
						ChatUtil.sendMessageToPlayer(player, "Not yet implemented", ChatUtil.MessageType.CHAT);
					}
				}
				return ActionResultType.PASS;
			}
		} else if (!Helper.isTardis(level)) ChatUtil.sendMessageToPlayer(player, new TranslationTextComponent("entity.dmadditions.console.fail.dim"), ChatUtil.MessageType.CHAT);
        return ActionResultType.FAIL;
    }

	public enum ControlType {
		DOOR("door", new Vector3d(0.5, 0.5, 0)),
		LOCK("lock", new Vector3d(0.5, 0, 0.5)),
		FLIGHT("flight", new Vector3d(0, 0, 0)),
		COORD_MODIFIER("coord_modifier", new Vector3d(0, 0, 0)),
		X_POSITIVE("x_positive", new Vector3d(0, 0, 0)),
		X_NEGATIVE("x_negative", new Vector3d(0, 0, 0)),
		Y_POSITIVE("y_positive", new Vector3d(0, 0, 0)),
		Y_NEGATIVE("y_negative", new Vector3d(0, 0, 0)),
		Z_POSITIVE("z_positive", new Vector3d(0, 0, 0)),
		Z_NEGATIVE("z_negative", new Vector3d(0, 0, 0)),
		;

		private String name;
		private Vector3d offset;
		ControlType(String name, Vector3d offset) {
			this.name = name;
			this.offset = offset;
		}

		public void setOffset(Vector3d newOffset) {
			this.offset = newOffset;
		}

		public static ControlType getByName(String name) {
			return ControlType.valueOf(name.toUpperCase());
		}

		public String getName() {
			return name;
		}

		public Vector3d getOffset() {
			return offset;
		}
	}
}
