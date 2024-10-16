package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableSet;
import com.jdolphin.dmadditions.entity.TankEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class TankModel<T extends TankEntity> extends SegmentedModel<T> implements IModelPartReloader {

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

	public TankModel() {
		super();
		ModelReloaderRegistry.register(this);
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/torchwood_tank.json"));
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
			turret);
	}

	@Override
	public void setupAnim(TankEntity entity, float v, float v1, float v2,
						  float v3, float v4) {

		this.turret.yRot = entity.turretRot - (float) Math.toRadians(entity.yRot);

// 		Minecraft minecraft = Minecraft.getInstance();
// 		ChatUtil.sendMessageToPlayer(minecraft.player, String.format("%s", entity.turretRot), MessageType.STATUS_BAR);

	}

}
