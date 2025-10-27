package com.jdolphin.dmadditions.client.render.tileentity;

import com.jdolphin.dmadditions.common.block.tardis.BrokenTardisBlock;
import com.jdolphin.dmadditions.common.init.DMABrokenTardisTypes;
import com.jdolphin.dmadditions.common.tileentity.BrokenTardisTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.tardis.data.ExteriorModels;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelRendererWrapper;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class BrokenTardisRenderer extends TileEntityRenderer<BrokenTardisTileEntity> {
	public static JSONModel MODEL_TARDIS = ModelLoader.loadModel(DMABrokenTardisTypes.DEFAULT_EXT);

	public BrokenTardisRenderer(TileEntityRendererDispatcher dispatcher) {
		super(dispatcher);
	}

	private void renderDoors(BrokenTardisTileEntity tile) {
		ResourceLocation type = tile.getExteriorType();
		ExteriorModels.DoorAnimations doors = ExteriorModels.getDoorAnimation(type);
		if (doors != null && doors.getDoorAnimations() != null) {
			for (ExteriorModels.DoorAnimations.TardisAnimation animation : doors.getDoorAnimations()) {
				ModelRendererWrapper renderer = MODEL_TARDIS.getModelData().getModel().getPart(animation.getShapeName());
				float doorRot = animation.getDoorType().equalsIgnoreCase("right") && tile.isOpen() ? 1.5f : 0;
				switch (animation.getMode().toLowerCase()) {
					case "rotate":
						switch (animation.getAxis().toLowerCase()) {
							case "x":
								renderer.xRot = (float) Math.toRadians(doorRot * 90.0F * animation.getAmplifier());
								continue;
							case "y":
								renderer.yRot = (float) Math.toRadians(doorRot * 90.0F * animation.getAmplifier());
								continue;
							case "z":
								renderer.zRot = (float) Math.toRadians(doorRot * 90.0F * animation.getAmplifier());
							default:
								continue;
						}
					case "translate":
						Vector3f initialRotation = renderer.getInitialRotation();
						switch (animation.getAxis().toLowerCase()) {
							case "x":
								renderer.x = initialRotation.x() + doorRot * 20.0F * animation.getAmplifier();
								break;
							case "y":
								renderer.y = initialRotation.y() + doorRot * 20.0F * animation.getAmplifier();
								break;
							case "z":
								renderer.z = initialRotation.z() + doorRot * 20.0F * animation.getAmplifier();
						}
				}
			}
		}
	}

	@Override
	public void render(BrokenTardisTileEntity tile, float v, MatrixStack stack, IRenderTypeBuffer buffer, int i, int i1) {
		ResourceLocation type = tile.getExteriorType();

		if (type != null) {
			MODEL_TARDIS = ModelLoader.loadModel(type);
			if (MODEL_TARDIS != null) {
				stack.pushPose();
				BlockState state = tile.getBlockState();
				Direction direction = state.getValue(BrokenTardisBlock.FACING);

				stack.mulPose(Vector3f.ZP.rotationDegrees(180));

				stack.translate(-0.5, -1.5, 0.5);
				stack.mulPose(Vector3f.YP.rotationDegrees(direction.toYRot()));
				renderDoors(tile);
				MODEL_TARDIS.getModelData().getModel().renderToBuffer(stack, buffer, i, i1, 1.0f, 1.0f, 1.0f, 1.0f);

				stack.popPose();
			}
		}
	}
}
