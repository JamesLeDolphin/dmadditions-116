package com.jdolphin.dmadditions.mixin.client;

import com.jdolphin.dmadditions.advent.TimedUnlock;
import com.swdteam.client.data.Splashes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

@Mixin(Splashes.class)
public class SplashesMixin {
	@Inject(method = "load", at = @At("TAIL"), remap = false)
	private static void load(CallbackInfo ci) {

		ArrayList<String> splashes = new ArrayList<String>(Arrays.asList(Splashes.SPLASHES)) {
			{
				add("I blame James");
				add("Bloody Torchwood!");

				add("I'll ask Bug");
				add("I'll ask Json");

				add("The secret is our public open-source technology!");

				add("Sam secretly did all the work");
				add(":3");

				add("Not to be confused with DMSubtractions!");
				add("Also try Wicked!");

				add("java.io.FileNotFoundException");
				add("java.lang.NullPointerException");

				add("Soon™");
				add("We do a little hacking");

				add("James was here");
				add("Don't trust Torchwood");

				add("Very dodgily I must add");
				add("Stop typing everything I say");
			}
		};

		if (TimedUnlock.isDecember()) {
			splashes.add("DM Advent Calendar!?");
		}

		if (TimedUnlock.isHalloween()) {
			splashes.add("Are YOU trick-or-treating!?");
			splashes.add("Get yourself some sweets!");
			splashes.add("Is that dalek wearing a pumpkin?");
			splashes.add("Spoopy");
		}

		if (TimedUnlock.getCalendar().get(Calendar.MONTH) == Calendar.APRIL && TimedUnlock.getDate() == 1) {
			Random random = new Random();

			if (random.nextBoolean()) {
				splashes.replaceAll(string -> new StringBuilder(string).reverse().toString());
			}

			if (random.nextFloat() < 0.02) {
				splashes.replaceAll(string -> "§k" + string);
			}
		}

		Splashes.SPLASHES = splashes.toArray(new String[0]);

	}
}
