package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.client.audio.ShoppingCartTickableSound;
import com.jdolphin.dmadditions.init.DMABlocks;
import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.init.DMASoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;

import java.util.Collections;
import java.util.List;

public class ShoppingCartEntity extends MobEntity implements IJumpingMount, IForgeShearable {
	private int engineRevTime = 0;

	protected static final DataParameter<Boolean> DATA_ID_FLYABLE = EntityDataManager.defineId(ShoppingCartEntity.class, DataSerializers.BOOLEAN);
	protected static final DataParameter<Boolean> DATA_ID_HAS_ENGINE = EntityDataManager.defineId(ShoppingCartEntity.class, DataSerializers.BOOLEAN);
	protected static final DataParameter<Boolean> DATA_ID_ENGINE_STARTED = EntityDataManager.defineId(ShoppingCartEntity.class, DataSerializers.BOOLEAN);

	@OnlyIn(Dist.CLIENT)
	ShoppingCartTickableSound sound;

	public ShoppingCartEntity(EntityType<? extends MobEntity> entityType, World world) {
		super(entityType, world);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_ID_FLYABLE, false);
		this.entityData.define(DATA_ID_HAS_ENGINE, true);
		this.entityData.define(DATA_ID_ENGINE_STARTED, false);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes()
			.add(Attributes.MOVEMENT_SPEED, 0.4)
			.add(Attributes.MAX_HEALTH, 20.0)
			.add(Attributes.ATTACK_DAMAGE, 2.0);
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
		if (level.isClientSide)
			return ActionResultType.PASS;

		ItemStack itemstack = player.getItemInHand(hand);

		if (itemstack.getItem().equals(DMABlocks.ENGINE.get().asItem()) && !this.hasEngine()) {
			this.setHasEngine(true);
			itemstack.shrink(1);
			this.playSound(SoundEvents.ARMOR_EQUIP_CHAIN, 1, 1);

			return ActionResultType.CONSUME;
		}

		if (itemstack.isEmpty() && !player.isCrouching() && hand == Hand.MAIN_HAND) {
			player.startRiding(this);
			return ActionResultType.PASS;
		}

		return super.mobInteract(player, hand);
	}

	@Override
	public List<ItemStack> onSheared(PlayerEntity player, ItemStack item, World world, BlockPos pos, int fortune) {
		if (!this.hasEngine()) return Collections.emptyList();

		this.setHasEngine(false);

		world.playSound(null, this, SoundEvents.SNOW_GOLEM_SHEAR, SoundCategory.PLAYERS, 1, 1);
		return Collections.singletonList(new ItemStack(DMABlocks.ENGINE.get().asItem()));
	}

	@Override
	public boolean isShearable(ItemStack item, World world, BlockPos pos) {
		return this.hasEngine();
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
	public void travel(Vector3d vector3d) {
		if (this.isAlive()) {

			if (this.isVehicle() && this.canBeSteered()) {
				if (hasEngine()) this.setEngineStarted(true);
				else this.setEngineStarted(false);

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

				if (this.isFlyable()) {
					this.setNoGravity(true);
					if (f1 > 0.0F) {

						this.setDeltaMovement(this.getDeltaMovement().add(-0.2F * f2, Math.toRadians(this.xRot) * -0.5, 0.2F * f3));

						this.setNoGravity(false);
					} else if (this.getEntity().getDeltaMovement().y <= 0) {
						this.setNoGravity(true);
					}
				} else {
					if (f1 > 0.0F) {
						this.setNoGravity(false);
						if (this.isEngineStarted()) {
							this.setDeltaMovement(this.getDeltaMovement().add(-0.2F * f2, 0, 0.2F * f3));

							--this.engineRevTime;
							if (this.engineRevTime <= 0) {
								this.engineRevTime = 40;
								playRevSound(0.5f, 0.5f + f1);
							}
						}
					}
				}
			}

			super.travel(vector3d);
		}
	}

	public void playRevSound(float volume, float pitch) {
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
	public boolean causeFallDamage(float v, float v1) {
		return false;
	}

	@Override
	public void onPlayerJump(int i) {
		this.setDeltaMovement(this.getDeltaMovement().add(0, 1, 0));
	}

	@Override
	public boolean canJump() {
		return this.isFlyable() || this.isNoGravity();
	}

	@Override
	public void handleStartJump(int i) {

	}

	@Override
	public void handleStopJump() {
	}

	@Override
	public boolean hurt(DamageSource source, float v) {
		Entity entity = source.getEntity();
		if (entity instanceof PlayerEntity) {
			if (isEngineStarted()) {
				this.setEngineStarted(false);
				this.playSound(SoundEvents.ITEM_BREAK, 1, 1);
				return false;
			}

			PlayerEntity player = (PlayerEntity) entity;
			if (player.abilities.instabuild) {
				this.remove();
				return false;
			}
		}

		return super.hurt(source, v);
	}

	@Override
	public void die(DamageSource damageSource) {
		this.remove();
		if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
			ItemStack itemstack = new ItemStack(DMAItems.SHOPPING_CART.get());
			if (this.hasCustomName()) {
				itemstack.setHoverName(this.getCustomName());
			}

			this.spawnAtLocation(itemstack);
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT tag) {
		super.readAdditionalSaveData(tag);

		this.setFlyable(tag.getBoolean("Flyable"));
		this.setNoGravity(this.isFlyable());
		this.setEngineStarted(tag.getBoolean("EngineStarted"));
		if (tag.contains("HasEngine"))
			this.setHasEngine(tag.getBoolean("HasEngine"));
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT tag) {
		super.addAdditionalSaveData(tag);

		tag.putBoolean("Flyable", isFlyable());
		tag.putBoolean("EngineStarted", isEngineStarted());
		tag.putBoolean("HasEngine", hasEngine());
	}

	public boolean hasEngine() {
		return this.entityData.get(DATA_ID_HAS_ENGINE);
	}

	public void setHasEngine(boolean hasEngine) {
		this.entityData.set(DATA_ID_HAS_ENGINE, hasEngine);
		if (!hasEngine)
			this.setEngineStarted(false);
	}

	public boolean isEngineStarted() {
		return this.entityData.get(DATA_ID_ENGINE_STARTED);
	}

	public void setEngineStarted(boolean isEngineStarted) {
		this.entityData.set(DATA_ID_ENGINE_STARTED, isEngineStarted);
	}

	public boolean isFlyable() {
		return this.entityData.get(DATA_ID_FLYABLE);
	}

	public void setFlyable(boolean isFlyable) {
		this.entityData.set(DATA_ID_FLYABLE, isFlyable);
	}

	@OnlyIn(Dist.CLIENT)
	public void playSound() {
		if (!this.level.isClientSide) return;

		SoundHandler soundManager = Minecraft.getInstance().getSoundManager();

		if (sound == null)
			sound = new ShoppingCartTickableSound(this, DMASoundEvents.V8_IDLE.get());

		if (!soundManager.isActive(sound)) {
			soundManager.play(sound);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void clientTick() {
		SoundHandler soundManager = Minecraft.getInstance().getSoundManager();

		if (this.isEngineStarted() && (this.sound == null || this.sound.isStopped() || !soundManager.isActive(sound))) {
			this.playSound();
		} else if (this.sound != null && this.sound.isStopped()) {
			sound = null;
		}
	}

	@Override
	public void tick() {
		if (this.level.isClientSide()) {
			clientTick();
		}

		super.tick();
	}

}
