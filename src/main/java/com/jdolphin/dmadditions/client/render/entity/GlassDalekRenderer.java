package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.client.model.entity.GlassDalekModel;
import com.jdolphin.dmadditions.entity.dalek.GlassDalek;
import com.swdteam.client.model.ModelDalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.main.DalekMod;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GlassDalekRenderer extends MobRenderer<GlassDalek, GlassDalekModel> {
	public GlassDalekRenderer(EntityRendererManager rendererManager) {
		super(rendererManager, new GlassDalekModel((JSONModel)null), 0.8F);
	}

	public Vector3d getRenderOffset(GlassDalek entity, float f) {
		if (entity.getDalekData() != null && entity.getDalekData().getModel() != null) {
			this.model = new GlassDalekModel(ModelLoader.loadModel(new ResourceLocation(DalekMod.MODID, "models/entity/dalek/glass_dalek_with_mutant.json")));
		}

		return super.getRenderOffset(entity, f);
	}

	public ResourceLocation getTextureLocation(GlassDalek entity) {
		return ((GlassDalekModel)this.getModel()).getModelData() != null && ((GlassDalekModel)this.getModel()).getModelData().getModelData() != null ? ((GlassDalekModel)this.model).getModelData().getModelData().getTexture() : TextureManager.INTENTIONAL_MISSING_TEXTURE;
	}

	protected boolean shouldShowName(GlassDalek entity) {
		return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
	}
}
