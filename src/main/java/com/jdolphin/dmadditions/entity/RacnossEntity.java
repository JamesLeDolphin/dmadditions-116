package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.client.model.entity.RacnossModel;
import com.swdteam.common.entity.LookAtGoalBetter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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

	@OnlyIn(Dist.CLIENT)
	public void setupAnim(RacnossModel model, RacnossEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		boolean fallFlying = entity.getFallFlyingTicks() > 4;
		boolean swimming = entity.isVisuallySwimming();

		float f = 1.0F;
		if (fallFlying) {
			f = (float)entity.getDeltaMovement().lengthSqr();
			f = f / 0.2F;
			f = f * f * f;
		}

		if (f < 1.0F) {
			f = 1.0F;
		}

		model.head.xRot = headPitch * ((float) Math.PI / 180F);
		model.head.yRot = netHeadYaw * ((float) Math.PI / 270F);

		model.leg0.zRot = -1;
		model.leg1.zRot = -1;
		model.leg2.zRot = -1;
		model.leg3.zRot = -1;
		model.leg4.zRot = -1;
		model.leg5.zRot = -1;
		model.leg6.zRot = -7;
		model.leg7.zRot = -7;
		float f1 = -0.0F;
		float f2 = ((float)Math.PI / 8F);
		model.leg0.yRot = -12.5f;
		model.leg1.yRot = 12.5f;
		model.leg2.yRot = 0;
		model.leg3.yRot = 0;
		model.leg4.yRot = 12.5f;
		model.leg5.yRot = -12.5f;
		model.leg6.yRot = 67.8882f;
		model.leg7.yRot = -67.8882f;
		model.leg7.xRot = 0;
		model.leg6.xRot = 0;

		float f3 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
		float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * limbSwingAmount;
		float f5 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
		float f6 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI * 1.5F)) * 0.4F) * limbSwingAmount;
		float f7 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
		float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.4F) * limbSwingAmount;
		float f9 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
		float f10 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float)Math.PI * 1.5F)) * 0.4F) * limbSwingAmount;
		model.leg0.yRot += f3;
		model.leg1.yRot += -f3;
		model.leg2.yRot += f4;
		model.leg3.yRot += -f4;
		model.leg4.yRot += f5;
		model.leg5.yRot += -f5;
		model.leg6.yRot += f6;
		model.leg7.yRot += -f6;
		model.leg0.zRot += f7;
		model.leg1.zRot += -f7;
		model.leg2.zRot += f8;
		model.leg3.zRot += -f8;
		model.leg4.zRot += f9;
		model.leg5.zRot += -f9;
	}
}
