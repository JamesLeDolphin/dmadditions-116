package com.jdolphin.dmadditions.client.model.tileentity;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.*;
import net.minecraft.util.ResourceLocation;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TimeKeeperModel extends Model {

	public TimeKeeperModel(Function<ResourceLocation, RenderType> p_i225947_1_) {
		super(p_i225947_1_);
	}


	@Override
	public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {

	}
}
