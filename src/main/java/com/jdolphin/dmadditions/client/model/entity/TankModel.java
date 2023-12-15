package com.jdolphin.dmadditions.client.model.entity;

import static com.jdolphin.dmadditions.DmAdditions.MODID;
import com.google.common.collect.ImmutableSet;
import com.jdolphin.dmadditions.entity.TankEntity;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;

import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class TankModel<T extends TankEntity> extends SegmentedModel<T> implements IModelPartReloader{

	protected JSONModel model;

	protected ModelRenderer upperBody;
	protected ModelRenderer mainBody;
	protected ModelRenderer sideLadders;
	protected ModelRenderer lowerBody;

	protected ModelRenderer wheels;

	protected ModelRenderer fence;
	protected ModelRenderer fenceCrossBeams;

	protected ModelRenderer turret;
	protected ModelRenderer turretBase;
	protected ModelRenderer turretUpper;
	protected ModelRenderer turretBarrel;

	public TankModel(){
		super();
		ModelReloaderRegistry.register(this);
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(new ResourceLocation(MODID, "models/entity/torchwood_tank.json"));
		ModelWrapper modelWrapper = model.getModelData().getModel();
		this.upperBody = modelWrapper.getPart("Upperbody"); //TODO: maybe put all body parts into one group
		this.lowerBody = modelWrapper.getPart("Lowerbody");
		this.mainBody = modelWrapper.getPart("Mainbody");

		this.turret = modelWrapper.getPart("Turret");
		this.turretBase = modelWrapper.getPart("TurretBase");
		this.turretUpper = modelWrapper.getPart("TurretUpper");
		this.turretBarrel = modelWrapper.getPart("TurretBarrel");

		this.sideLadders = modelWrapper.getPart("SideLadders");

		this.wheels = modelWrapper.getPart("Wheels");

		this.fence = modelWrapper.getPart("Fence");
		this.fenceCrossBeams = modelWrapper.getPart("FenceCrossBeams");
	}

	@Override
	public Iterable<ModelRenderer> parts() {
		return ImmutableSet.of(upperBody, lowerBody, mainBody, 
				sideLadders, 
				wheels, 
				fence, 
				turret);
	}

	@Override
	public void setupAnim(TankEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_,
			float p_225597_5_, float p_225597_6_) {

		Vector3d deltaMovement = p_225597_1_.getDeltaMovement();

		if(deltaMovement.x() == 0 && deltaMovement.z() == 0){
			this.turret.yRot = p_225597_1_.yHeadRot;
		}

	}

}
