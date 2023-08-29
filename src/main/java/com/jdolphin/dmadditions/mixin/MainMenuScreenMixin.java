package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.client.title.MenuBackGround;
import com.jdolphin.dmadditions.client.title.vortex.VortexSkybox;
import com.jdolphin.dmadditions.config.DMAClientConfig;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.gui.util.GuiUtils;
import com.swdteam.client.tardis.data.ExteriorModels;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.gui.NotificationModUpdateScreen;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(MainMenuScreen.class)
public abstract class MainMenuScreenMixin extends Screen{
	protected MainMenuScreenMixin(ITextComponent p_i51108_1_) {
		super(p_i51108_1_);
	}


	@Mutable
	@Shadow
	@Final private RenderSkybox panorama;

	@Unique private static long rotation = 0;

	@Mutable
	@Shadow
	@Final private static ResourceLocation PANORAMA_OVERLAY;

	@Shadow
	private NotificationModUpdateScreen modUpdateNotification;

	@Shadow
	public abstract boolean shouldCloseOnEsc();

	@Unique
	private static void dmadditions_116$getBg(String name) {
		PANORAMA_OVERLAY = new ResourceLocation(DmAdditions.MODID, "textures/gui/main/background/" + name + ".png");
	}
	@Unique
	private static JSONModel TARDIS;


	@Inject(method = "render", at = @At(value = "HEAD"))
	public void render(MatrixStack stack, int width, int height, float scale, CallbackInfo ci) {
		this.minecraft.getTextureManager().bind(PANORAMA_OVERLAY);
		rotation += 5;
		GuiUtils.drawEntityOnScreen(stack, width / 2, height / 2, scale, rotation, TARDIS);
	}

	@Inject(method = "init", at = @At(value = "TAIL"))
	private void init(CallbackInfo ci) {
		if (DMAClientConfig.dma_classic.get()) {
			int j = new Random().nextInt(MenuBackGround.values().length);
			dmadditions_116$getBg(MenuBackGround.values()[j].getName());
		}
		if (DMAClientConfig.dma_vortex.get()) {
			panorama = new VortexSkybox(DMAClientConfig.getVortex());
			Random rand = new Random();
			if (!DmAdditions.EXTERIORS.isEmpty()) {
				int i = rand.nextInt(DmAdditions.EXTERIORS.size());
				TARDIS = ModelLoader.loadModel(DmAdditions.EXTERIORS.get(1).getModel(1));
			}
		}
	}
}
