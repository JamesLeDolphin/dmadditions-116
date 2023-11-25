package com.jdolphin.dmadditions.mixin;

import java.lang.reflect.Field;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.base.Function;
import com.jdolphin.dmadditions.entity.dalek.IDalekEntityMixin;
import com.swdteam.client.model.ModelDalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.model.javajson.JSONModel;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

@Mixin(ModelDalekBase.class)
public class ModelDalekBaseMixin {
	private ModelRenderer headDefaultState;
	private ModelRenderer neckDefaultState;
	private ModelRenderer torsoDefaultState;

	private ModelRenderer leftArmDefaultState;
	private ModelRenderer rightArmDefaultState;

	@Inject(method = "<init>", at = @At("TAIL"), remap = false)
	public void ModelDalekBase(JSONModel model, CallbackInfo ci) {
		ModelDalekBase modelDalekBase = (ModelDalekBase) (Object) this;
		Field renderTypeField;
		try {
			renderTypeField = Model.class
				.getDeclaredField("renderType");

			renderTypeField.setAccessible(true);
			Function<ResourceLocation, RenderType> renderType = RenderType::entityTranslucent;
			renderTypeField.set(modelDalekBase, renderType);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

	}


	@Inject(method = "init", at = @At("TAIL"), remap = false)
	public void init(CallbackInfo ci) {
		ModelDalekBase instance = (ModelDalekBase) (Object) this;

		headDefaultState = instance.ANI_HEAD.createShallowCopy();
		neckDefaultState = instance.ANI_NECK.createShallowCopy();
		torsoDefaultState = instance.ANI_TORSO.createShallowCopy();
	}

	@Inject(method = "setupAnim(Lcom/swdteam/common/entity/dalek/DalekEntity;FFFFF)V",
		at = @At("HEAD"), cancellable = true, remap = false)
	public void setupAnim(DalekEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
		ModelDalekBase instance = (ModelDalekBase) (Object) this;

		ModelRenderer leftArm = instance.getPart(entity.getDalekArmLeft());
		ModelRenderer rightArm = instance.getPart(entity.getDalekArmRight());

		if (leftArmDefaultState == null && leftArm != null)
			leftArmDefaultState = leftArm.createShallowCopy();

		if (rightArmDefaultState == null && rightArm != null)
			rightArmDefaultState = rightArm.createShallowCopy();


		instance.ANI_HEAD.xRot = headPitch * ((float) Math.PI / 180F);
		instance.ANI_HEAD.zRot = 0.0F;

		if (((IDalekEntityMixin) entity).isPartyDalek()) {
			float f = MathHelper.cos(entity.tickCount);
			float f1 = MathHelper.sin((float) entity.tickCount);

			instance.ANI_HEAD.x = headDefaultState.x + f;
			instance.ANI_HEAD.y = headDefaultState.y + f1;
			instance.ANI_HEAD.xRot = 0.0F;
			instance.ANI_HEAD.yRot = 0.0F;
			instance.ANI_HEAD.zRot = f1 * 0.4F;

			instance.ANI_NECK.x = neckDefaultState.x + f;
			instance.ANI_NECK.y = neckDefaultState.y + f1;
			instance.ANI_NECK.xRot = 0.0F;
			instance.ANI_NECK.yRot = 0.0F;
			instance.ANI_NECK.zRot = f1 * 0.4F;

			instance.ANI_TORSO.x = torsoDefaultState.x + f;
			instance.ANI_TORSO.y = torsoDefaultState.y + f1;

			if (leftArm != null) {
				leftArm.zRot = -leftArmDefaultState.zRot - entity.tickCount;
				leftArm.x = leftArmDefaultState.x + f;
				leftArm.y = leftArmDefaultState.y + f1;
			}
			if (rightArm != null) {
				rightArm.zRot = rightArmDefaultState.zRot + entity.tickCount;
				rightArm.x = rightArmDefaultState.x + f;
				rightArm.y = rightArmDefaultState.y + f1;
			}

			ci.cancel();
			return;
		}

		instance.ANI_HEAD.x = headDefaultState.x;
		instance.ANI_HEAD.y = headDefaultState.y;
		instance.ANI_NECK.x = neckDefaultState.x;
		instance.ANI_NECK.y = neckDefaultState.y;

		instance.ANI_NECK.xRot = neckDefaultState.xRot;
		instance.ANI_NECK.yRot = neckDefaultState.yRot;
		instance.ANI_NECK.zRot = neckDefaultState.zRot;

		instance.ANI_TORSO.x = torsoDefaultState.x;
		instance.ANI_TORSO.y = torsoDefaultState.y;

		instance.ANI_TORSO.xRot = torsoDefaultState.xRot;
		instance.ANI_TORSO.yRot = torsoDefaultState.yRot;
		instance.ANI_TORSO.zRot = torsoDefaultState.zRot;

		if (leftArm != null) leftArm.zRot = leftArmDefaultState.zRot;
		if (rightArm != null) rightArm.zRot = rightArmDefaultState.zRot;

	}
}
