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
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class TardisControl extends Entity {
	private static final DataParameter<Integer> DATA_ID_HURT = EntityDataManager.defineId(TardisControl.class, DataSerializers.INT);
	protected int cooldown;

	public TardisControl(EntityType<?> type, World world) {
		super(type, world);
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
		return this.getEffect(player);
	}

	@Override
	public void tick() {
		super.tick();
		if (cooldown > 0) cooldown--;
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

	public ActionResultType getEffect(PlayerEntity player) {
		return ActionResultType.PASS;
	}
}
