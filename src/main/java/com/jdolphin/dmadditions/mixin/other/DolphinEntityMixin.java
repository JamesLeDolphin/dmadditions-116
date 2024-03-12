package com.jdolphin.dmadditions.mixin.other;

import com.jdolphin.dmadditions.entity.JamesLeDolphinEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nullable;

import static com.jdolphin.dmadditions.init.DMAEntities.JAMESLEDOLPHIN;

@Mixin(DolphinEntity.class)
public abstract class DolphinEntityMixin extends LivingEntity {

	protected DolphinEntityMixin(EntityType<? extends LivingEntity> p_i48577_1_, World p_i48577_2_) {
		super(p_i48577_1_, p_i48577_2_);
	}

	protected void checkName(ITextComponent name) {
		if (name != null && name.getString()
			.matches("(?i)1?(james|jimothy)(le)?(dolphin)?")) {

			DolphinEntity dolphin = (DolphinEntity) (Object) this;

			JamesLeDolphinEntity james = new JamesLeDolphinEntity(JAMESLEDOLPHIN.get(), dolphin.level);
			james.setCustomName(this.getCustomName());
			james.setHealth(this.getHealth());
			james.setBaby(this.isBaby());
			james.setDeltaMovement(this.getDeltaMovement());
			james.setGotFish(dolphin.gotFish());
			james.setTreasurePos(dolphin.getTreasurePos());
			james.setInvulnerable(this.isInvulnerable());
			james.copyPosition(this);

			if (!this.level.isClientSide)
				james.finalizeSpawn((IServerWorld) this.level, this.level.getCurrentDifficultyAt(this.blockPosition()),
					SpawnReason.CONVERSION, null, null);

			this.remove();
			this.level.addFreshEntity(james);
		}

	}

	@Override
	public void setCustomName(@Nullable ITextComponent component) {
		super.setCustomName(component);

		if (this.level.isClientSide || this.getType().equals(JAMESLEDOLPHIN.get()) || !this.isAddedToWorld())
			return;

		checkName(component);
	}

}
