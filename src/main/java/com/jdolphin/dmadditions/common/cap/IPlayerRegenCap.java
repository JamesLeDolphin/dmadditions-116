package com.jdolphin.dmadditions.common.cap;

import com.jdolphin.dmadditions.common.init.DMACapabilities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface IPlayerRegenCap extends INBTSerializable<CompoundNBT> {

	void tick();

	boolean postponed();

	int getPostponeTime();

	boolean canPostpone();

	void postpone(boolean postpone);

	boolean hasRegens();

	void setPreRegen(boolean preRegen);

	void regenerate();

	void update();

	boolean isPreRegen();

	boolean isRegenerating();

	int getRegens();

	int getMaxRegens();

	void addRegens(int add);

	void removeRegens(int remove);

	void setRegens(int regens);

	void setRegenTicks(int ticks);

	int getRegenTicks();

	class Provider implements ICapabilitySerializable<CompoundNBT> {
		IPlayerRegenCap data;

		public Provider(IPlayerRegenCap data) {
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
			return cap.equals(DMACapabilities.REGEN_CAPABILITY) ? (LazyOptional<T>) LazyOptional.of(() -> this.data) : LazyOptional.empty();
		}
	}

	class Storage implements Capability.IStorage<IPlayerRegenCap> {
		public Storage() {
		}

		public INBT writeNBT(Capability<IPlayerRegenCap> capability, IPlayerRegenCap instance, Direction side) {
			return instance.serializeNBT();
		}

		public void readNBT(Capability<IPlayerRegenCap> capability, IPlayerRegenCap instance, Direction side, INBT nbt) {
			if (nbt instanceof CompoundNBT) {
				instance.deserializeNBT((CompoundNBT) nbt);
			}

		}
	}
}
