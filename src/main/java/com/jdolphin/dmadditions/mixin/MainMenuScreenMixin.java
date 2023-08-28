package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.client.title.MenuBackGround;
import com.jdolphin.dmadditions.client.title.vortex.VortexSkybox;
import com.jdolphin.dmadditions.config.DMAClientConfig;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.gui.util.GuiUtils;
import com.swdteam.client.tardis.data.ExteriorModels;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.main.DalekMod;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

@Mixin(MainMenuScreen.class)
public abstract class MainMenuScreenMixin extends Screen{
	protected MainMenuScreenMixin(ITextComponent p_i51108_1_) {
		super(p_i51108_1_);
	}


	@Mutable
	@Shadow
	@Final private RenderSkybox panorama;

	@Unique private static int i;
	@Unique private static int rotation = 0;

	@Mutable
	@Shadow
	@Final private static ResourceLocation PANORAMA_OVERLAY;

	@Unique private static ResourceLocation TARDIS;

	@Unique
	private static void dmadditions_116$getBg(String name) {
		PANORAMA_OVERLAY = new ResourceLocation(DmAdditions.MODID, "textures/gui/main/background/" + name + ".png");
	}
	@Unique private static void getTardis(String modid, String path) {
		TARDIS = new ResourceLocation(modid, "models/tileentity/tardis/" + path + ".json");
	}

	@Inject(method = "render", at = @At(value = "HEAD"))
	public void render(MatrixStack stack, int width, int height, float scale, CallbackInfo ci) {
		this.minecraft.getTextureManager().bind(PANORAMA_OVERLAY);
		rotation += 5;
		if (i > 0) {
			GuiUtils.drawEntityOnScreen(stack, width, height, scale, rotation, ExteriorModels.getModel(TARDIS));
			ExteriorModels.resetDoorAnimation(TARDIS);
		}
	}

	@Inject(method = "init", at = @At(value = "TAIL"))
	private void init(CallbackInfo ci) {
		if (DMAClientConfig.dma_classic.get()) {
			int i = new Random().nextInt(MenuBackGround.values().length);
			dmadditions_116$getBg(MenuBackGround.values()[i].getName());
		}
		if (DMAClientConfig.dma_vortex.get()) {
			panorama = new VortexSkybox(DMAClientConfig.getVortex());
			List<Tardis> list = DMTardisRegistry.getRegistryAsList();
			if (!list.isEmpty()) {
				Random rand = new Random();
				i = rand.nextInt(list.size());
				Tardis tardis = DMTardisRegistry.getRegistryAsList().get(i);
				if (tardis.getRegistryName().getNamespace().equals(DmAdditions.MODID)) {i = rand.nextInt(list.size());}
				getTardis(tardis.getRegistryName().getNamespace(), tardis.getRegistryName().getPath());
			}
		}
	}
}
