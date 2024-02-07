package com.jdolphin.dmadditions.client.model.entity;

import com.jdolphin.dmadditions.entity.JimEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;

public class JimModel extends PlayerModel<JimEntity>{
	public JimModel(float limbSizeOrSomething) {
		super(limbSizeOrSomething, true);
	}

	@Override
	public void setupAnim(JimEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_,
			float p_225597_5_, float p_225597_6_) {
		super.setupAnim(p_225597_1_, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
	}

}
