package com.jdolphin.dmadditions.cap;

import com.jdolphin.dmadditions.init.DMACapabilities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface IPlayerDataCap extends INBTSerializable<CompoundNBT> {

	void tick();

	boolean postponed();

	int getPostponeTime();

	boolean canPostpone();

	void postpone();

	boolean hasRegens();

	void setPreRegen(boolean b);

	void update();
	boolean isPreRegen();

	int getRegens();

	int getMaxRegens();

	void addRegens(int add);

	void setRegens(int regens);

	void setRegenTicks(int ticks);

	int getRegenTicks();

	class Provider implements ICapabilitySerializable<CompoundNBT> {
		IPlayerDataCap data;

		public Provider(IPlayerDataCap data) {
			this.data = data;
		}

		public CompoundNBT serializeNBT() {
			return this.data.serializeNBT();
		}

		public void deserializeNBT(CompoundNBT nbt) {
			this.data.deserializeNBT(nbt);
		}

		@NotNull
		@Override
		@SuppressWarnings("unchecked")
		public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
			return cap.equals(DMACapabilities.PLAYER_DATA) ? (LazyOptional<T>) LazyOptional.of(() -> this.data) : LazyOptional.empty();
		}
	}

	class Storage implements Capability.IStorage<IPlayerDataCap> {
		public Storage() {
		}

		public INBT writeNBT(Capability<IPlayerDataCap> capability, IPlayerDataCap instance, Direction side) {
			return instance.serializeNBT();
		}

		public void readNBT(Capability<IPlayerDataCap> capability, IPlayerDataCap instance, Direction side, INBT nbt) {
			if (nbt instanceof CompoundNBT) {
				instance.deserializeNBT((CompoundNBT)nbt);
			}

		}
	}
}
