package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.init.DMAEntities;
import com.jdolphin.dmadditions.init.DMAItems;
import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.item.GunItem;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public class JimEntity extends AnimalEntity implements IRangedAttackMob{

	public final static Set<UUID> DONT_ATTACK = new HashSet<>(Arrays.asList(
		UUID.fromString("f54da43a-eedc-43cc-bccd-3337334e9a66"), // torchwood_one
		UUID.fromString("f7648302-a746-4311-9b37-b6d4041d4dd6"), // torchwood_two
		UUID.fromString("af6750d4-3b99-422a-9240-15c9364cbbaa"), // samtiz
		UUID.fromString("0d115634-999c-4e98-a149-0b6984d04c0f"), // the_real_rebel
		UUID.fromString("6d0f4e7a-5a12-4ef5-9420-e15a8f99ba1b"), // dawzit
		UUID.fromString("f5e247ad-8bcc-49e7-8da8-ff960d6a3766"), // jim
		UUID.fromString("a75ea8dd-efc8-45dd-b724-bac7a9c14d6f")  // leo
		//why no James
	));

	private final RangedAttackGoal rangedAttackGoal = new RangedAttackGoal(this, 1.0D, 20, 15.0F);
	private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2D, false){
      public void stop() {
         super.stop();
         JimEntity.this.setAggressive(false);
      }

      public void start() {
         super.start();
         JimEntity.this.setAggressive(true);
      }
   };
 

	public JimEntity(EntityType<? extends AnimalEntity> type, World world) {
		super(type, world);

		reassessWeaponGoal();
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(5, new RandomWalkingGoal((CreatureEntity) this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, FoxEntity.class, 10, true, true, (Predicate<LivingEntity>)null));
		this.targetSelector.addGoal(4, new JimNearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, false));
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 300.0D)
			.add(Attributes.FOLLOW_RANGE, 20.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.25D)
			.add(Attributes.ATTACK_DAMAGE, 6.0D);
	}

	@Override
	public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity partner) {
		Entity offspring = partner.getType().create(world);

		if(offspring instanceof AgeableEntity){
			return (AgeableEntity) offspring;
		}

		return null;
	}

	protected boolean addItemToEmptyHand(ItemStack stack){
		boolean mainHandEmpty = getMainHandItem().isEmpty();
		boolean offhandEmpty = getOffhandItem().isEmpty();

		Hand emptyHand = mainHandEmpty ? Hand.MAIN_HAND : offhandEmpty ? Hand.OFF_HAND : null;
		if(emptyHand != null){
			this.setItemInHand(emptyHand, stack);
			return true;
		}

		return false;
	}

	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(difficulty);

		if(AdventUnlock.isDecember() && random.nextBoolean()){
			equipItemIfPossible(new ItemStack(DMAItems.SANTA_HAT.get()));
		}

		if(random.nextFloat() <= 0.95){
			equipItemIfPossible(new ItemStack(DMAItems.TOP_HAT.get()));
		}


		if(!difficulty.isHarderThan(Difficulty.EASY.getId()))
			return;

		if(random.nextFloat() <= 0.75){
			addItemToEmptyHand(new ItemStack(DMItems.METALERT_SWORD.get()));
		}

		if(random.nextFloat() <= 0.75){
			addItemToEmptyHand(new ItemStack(DMAItems.PISTOL.get()));
		}

	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_,
			SpawnReason p_213386_3_, ILivingEntityData p_213386_4_, CompoundNBT p_213386_5_) {

		this.populateDefaultEquipmentSlots(p_213386_2_);
		reassessWeaponGoal();
		this.setCanPickUpLoot(true);

		return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
	}

	public void reassessWeaponGoal() {
		if (this.level != null && !this.level.isClientSide) {
			this.goalSelector.removeGoal(this.meleeGoal);
			this.goalSelector.removeGoal(this.rangedAttackGoal);
			ItemStack itemstack = this.getItemInHand(ProjectileHelper.getWeaponHoldingHand(this, item -> item instanceof GunItem));
			if (itemstack.getItem() == DMAItems.PISTOL.get()) { //TODO maybe make an item tag or something so that it doesn't have to be a specific item

				this.goalSelector.addGoal(4, this.rangedAttackGoal);
			} else {
				this.goalSelector.addGoal(4, this.meleeGoal);
			}

		}
	}

	@Override
	public void performRangedAttack(LivingEntity target, float p_82196_2_) {
      double d0 = 0.0;
      double d1 = 0.0;
      double d2 = 0.0;
      if (target.isAlive()) {
         d0 = target.getX() - this.getX();
         d1 = target.getY(0.3333333333333333) - this.getY() - 0.75;
         d2 = target.getZ() - this.getZ();
         LaserEntity laser = new LaserEntity(this.level, this, 0.2F, (float)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
         laser.setDamageSource(new EntityDamageSource("dalekgun", this));
         laser.setLaserType(DMProjectiles.BULLET);
         laser.shoot(d0, d1, d2, 2.5F, 0.0F);
         this.playSound((SoundEvent)DMSoundEvents.ENTITY_AUTON_SHOOT.get(), 1.0F, 1.0F);
         this.level.addFreshEntity(laser);
      }
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
		super.readAdditionalSaveData(p_70037_1_);

		reassessWeaponGoal();
	}

	public void setItemSlot(EquipmentSlotType type, ItemStack stack) {
		super.setItemSlot(type, stack);
		if (!this.level.isClientSide) {
			this.reassessWeaponGoal();
		}
	}

	@Override
	public boolean equipItemIfPossible(ItemStack stack) {
		if(super.equipItemIfPossible(stack)) return true;

		if(stack.getEquipmentSlot() == null)
			return addItemToEmptyHand(stack);

		return false;
	}

	public static class JimNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T>{
		public JimNearestAttackableTargetGoal(MobEntity entity, Class<T> clazz, boolean p_i50313_3_) {
			super(entity, clazz, p_i50313_3_);
		}

		@Override
		public boolean canContinueToUse() {
			if(! super.canContinueToUse()) return false;

			if(target != null && target.getType().equals(DMAEntities.JIM.get())) return false;

			return true;
		}

		@Override
		public boolean canUse() {
			boolean canUse = super.canUse();
			if(!canUse) return false;

			if(target.getType().equals(DMAEntities.JIM.get())) return false;

			if(target instanceof PlayerEntity){
				if(target.getUUID() == UUID.fromString("380df991-f603-344c-a090-369bad2a924a")) // Dev
					return false;

				if(DONT_ATTACK.contains(target.getUUID())) return false;
			}

			return canUse;
		}

	}
}
