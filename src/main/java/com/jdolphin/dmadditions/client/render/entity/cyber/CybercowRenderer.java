package com.jdolphin.dmadditions.client.render.entity.cyber;

import com.jdolphin.dmadditions.client.model.entity.CybercowModel;
import com.jdolphin.dmadditions.entity.cyber.CyberCowEntity;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class CybercowRenderer extends MobRenderer<CyberCowEntity, CybercowModel> {
	public CybercowRenderer(EntityRendererManager rendererManager) {
		super(rendererManager, new CybercowModel(), 0.7f);
	}

	@Override
	public ResourceLocation getTextureLocation(CyberCowEntity cyberCowEntity) {
		return Helper.createAdditionsRL("textures/entity/cyber/cybercow.png");
	}
}
