package com.jdolphin.dmadditions.client.model.entity;

import com.jdolphin.dmadditions.entity.IceGovernessEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.JSONModel.ModelInformation;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

import static com.jdolphin.dmadditions.DmAdditions.MODID;

public class IceGovernessModel extends BipedModel<IceGovernessEntity> implements IModelPartReloader{

	private JSONModel model;

	public IceGovernessModel(float p_i1148_1_) {
		super(p_i1148_1_);
		ModelReloaderRegistry.register(this);
	}

	@Override
	public void setupAnim(IceGovernessEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_,
			float p_225597_5_, float p_225597_6_) {
		super.setupAnim(p_225597_1_, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/ice_governess.json"));

		ModelWrapper modelwrapper = model.getModelData().getModel();
		this.head = modelwrapper.getPart("Head");
		this.body = modelwrapper.getPart("Body");
		this.rightArm = modelwrapper.getPart("RightArm");
		this.leftArm = modelwrapper.getPart("LeftArm");
		this.rightLeg = modelwrapper.getPart("RightLeg");
		this.leftLeg = modelwrapper.getPart("LeftLeg");

		this.hat.visible = false;

	}

	public ResourceLocation getTexture(){
		ModelInformation modelData = this.model.getModelData();
		if(modelData == null) return null;
		return modelData.getTexture();
	}

}
