package com.jdolphin.dmadditions.entity;

import com.swdteam.common.entity.LookAtGoalBetter;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.common.extensions.IForgeEntity;

public class RacnossEntity extends MonsterEntity implements IForgeEntity {
	private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2D, false);

	public RacnossEntity(EntityType<? extends MonsterEntity> type, World world) {
		super(type, world);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes()
			.add(Attributes.MOVEMENT_SPEED, 0.7)
			.add(Attributes.MAX_HEALTH, 200.0)
			.add(Attributes.ATTACK_DAMAGE, 4.356)
			.add(Attributes.FOLLOW_RANGE, 20.0);
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PiglinEntity.class, 6.0F, 1.0, 1.2));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PiglinBruteEntity.class, 6.0F, 1.0, 1.2));
		this.goalSelector.addGoal(5, new LookAtGoalBetter(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Entity.class));
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));

	}

	@Override
	public boolean checkSpawnRules(IWorld world, SpawnReason reason) {
		if(!reason.equals(SpawnReason.NATURAL))
			return super.checkSpawnRules(world, reason);

		BlockPos blockPos = blockPosition();
		IChunk chunk = world.getChunk(blockPos);

		boolean noVillages = chunk.getReferencesForFeature(Structure.VILLAGE).isEmpty();
		if(noVillages) return false;

		return super.checkSpawnRules(world, reason);
	}
}