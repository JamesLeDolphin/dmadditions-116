package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.DMAdditions;
import com.jdolphin.dmadditions.client.model.entity.IceWarriorModel;
import com.jdolphin.dmadditions.entity.IceWarriorEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class IceWarriorRenderer extends BipedRenderer<IceWarriorEntity, IceWarriorModel> {

	public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(DMAdditions.MODID, "textures/entity/ice_warrior.png");

    public IceWarriorRenderer(EntityRendererManager entityRendererManager){
		super(entityRendererManager, new IceWarriorModel(), 1);
	}

	@Override
	public  ResourceLocation getTextureLocation(IceWarriorEntity iceWarriorEntity) {
		return TEXTURE_LOCATION;
	}
}
