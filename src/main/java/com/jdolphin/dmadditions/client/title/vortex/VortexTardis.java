package com.jdolphin.dmadditions.client.title.vortex;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.client.gui.util.GuiUtils;
import com.swdteam.client.render.tileentity.RenderBlockTardis;
import com.swdteam.client.tardis.data.ExteriorModels;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.main.DalekMod;
import com.swdteam.model.javajson.JSONModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class VortexTardis {
	static float rotation = 0;

	public static void render() {
		rotation += 5;
		float width = Minecraft.getInstance().getWindow().getWidth();
		float height = Minecraft.getInstance().getWindow().getHeight();
		float scale = Math.max(width, height) / 512;

		//JSONModel tardisModel = ExteriorModels.getModel(new ResourceLocation(DalekMod.MODID, "models/tileentity/tardis/2018_police_box"));
		Random rand = new Random();
		List<Tardis> list = DMTardisRegistry.getRegistryAsList();
		System.out.print(list.size());
		int j = rand.nextInt(17);

		IRenderTypeBuffer buffer = Minecraft.getInstance().renderBuffers().bufferSource();

		MatrixStack matrixStack = new MatrixStack();
		matrixStack.pushPose();

		matrixStack.translate(0, 0, 1);
		matrixStack.scale(scale, scale, scale);
		RenderSystem.translatef(0f, 0f, 0f);
		GuiUtils.drawEntityOnScreen(matrixStack, width / 2, height / 2, scale, rotation, ExteriorModels.getModel(DMTardisRegistry
			.getRegistryAsList().get(j).getRegistryName()));

		matrixStack.popPose();
	}
}
