package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.client.model.entity.RacnossModel;
import com.swdteam.common.entity.LookAtGoalBetter;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.HandSide;
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
			.add(Attributes.MOVEMENT_SPEED, 0.4)
			.add(Attributes.MAX_HEALTH, 200.0)
			.add(Attributes.ATTACK_DAMAGE, 4.356)
			.add(Attributes.FOLLOW_RANGE, 20.0);
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(5, new LookAtGoalBetter(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));

		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Entity.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));

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

		model.rightArm.xRot = -0.3f;
		model.leftArm.xRot = -0.3f;
		model.rightArm.yRot = 0;
		model.leftArm.yRot = 0;
		model.rightArm.zRot = 0;
		model.leftArm.zRot = 0;

		boolean flag2 = entity.getMainArm() == HandSide.RIGHT;
		boolean flag3 = flag2 ? model.leftArmPose.isTwoHanded() : model.rightArmPose.isTwoHanded();
		if (flag2 != flag3) {
			model.poseLeftArm(entity);
			model.poseRightArm(entity);
		} else {
			model.poseRightArm(entity);
			model.poseLeftArm(entity);
		}

		this.setupAttackAnimation(model, ageInTicks);
	}

	protected void setupAttackAnimation(RacnossModel model, float p_230486_2_) {
		if (!(model.attackTime <= 0.0F)) {
			HandSide handside = model.getAttackArm(this);
			ModelRenderer modelrenderer = model.getArm(handside);
			float f = model.attackTime;
			model.body.yRot = MathHelper.sin(MathHelper.sqrt(f) * ((float)Math.PI * 2F)) * 0.2F;
			if (handside == HandSide.LEFT) {
				model.body.yRot *= -1.0F;
			}

			model.rightArm.z = MathHelper.sin(model.body.yRot) * 5.0F;
			model.rightArm.x = -MathHelper.cos(model.body.yRot) * 5.0F;
			model.leftArm.z = -MathHelper.sin(model.body.yRot) * 5.0F;
			model.leftArm.x = MathHelper.cos(model.body.yRot) * 5.0F;
			model.rightArm.yRot += model.body.yRot;
			model.leftArm.yRot += model.body.yRot;
			model.leftArm.xRot += model.body.yRot;
			f = 1.0F - model.attackTime;
			f = f * f;
			f = f * f;
			f = 1.0F - f;
			float f1 = MathHelper.sin(f * (float)Math.PI);
			float f2 = MathHelper.sin(model.attackTime * (float)Math.PI) * -(model.head.xRot - 0.7F) * 0.75F;
			modelrenderer.xRot = (float)((double)modelrenderer.xRot - ((double)f1 * 1.2D + (double)f2));
			modelrenderer.yRot += model.body.yRot * 2.0F;
			modelrenderer.zRot += MathHelper.sin(model.attackTime * (float)Math.PI) * -0.4F;
		}
	}
}
