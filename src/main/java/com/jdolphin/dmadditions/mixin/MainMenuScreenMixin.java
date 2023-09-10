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
import net.minecraft.client.gui.AccessibilityScreen;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.realms.RealmsBridgeScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.gui.NotificationModUpdateScreen;
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

	/* Gotta make a mixin to this: Lcom/swdteam/client/init/BusClientEvents;guiEvent(Lnet/minecraftforge/client/event/GuiScreenEvent$InitGuiEvent;)V
	To get the DMU button into here
	 */

	@Mutable
	@Shadow
	@Final private RenderSkybox panorama;

	@Unique private static long rotation = 0;

	@Mutable
	@Shadow
	@Final private static ResourceLocation PANORAMA_OVERLAY;

	@Shadow
	private void realmsButtonClicked() {
		RealmsBridgeScreen realmsbridgescreen = new RealmsBridgeScreen();
		realmsbridgescreen.switchToRealms(this);
	}

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
	@Shadow @Nullable private String splash;
	@Shadow private Button resetDemoButton;

	@Shadow private int copyrightWidth;
	@Shadow private int copyrightX;
	@Shadow private Screen realmsNotificationsScreen;
	@Shadow private boolean realmsNotificationsInitialized;
	@Shadow private boolean realmsNotificationsEnabled() {
		return this.minecraft.options.realmsNotifications && this.realmsNotificationsScreen != null;
	}
	@Shadow private void createDemoMenuOptions(int p_73972_1_, int p_73972_2_) {}

	/**
	 * @author JamesLeDolphin
	 * @reason I wanted to try make the main menu more 1.12 styled lol
	 */
	@Overwrite
	public void init() {
		if (DMAClientConfig.dma_classic.get()) {
			if (this.splash == null) {
				this.splash = this.minecraft.getSplashManager().getSplash();
			}

			this.copyrightWidth = this.font.width("Copyright Mojang AB. Do not distribute!");
			this.copyrightX = this.width - this.copyrightWidth - 2;
			int i = 24;
			int j = this.height / 4 + 48;
			Button modButton = null;
			if (this.minecraft.isDemo()) {
				this.createDemoMenuOptions(j, 24);
			} else {

				this.addButton(new Button(this.width / 2 - 100, j, 200, 20, new TranslationTextComponent("menu.singleplayer"), (p_213089_1_) -> {
					this.minecraft.setScreen(new WorldSelectionScreen(this));
				}));
				boolean flag = this.minecraft.allowsMultiplayer();
				Button.ITooltip button$itooltip = flag ? Button.NO_TOOLTIP : (p_238659_1_, p_238659_2_, p_238659_3_, p_238659_4_) -> {
					if (!p_238659_1_.active) {
						this.renderTooltip(p_238659_2_, this.minecraft.font.split(new TranslationTextComponent("title.multiplayer.disabled"), Math.max(this.width / 2 - 43, 170)), p_238659_3_, p_238659_4_);
					}

				};
				(this.addButton(new Button(this.width / 2 - 100, j + 24 * 1, 200, 20, new TranslationTextComponent("menu.multiplayer"), (p_213095_1_) -> {
					Screen screen = (Screen)(this.minecraft.options.skipMultiplayerWarning ? new MultiplayerScreen(this) : new MultiplayerWarningScreen(this));
					this.minecraft.setScreen(screen);
				}, button$itooltip))).active = flag;
				(this.addButton(new Button(this.width / 2 + 2, j + 24 * 2, 98, 20, new TranslationTextComponent("menu.online"), (p_238661_1_) -> {
					this.realmsButtonClicked();
				}, button$itooltip))).active = flag;

				modButton = this.addButton(new Button(this.width / 2 - 100, j + 24 * 2, 98, 20, new TranslationTextComponent("fml.menu.mods"), button -> {
					this.minecraft.setScreen(new net.minecraftforge.fml.client.gui.screen.ModListScreen(this));
				}));
			}
			modUpdateNotification = net.minecraftforge.client.gui.NotificationModUpdateScreen.init((MainMenuScreen)(Object) this, modButton);

			this.addButton(new ImageButton(this.width / 2 - 124, j + 72 + 12, 20, 20, 0, 106, 20, Button.WIDGETS_LOCATION, 256, 256, (p_213090_1_) -> {
				this.minecraft.setScreen(new LanguageScreen(this, this.minecraft.options, this.minecraft.getLanguageManager()));
			}, new TranslationTextComponent("narrator.button.language")));
			this.addButton(new Button(this.width / 2 - 100, j + 72 + 12, 98, 20, new TranslationTextComponent("menu.options"), (p_213096_1_) -> {
				this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
			}));
			this.addButton(new Button(this.width / 2 + 2, j + 72 + 12, 98, 20, new TranslationTextComponent("menu.quit"), (p_213094_1_) -> {
				this.minecraft.stop();
			}));
			this.addButton(new Button(this.width / 2 + 104, j + 72 + 12, 20, 20, new TranslationTextComponent("narrator.button.accessibility"), (p_213088_1_) -> {
				this.minecraft.setScreen(new AccessibilityScreen(this, this.minecraft.options));
			}));
			this.minecraft.setConnectedToRealms(false);
			if (this.minecraft.options.realmsNotifications && !this.realmsNotificationsInitialized) {
				RealmsBridgeScreen realmsbridgescreen = new RealmsBridgeScreen();
				this.realmsNotificationsScreen = realmsbridgescreen.getNotificationScreen(this);
				this.realmsNotificationsInitialized = true;
			}

			if (this.realmsNotificationsEnabled()) {
				this.realmsNotificationsScreen.init(this.minecraft, this.width, this.height);
			}

		}
	}


	@Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
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
