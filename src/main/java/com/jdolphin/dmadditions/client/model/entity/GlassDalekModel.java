package com.jdolphin.dmadditions.client.model.entity;

import com.jdolphin.dmadditions.entity.dalek.GlassDalek;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.dalek.IDalek;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMDalekRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class GlassDalekModel extends EntityModel<GlassDalek> implements IModelPartReloader {
	private boolean showBomb = false;
	private JSONModel model;
	public ModelRenderer LIGHT_LEFT;
	public ModelRenderer LIGHT_RIGHT;
	public ModelRenderer ANI_HEAD;
	public ModelRenderer ANI_EYE;
	public ModelRenderer ANI_NECK;
	public ModelRenderer ANI_TORSO;

	public GlassDalekModel(JSONModel model) {
		super(RenderType::entityTranslucent);
		this.model = model;
		ModelReloaderRegistry.register(this);
	}

	public void setPartEye(ModelRenderer aNI_EYE) {
		this.ANI_EYE = aNI_EYE;
	}

	public void setPartHead(ModelRenderer aNI_HEAD) {
		this.ANI_HEAD = aNI_HEAD;
	}

	public void setPartNeck(ModelRenderer aNI_NECK) {
		this.ANI_NECK = aNI_NECK;
	}

	public void setPartTorso(ModelRenderer aNI_TORSO) {
		this.ANI_TORSO = aNI_TORSO;
	}

	public void setupAnim(GlassDalek entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity.isNitroNined()) {
			this.showBomb = true;
		} else {
			this.showBomb = false;
		}

		double rotation = Math.toRadians((double)netHeadYaw);
		if (this.ANI_HEAD != null) {
			this.ANI_HEAD.yRot = (float)rotation;
		}

		double rotationEye = Math.toRadians((double)headPitch);
		if (this.ANI_EYE != null) {
			this.ANI_EYE.xRot = (float)rotationEye;
		}

		IDalek dalek = entity.getDalekData();
		if (dalek != null) {
			int i;
			String arm;
			ModelRenderer part;
			double rotationRightArm;
			for(i = 0; i < dalek.getLeftArmAttatchments().size(); ++i) {
				arm = (String)dalek.getLeftArmAttatchments().get(i);
				part = this.getPart(arm);
				if (part != ModelLoader.NULL_PART) {
					if (entity.isLeftArm(arm)) {
						part.visible = true;
						rotationRightArm = Math.toRadians((double)headPitch) / 1.5;
						if (part != null) {
							part.xRot = (float)rotationRightArm;
							if (entity.getDalekData() == DMDalekRegistry.SPECIAL_WEAPONS_DALEK) {
								if (entity.getFuse() >= 0) {
									part.yRot = entity.level.random.nextFloat() / 16.0F;
									part.xRot += entity.level.random.nextFloat() / 16.0F;
								} else {
									part.yRot = 0.0F;
								}
							}
						}
					} else {
						part.visible = false;
					}
				}
			}

			for(i = 0; i < dalek.getRightArmAttatchments().size(); ++i) {
				arm = (String)dalek.getRightArmAttatchments().get(i);
				part = this.getPart(arm);
				if (part != ModelLoader.NULL_PART) {
					if (entity.isRightArm(arm)) {
						part.visible = true;
						rotationRightArm = Math.toRadians((double)headPitch) / -1.5;
						if (part != null) {
							part.xRot = (float)rotationRightArm;
						}
					} else {
						part.visible = false;
					}
				}
			}
		}

	}

	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (this.model != null) {
			this.model.getModelData().getModel().renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, true);
			if (this.showBomb) {
				IBakedModel p_191962_4_ = Minecraft.getInstance().getItemRenderer().getModel(new ItemStack((IItemProvider) DMBlocks.NITRO9.get()), (World)null, (LivingEntity)null);
				IRenderTypeBuffer.Impl buf = Minecraft.getInstance().renderBuffers().bufferSource();
				matrixStack.translate(-0.05000000074505806, 0.5, 0.75);
				matrixStack.mulPose(Vector3f.XP.rotation(10.0F));
				Minecraft.getInstance().getBlockRenderer().renderSingleBlock(((Block)DMBlocks.NITRO9.get()).defaultBlockState(), matrixStack, buf, packedLight, packedOverlay);
			}
		}

	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	public ModelRenderer getPart(String s) {
		return this.model != null && this.model.getModelData() != null && this.model.getModelData().getModel() != null ? this.model.getModelData().getModel().getPart(s) : ModelLoader.NULL_PART;
	}

	public JSONModel getModelData() {
		return this.model;
	}

	public void init() {
		this.setPartEye(this.getPart("Eye"));
		this.setPartHead(this.getPart("Head"));
		this.setPartNeck(this.getPart("Neck"));
		this.setPartTorso(this.getPart("Torso"));
		this.LIGHT_LEFT = this.getPart("LightLeft");
		this.LIGHT_RIGHT = this.getPart("LightRight");
	}

	public JSONModel getModel() {
		return this.model;
	}
}

