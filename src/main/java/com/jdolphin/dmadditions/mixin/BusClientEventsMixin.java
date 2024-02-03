package com.jdolphin.dmadditions.mixin;

import com.swdteam.client.gui.GuiDMU;
import com.swdteam.client.init.BusClientEvents;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.main.DMConfig;
import com.swdteam.util.helpers.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.lang.reflect.Field;
import java.util.function.Function;

@Mixin(BusClientEvents.class)
public class BusClientEventsMixin {

	@Shadow private static ISound sound;
	/**
	 * @author Jam
	 * @reason prevent joining dmu w dma
	 */

	@Overwrite
	public static void guiEvent(GuiScreenEvent.InitGuiEvent event) {
		if (sound == null) {
			sound = SimpleSound.forMusic((net.minecraft.util.SoundEvent) DMSoundEvents.MUSIC_TITLE_SCREEN.get());
		}

		if (sound != null && !Minecraft.getInstance().getSoundManager().isActive(sound)) {
			Minecraft.getInstance().getSoundManager().play(sound);
		}
	}
}
