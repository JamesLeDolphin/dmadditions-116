package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.client.audio.ShoppingCartTickableSound;
import com.jdolphin.dmadditions.init.DMASoundEvents;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShoppingCartEntity extends MobEntity implements IJumpingMount {
	public boolean flyable = false;
	public boolean engineStarted = false;
	private int engineRevTime = 0;

	@OnlyIn(Dist.CLIENT)
	ShoppingCartTickableSound sound;

	public ShoppingCartEntity(EntityType<? extends MobEntity> p_i48576_1_, World p_i48576_2_) {
		super(p_i48576_1_, p_i48576_2_);

		this.setNoAi(true);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.4)
				.add(Attributes.MAX_HEALTH, 20.0)
				.add(Attributes.ATTACK_DAMAGE, 2.0);
	}

	@Override
	protected ActionResultType mobInteract(PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (itemstack.isEmpty() && !player.isCrouching()) {
			player.startRiding(this);
			return ActionResultType.PASS;
		}

		return super.mobInteract(player, hand);
	}

	public boolean canBeControlledByRider() {
		return this.getControllingPassenger() instanceof LivingEntity;
	}

	public boolean canBeSteered() {
		return this.getControllingPassenger() instanceof LivingEntity;
	}

	@javax.annotation.Nullable
	public Entity getControllingPassenger() {
		return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
	}

	@Override
	public void travel(Vector3d p_213352_1_) {
		if (this.isAlive()) {

			if (this.isVehicle() && this.canBeSteered()) {
				this.engineStarted = true;

				LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
				this.yRot = livingentity.yRot;
				this.yRotO = this.yRot;
				this.xRot = livingentity.xRot * 0.5F;
				this.setRot(this.yRot, this.xRot);
				this.yBodyRot = this.yRot;
				this.yHeadRot = this.yBodyRot;
				float f1 = livingentity.zza;

				if (f1 <= 0.0F) {
					f1 *= 0.25F;

					this.engineRevTime = 0;
				}

				if (f1 > 0.5F) {
					f1 = 0.5F;
				}

				float f2 = MathHelper.sin(this.yRot * 0.017453292F);
				float f3 = MathHelper.cos(this.yRot * 0.017453292F);

				if(this.flyable){
					this.setNoGravity(true);
					if (f1 > 0.0F) {

						this.setDeltaMovement(this.getDeltaMovement().add(-0.2F * f2, Math.toRadians(this.xRot) * -0.5, 0.2F * f3));

						this.setNoGravity(false);
					} else if (this.getEntity().getDeltaMovement().y <= 0) {
						this.setNoGravity(true);
					}
				} else {
					if(f1 > 0.0F) {
						this.setNoGravity(false);
						this.setDeltaMovement(this.getDeltaMovement().add(-0.2F * f2, 0, 0.2F * f3));

						--this.engineRevTime;
						if (this.engineRevTime <= 0) {
							this.engineRevTime = 40;
							playRevSound(0.5f, 0.5f + f1);
						}
					}
				}
			}

			super.travel(p_213352_1_);
		}
	}

	public void playRevSound(float volume, float pitch){
		this.playSound(DMASoundEvents.V8_REVVING.get(), volume, pitch);
	}

	public void positionRider(Entity entity) {
		if (this.hasPassenger(entity)) {
			float f = -0.5F;
			float f1 = (float) ((!this.isAlive() ? (double) 0.01F : this.getPassengersRidingOffset()) + entity.getMyRidingOffset());
			Vector3d vector3d = (new Vector3d(f, 0.0D, 0.0D)).yRot(-this.yRot * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
			entity.setPos(this.getX() + vector3d.x, this.getY() + (double) f1, this.getZ() + vector3d.z);
		}
	}

	@Override
	public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
		return false;
	}

	@Override
	public void onPlayerJump(int p_110206_1_) {
		this.setDeltaMovement(this.getDeltaMovement().add(0, 1, 0));
	}

	@Override
	public boolean canJump() {
		return this.flyable || this.isNoGravity();
	}

	@Override
	public void handleStartJump(int p_184775_1_) {

	}

	@Override
	public void handleStopJump() {
	}

	@Override
	public boolean hurt(DamageSource source, float p_70097_2_) {
		Entity entity = source.getEntity();
		if(entity instanceof PlayerEntity){
			if(engineStarted){
				engineStarted = false;
				return false;
			}

			PlayerEntity player = (PlayerEntity) entity;
			if(player.abilities.instabuild)
				this.kill();
		}
		return super.hurt(source, p_70097_2_);
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT tag) {
		super.readAdditionalSaveData(tag);

		this.flyable = tag.getBoolean("Flyable");
		this.engineStarted = tag.getBoolean("EngineStarted");
		this.setNoGravity(this.flyable);
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT tag) {
		tag.putBoolean("Flyable", flyable);
		tag.putBoolean("EngineStarted", engineStarted);

		super.addAdditionalSaveData(tag);
	}

	@OnlyIn(Dist.CLIENT)
	public void playSound() {
		if (!this.level.isClientSide) return;

		SoundHandler soundManager = Minecraft.getInstance().getSoundManager();

		if(sound == null)
			sound = new ShoppingCartTickableSound(this, DMASoundEvents.V8_IDLE.get());

		if (!soundManager.isActive(sound)) {
			soundManager.play(sound);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void clientTick() {
		SoundHandler soundManager = Minecraft.getInstance().getSoundManager();

		if(this.engineStarted && (this.sound == null || this.sound.isStopped() || !soundManager.isActive(sound))){
			this.playSound();
		} else if(this.sound != null && this.sound.isStopped()){
			sound = null;
		}
	}

	@Override
	public void tick() {
		if(this.level.isClientSide()){
			clientTick();
		}

		super.tick();
	}

}
