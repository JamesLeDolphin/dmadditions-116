package com.jdolphin.dmadditions.entity.control;

import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.util.ChatUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class TardisControl extends Entity {
	private static final DataParameter<Integer> DATA_ID_HURT = EntityDataManager.defineId(TardisControl.class, DataSerializers.INT);

	public TardisControl(EntityType<?> type, World world) {
		super(type, world);
	}

	public boolean canCollideWith(@NotNull Entity entity) {
		return false;
	}

	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	public boolean hurt(DamageSource source, float f) {
		if (!this.level.isClientSide() && source.isCreativePlayer()) {
			World level = this.level;
			PlayerEntity player = (PlayerEntity) source.getEntity();
			System.out.print("awdawdawdawsdadaw");
			BlockPos pos = Helper.vec3ToBlockPos(this.position());
			if (!level.isClientSide()) {
				if (Helper.isTardis(level)) {
					TardisData data = DMTardis.getTardisFromInteriorPos(pos);
					if (data.isInFlight()) {
						if (data.timeLeft() == 0.0D) {
							if (TardisActionList.remat(player, level, data)) {
								Helper.playSound(level, pos, DMSoundEvents.TARDIS_REMAT.get(), SoundCategory.BLOCKS);
							}
						} else {
							int seconds = (int) data.timeLeft();
							String s = seconds + "s";
							ChatUtil.sendError(player, new TranslationTextComponent("notice.dalekmod.tardis.traveling", new StringTextComponent(s)), ChatUtil.MessageType.CHAT);

						}
					} else if (TardisActionList.demat(player, level, data)) {
						Helper.playSound(level, pos, DMSoundEvents.TARDIS_DEMAT.get(), SoundCategory.BLOCKS);
					}
				}
			}
		}
		return false;
	}

	public @NotNull ActionResultType interact(@NotNull PlayerEntity player, @NotNull Hand hand) {
		World level = this.level;
		System.out.print("awdawdawdawsdadaw");
		BlockPos pos = Helper.vec3ToBlockPos(this.position());
		if (!level.isClientSide()) {
			if (Helper.isTardis(level)) {
				TardisData data = DMTardis.getTardisFromInteriorPos(pos);
				if (data.isInFlight()) {
					if (data.timeLeft() == 0.0D) {
						if (TardisActionList.remat(player, level, data)) {
							Helper.playSound(level, pos, DMSoundEvents.TARDIS_REMAT.get(), SoundCategory.BLOCKS);
							return ActionResultType.SUCCESS;
						}
					} else {
						int seconds = (int) data.timeLeft();
						String s = seconds + "s";
						ChatUtil.sendError(player, new TranslationTextComponent("notice.dalekmod.tardis.traveling", new StringTextComponent(s)), ChatUtil.MessageType.CHAT);
						return ActionResultType.SUCCESS;
					}
				} else if (TardisActionList.demat(player, level, data)) {
					Helper.playSound(level, pos, DMSoundEvents.TARDIS_DEMAT.get(), SoundCategory.BLOCKS);
					return ActionResultType.SUCCESS;
				}
			} System.out.print("not tardis dim :(");
			return ActionResultType.PASS;
		}
		return ActionResultType.PASS;
	}

	@Override
	public @NotNull ActionResultType interactAt(@NotNull PlayerEntity player, Vector3d vector3d, @NotNull Hand hand) {
		return this.interact(player, hand);
	}

	@Override
	public void tick() {
		super.tick();
		World world = this.level;
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(DATA_ID_HURT, 0);
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT tag) {

	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT tag) {

	}

	@Override
	public @NotNull IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
