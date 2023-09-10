package com.jdolphin.dmadditions.mixin;

import com.swdteam.client.gui.GuiDMU;
import com.swdteam.client.init.BusClientEvents;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.main.DMConfig;
import com.swdteam.util.helpers.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(BusClientEvents.class)
public class BusClientEventsMixin {

	/**
	 * @author JamesLeDolphin
	 * @reason Make DMU button be in menu screen
	 */
	@Overwrite
	public static void guiEvent(GuiScreenEvent.InitGuiEvent event) {
		if (event.getGui() instanceof MainMenuScreen && (Boolean) DMConfig.CLIENT.customTitleScreen.get()) {
			MainMenuScreen gui = (MainMenuScreen)event.getGui();
			ReflectionHelper.setValuePrivateDeclaredField("splash", MainMenuScreen.class, gui, (Object)null);
			ReflectionHelper.setValuePrivateDeclaredField("field_73975_c", MainMenuScreen.class, gui, (Object)null);

			for(int i = 0; i < gui.buttons.size(); ++i) {
				Widget w = (Widget)gui.buttons.get(i);
				if (w.getMessage() instanceof TranslationTextComponent && ((TranslationTextComponent)w.getMessage()).getKey().equalsIgnoreCase("menu.multiplayer")) {
					w.setWidth(98);
					Button b = new Button(gui.width / 2 + 2, gui.height / 4 + 72, 98, 20, new StringTextComponent("Dalek Mod Server"), (button) -> {
						Minecraft.getInstance().setScreen(new GuiDMU(gui));
					});
					gui.children.add(b);
					gui.buttons.add(b);
				}
			}

			if (sound == null) {
				sound = SimpleSound.forMusic((net.minecraft.util.SoundEvent) DMSoundEvents.MUSIC_TITLE_SCREEN.get());
			}

			if (sound != null && !Minecraft.getInstance().getSoundManager().isActive(sound)) {
				Minecraft.getInstance().getSoundManager().play(sound);
			}
		}

	}
}
