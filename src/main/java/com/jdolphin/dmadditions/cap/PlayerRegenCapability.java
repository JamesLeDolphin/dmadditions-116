package com.jdolphin.dmadditions.cap;

import com.jdolphin.dmadditions.init.DMAPackets;
import com.jdolphin.dmadditions.network.CBSyncPlayerPacket;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.util.ChatUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class PlayerRegenCapability implements IPlayerRegenCap {
	private final String TAG_REGEN_AMOUNT = "regens";
	private final String TAG_POSTPONE = "postponedFor";
	private final String TAG_CAN_POSTPONE = "canPostpone";
	private final String TAG_PRE_REGEN = "preRegenTime";
	private final PlayerEntity player;
	private final int maxRegens = 12;
	private final int minRegens = 0;
	private int postponeTime = 0;
	private boolean canPostpone = true;
	private int preRegenTime = Helper.seconds(15);
	private int regenTicks;
	private int currentRegens;

	public PlayerRegenCapability(PlayerEntity player) {
	this.player = player;
	}

	@Override
	public void tick() {
		if (postponed()) {
			postponeTime--;
			Helper.print(postponeTime);
		}
		if (!this.player.isSpectator() && !player.isCreative()) Helper.print(this.currentRegens);

		if (isPreRegen()) preRegenTime--;
		if (preRegenTime == 5 && !postponed() && hasRegens() && this.regenTicks == 0) {
			this.regenerate();
		}

		if (regenTicks == Helper.seconds(30)) {
			regenTicks = 0;
		}
	}

	@Override
	public boolean postponed() {
		return this.postponeTime > 0;
	}

	@Override
	public int getPostponeTime() {
		return this.postponeTime;
	}

	@Override
	public boolean canPostpone() {
		return this.hasRegens() && !postponed() && isPreRegen() && this.canPostpone;
	}

	@Override
	public void postpone() {
		ChatUtil.sendMessageToPlayer(player, new StringTextComponent("You've postponed your regeneration for 5 minutes").withStyle(TextFormatting.GREEN),
			ChatUtil.MessageType.CHAT);
		this.postponeTime = Helper.minutes(5);
		this.canPostpone = false;
	}

	@Override
	public int getMaxRegens() {
		return this.maxRegens;
	}

	@Override
	public boolean hasRegens() {
        return currentRegens > minRegens;
    }

	@Override
	public void setPreRegen() {
		this.preRegenTime = Helper.minutes(1);
	}

	@Override
	public void regenerate() {
		this.regenTicks++;
		if (regenTicks == 1) ChatUtil.sendMessageToPlayer(player,
			new StringTextComponent(player.getName().getString() + ", I let you go."), ChatUtil.MessageType.CHAT);

		this.removeRegens(1);
		player.addEffect(new EffectInstance(Effects.REGENERATION, Helper.seconds(20), 0, false, false, false));
		player.setDeltaMovement(new Vector3d(0, 0, 0));
		this.update();
	}

	@Override
	public void update() {
		DMAPackets.sendTo((ServerPlayerEntity) this.player, new CBSyncPlayerPacket(player.getId(), this.serializeNBT()));
	}

	@Override
	public boolean isPreRegen() {
		return this.preRegenTime > 0;
	}

	@Override
	public boolean isRegenerating() {
		return this.regenTicks > 0;
	}

	@Override
	public int getRegens() {
		return currentRegens;
	}

	@Override
	public void setRegens(int regens) {
		this.currentRegens = regens;
	}

	@Override
	public void setRegenTicks(int ticks) {
		this.regenTicks = ticks;
	}

	@Override
	public int getRegenTicks() {
		return this.regenTicks;
	}

	@Override
	public void addRegens(int add) {
		this.currentRegens = Math.min(this.getRegens() + add, maxRegens);
		this.update();
	}

	@Override
	public void removeRegens(int remove) {
		this.currentRegens = Math.max(this.getRegens() - remove, 0);
		this.update();
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT tag = new CompoundNBT();
		tag.putInt(TAG_REGEN_AMOUNT, this.currentRegens);
		tag.putInt(TAG_POSTPONE, this.postponeTime);
		tag.putInt(TAG_PRE_REGEN, this.preRegenTime);
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		if (nbt.contains(TAG_REGEN_AMOUNT)) {
			this.currentRegens = nbt.getInt(TAG_REGEN_AMOUNT);
		}
		if (nbt.contains(TAG_POSTPONE)) {
			this.postponeTime = nbt.getInt(TAG_POSTPONE);
		}
		if (nbt.contains(TAG_PRE_REGEN)) {
			this.preRegenTime = nbt.getInt(TAG_PRE_REGEN);
		}
	}
}
