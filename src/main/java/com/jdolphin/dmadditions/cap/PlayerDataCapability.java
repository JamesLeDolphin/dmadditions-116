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
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class PlayerDataCapability implements IPlayerDataCap {
	private final String TAG_REGEN_AMOUNT = "regens";
	private final String TAG_POSTPONE = "postponedFor";
	private final PlayerEntity player;
	private final int maxRegens = 12;
	private final int minRegens = 0;
	private int postponeTime = 0;
	private int preRegenTime = Helper.minutes(1);
	private int regenTicks;
	private int currentRegens;

	public PlayerDataCapability(PlayerEntity player) {
	this.player = player;
	}

	@Override
	public void tick() {
		if (postponed()) {
			postponeTime--;
			Helper.print(postponeTime);
		}
		if (isPreRegen()) preRegenTime--;
		if (!isPreRegen() && !postponed() && hasRegens()) {
			this.regenerate();
		}
	}

	@Override
	public boolean postponed() {
		return postponeTime > 0;
	}

	@Override
	public int getPostponeTime() {
		return this.postponeTime;
	}

	@Override
	public boolean canPostpone() {
		return this.hasRegens() && !postponed();
	}

	@Override
	public void postpone() {
		ChatUtil.sendMessageToPlayer(player, new StringTextComponent("You've postponed your regeneration for 5 minutes").withStyle(TextFormatting.GREEN),
			ChatUtil.MessageType.CHAT);
		this.postponeTime = Helper.minutes(5);
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
	public void setPreRegen () {
		this.preRegenTime = Helper.minutes(1);
	}

	@Override
	public void regenerate() {
		 this.regenTicks++;
		 ChatUtil.sendMessageToPlayer(player, new StringTextComponent(player.getName().getString() + ", I let you go."), ChatUtil.MessageType.CHAT);
		 player.addEffect(new EffectInstance(Effects.REGENERATION, Helper.seconds(10), 0, false, false, false));
	}

	@Override
	public void update() {
		DMAPackets.sendTo((ServerPlayerEntity) this.player, new CBSyncPlayerPacket(player.getId(), this.serializeNBT()));
	}

	@Override
	public boolean isPreRegen() {
		return this.preRegenTime >= 5;
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
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT tag = new CompoundNBT();
		tag.putInt(TAG_REGEN_AMOUNT, this.currentRegens);
		tag.putInt(TAG_POSTPONE, this.postponeTime);
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
	}
}
