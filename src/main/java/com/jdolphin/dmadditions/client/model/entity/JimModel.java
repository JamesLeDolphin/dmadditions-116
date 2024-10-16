package com.jdolphin.dmadditions.client.model.entity;

import com.jdolphin.dmadditions.entity.JimEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;

public class JimModel extends PlayerModel<JimEntity> {
	public JimModel(float limbSizeOrSomething) {
		super(limbSizeOrSomething, true);
	}

	@Override
	public void setupAnim(JimEntity jimEntity, float v, float v1, float v2,
						  float v3, float v4) {
		super.setupAnim(jimEntity, v, v1, v2, v3, v4);
	}

}
