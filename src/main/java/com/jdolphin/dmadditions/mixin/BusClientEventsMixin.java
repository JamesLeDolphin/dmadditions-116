package com.jdolphin.dmadditions.mixin;

import com.swdteam.client.init.BusClientEvents;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraftforge.client.event.GuiScreenEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BusClientEvents.class)
public class BusClientEventsMixin {

	@Shadow(remap = false) private static ISound sound;
	/**
	 * @author Jam
	 * @reason prevent joining dmu w dma
	 */

	@Overwrite(remap = false)
	public static void guiEvent(GuiScreenEvent.InitGuiEvent event) {
		if (sound == null) {
			sound = SimpleSound.forMusic((net.minecraft.util.SoundEvent) DMSoundEvents.MUSIC_TITLE_SCREEN.get());
		}

		if (sound != null && !Minecraft.getInstance().getSoundManager().isActive(sound)) {
			Minecraft.getInstance().getSoundManager().play(sound);
		}
	}
}
