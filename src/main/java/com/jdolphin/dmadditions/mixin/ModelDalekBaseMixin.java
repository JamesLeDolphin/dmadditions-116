package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.entity.dalek.IDalekEntityMixin;
import com.swdteam.client.model.ModelDalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelDalekBase.class)
public class ModelDalekBaseMixin {
	@Shadow
	public ModelRenderer ANI_HEAD;

	@Shadow
	public ModelRenderer ANI_NECK;

	@Shadow
	public ModelRenderer ANI_TORSO;

	private ModelRenderer headDefaultState;
	private ModelRenderer neckDefaultState;
	private ModelRenderer torsoDefaultState;

	private ModelRenderer leftArmDefaultState;
	private ModelRenderer rightArmDefaultState;

	@Inject(method = "init", at = @At("TAIL"), remap = false)
	public void init(CallbackInfo ci) {
		headDefaultState = ANI_HEAD.createShallowCopy();
		neckDefaultState = ANI_NECK.createShallowCopy();
		torsoDefaultState = ANI_TORSO.createShallowCopy();
	}

	@Inject(method = "setupAnim(Lcom/swdteam/common/entity/dalek/DalekEntity;FFFFF)V",
		at = @At("HEAD"), remap = false, cancellable = true)
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

		if (leftArm != null) leftArm.zRot = leftArmDefaultState.zRot;
		if (rightArm != null) rightArm.zRot = rightArmDefaultState.zRot;

	}
}
