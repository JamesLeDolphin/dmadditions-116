package com.jdolphin.dmadditions.client.model.entity.cyber;

import com.jdolphin.dmadditions.entity.cyber.DMACybermanEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.BipedModel;

public class CyberWarriorModel extends AbstractCybermanModel {
	public JSONModel model;

	public CyberWarriorModel() {
		super(ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/cyber/cyberwarrior.json")));
		ModelReloaderRegistry.register(this);
	}
}
