package com.jdolphin.dmadditions.client.model.entity;

import com.jdolphin.dmadditions.entity.ChristmasCreeperEntity;

import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ChristmasCreeperModel extends CreeperModel<ChristmasCreeperEntity> implements IHasHead{
	private final ModelRenderer head;
	public ChristmasCreeperModel() {
		this(0f);
	}

	public ChristmasCreeperModel(float p_i46366_1_) {
		super();
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_i46366_1_);
		this.head.setPos(0.0F, 6.0F, 0.0F);
	}

	@Override
	public ModelRenderer getHead() {
		return head;
	}

}
