package com.jdolphin.dmadditions.entity.render;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.entity.timelord.AriModel;
import com.jdolphin.dmadditions.entity.timelord.TimeLordEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;


public class AriRenderer extends MobRenderer<TimeLordEntity, AriModel<TimeLordEntity>>
{
	protected static final ResourceLocation TEXTURE =
		new ResourceLocation(DmAdditions.MODID, "textures/entity/ari.png");

	public AriRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new AriModel<>(), 0.7F);
	}

	@Override
	public ResourceLocation getTextureLocation(TimeLordEntity timeLordEntity) {
		return null;
	}
	
	public ResourceLocation getEntityTexture(TimeLordEntity entity) {
		return TEXTURE;
	}
}