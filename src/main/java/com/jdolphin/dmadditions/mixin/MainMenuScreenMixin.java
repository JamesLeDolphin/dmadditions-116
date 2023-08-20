package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.client.title.MenuBackGround;
import com.jdolphin.dmadditions.config.DMAClientConfig;
import com.jdolphin.dmadditions.client.title.vortex.VortexSkybox;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.client.render.tileentity.RenderBlockTardis;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Random;

@Mixin(MainMenuScreen.class)
public abstract class MainMenuScreenMixin extends Screen{
	protected MainMenuScreenMixin(ITextComponent p_i51108_1_) {
		super(p_i51108_1_);
	}


	@Mutable
	@Shadow
	@Final private RenderSkybox panorama;

	@Shadow
	@Nullable
	private String splash;
	@Shadow private boolean realmsNotificationsInitialized;
	@Shadow private Screen realmsNotificationsScreen;
	@Shadow private int copyrightWidth;
	@Shadow private int copyrightX;
	@Shadow private Button resetDemoButton;
	@Shadow private net.minecraftforge.client.gui.NotificationModUpdateScreen modUpdateNotification;
	@Shadow private void createDemoMenuOptions(int p_73972_1_, int p_73972_2_) {}
	@Shadow private void createNormalMenuOptions(int p_73969_1_, int p_73969_2_) {}
	@Final @Shadow private static ResourceLocation ACCESSIBILITY_TEXTURE = new ResourceLocation("textures/gui/accessibility.png");

	@Shadow private boolean realmsNotificationsEnabled() {
		return this.minecraft.options.realmsNotifications && this.realmsNotificationsScreen != null;
	}
	@Final @Shadow private boolean minceraftEasterEgg;
	@Final @Shadow private static ResourceLocation MINECRAFT_LOGO = new ResourceLocation("textures/gui/title/minecraft.png");
	@Final @Shadow private static ResourceLocation MINECRAFT_EDITION = new ResourceLocation("textures/gui/title/edition.png");
	@Shadow	private long fadeInStart;
	@Final @Shadow private boolean fading;

	@Mutable
	@Final private static ResourceLocation PANORAMA_OVERLAY;


	@Unique
	private static void getBg(String name) {
		PANORAMA_OVERLAY = new ResourceLocation(DmAdditions.MODID, "textures/gui/main/background/" + name + ".png");

	}

	@Inject(method = "render", at = @At(value = "HEAD"))
	public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_, CallbackInfo ci) {
		if (this.fadeInStart == 0L && this.fading) {
			this.fadeInStart = Util.getMillis();
		}



		float f = this.fading ? (float)(Util.getMillis() - this.fadeInStart) / 1000.0F : 1.0F;
		fill(p_230430_1_, 0, 0, this.width, this.height, -1);
		this.panorama.render(p_230430_4_, MathHelper.clamp(f, 0.0F, 1.0F));
		int i = 274;
		int j = this.width / 2 - 137;
		int k = 30;
		this.minecraft.getTextureManager().bind(PANORAMA_OVERLAY);
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.fading ? (float)MathHelper.ceil(MathHelper.clamp(f, 0.0F, 1.0F)) : 1.0F);
		blit(p_230430_1_, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
		float f1 = this.fading ? MathHelper.clamp(f - 1.0F, 0.0F, 1.0F) : 1.0F;
		int l = MathHelper.ceil(f1 * 255.0F) << 24;
		if ((l & -67108864) != 0) {
			this.minecraft.getTextureManager().bind(MINECRAFT_LOGO);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, f1);
			if (this.minceraftEasterEgg) {
				this.blitOutlineBlack(j, 30, (p_238660_2_, p_238660_3_) -> {
					this.blit(p_230430_1_, p_238660_2_ + 0, p_238660_3_, 0, 0, 99, 44);
					this.blit(p_230430_1_, p_238660_2_ + 99, p_238660_3_, 129, 0, 27, 44);
					this.blit(p_230430_1_, p_238660_2_ + 99 + 26, p_238660_3_, 126, 0, 3, 44);
					this.blit(p_230430_1_, p_238660_2_ + 99 + 26 + 3, p_238660_3_, 99, 0, 26, 44);
					this.blit(p_230430_1_, p_238660_2_ + 155, p_238660_3_, 0, 45, 155, 44);
				});
			} else {
				this.blitOutlineBlack(j, 30, (p_238657_2_, p_238657_3_) -> {
					this.blit(p_230430_1_, p_238657_2_ + 0, p_238657_3_, 0, 0, 155, 44);
					this.blit(p_230430_1_, p_238657_2_ + 155, p_238657_3_, 0, 45, 155, 44);
				});
			}

			this.minecraft.getTextureManager().bind(MINECRAFT_EDITION);
			blit(p_230430_1_, j + 88, 67, 0.0F, 0.0F, 98, 14, 128, 16);
			net.minecraftforge.client.ForgeHooksClient.renderMainMenu((MainMenuScreen) (Object)this, p_230430_1_, this.font, this.width, this.height, l);
			if (this.splash != null) {
				RenderSystem.pushMatrix();
				RenderSystem.translatef((float)(this.width / 2 + 90), 70.0F, 0.0F);
				RenderSystem.rotatef(-20.0F, 0.0F, 0.0F, 1.0F);
				float f2 = 1.8F - MathHelper.abs(MathHelper.sin((float)(Util.getMillis() % 1000L) / 1000.0F * ((float)Math.PI * 2F)) * 0.1F);
				f2 = f2 * 100.0F / (float)(this.font.width(this.splash) + 32);
				RenderSystem.scalef(f2, f2, f2);
				drawCenteredString(p_230430_1_, this.font, this.splash, 0, -8, 16776960 | l);
				RenderSystem.popMatrix();
			}

			String s = "Minecraft " + SharedConstants.getCurrentVersion().getName();
			if (this.minecraft.isDemo()) {
				s = s + " Demo";
			} else {
				s = s + ("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : "/" + this.minecraft.getVersionType());
			}

			if (this.minecraft.isProbablyModded()) {
				s = s + I18n.get("menu.modded");
			}

			net.minecraftforge.fml.BrandingControl.forEachLine(true, true, (brdline, brd) ->
				drawString(p_230430_1_, this.font, brd, 2, this.height - ( 10 + brdline * (this.font.lineHeight + 1)), 16777215 | l)
			);

			net.minecraftforge.fml.BrandingControl.forEachAboveCopyrightLine((brdline, brd) ->
				drawString(p_230430_1_, this.font, brd, this.width - font.width(brd), this.height - (10 + (brdline + 1) * ( this.font.lineHeight + 1)), 16777215 | l)
			);

			drawString(p_230430_1_, this.font, "Copyright Mojang AB. Do not distribute!", this.copyrightX, this.height - 10, 16777215 | l);
			if (p_230430_2_ > this.copyrightX && p_230430_2_ < this.copyrightX + this.copyrightWidth && p_230430_3_ > this.height - 10 && p_230430_3_ < this.height) {
				fill(p_230430_1_, this.copyrightX, this.height - 1, this.copyrightX + this.copyrightWidth, this.height, 16777215 | l);
			}

			for(Widget widget : this.buttons) {
				widget.setAlpha(f1);
			}

			super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
			if (this.realmsNotificationsEnabled() && f1 >= 1.0F) {
				this.realmsNotificationsScreen.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
			}
			modUpdateNotification.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);

		}
	}

	@Inject(method = "init", at = @At(value = "TAIL"))
	private void init(CallbackInfo ci) {
		if (DMAClientConfig.dma_classic.get()) {
			int i = new Random().nextInt(MenuBackGround.values().length);
			getBg(MenuBackGround.values()[i].toString().toLowerCase());
		}
		if (DMAClientConfig.dma_vortex.get()) {
			panorama = new VortexSkybox(DMAClientConfig.getVortex());
		}
	}

}
