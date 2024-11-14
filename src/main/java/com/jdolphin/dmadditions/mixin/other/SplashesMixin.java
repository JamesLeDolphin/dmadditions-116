package com.jdolphin.dmadditions.mixin.other;

import com.jdolphin.dmadditions.advent.AdventUnlock;
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
				add("James blames Torchwood");

				add("Sam secretly did all the work");
				add(":3");

				add("Not to be confused with DMSubtractions!");
				add("Also try Wicked!");

				add("java.io.FileNotFoundException");
				add("java.lang.NullPointerException");

				add("Soon™");
				add("We do a little hacking");

				add("JAMES DID NOTHING WRONG!!!");
				add("James was here");

				add("Torchwood is responsible for everything");
				add("Don't trust Torchwood");

				// spellchecker:ignore dodgily
				add("Very dodgily I must add");
			}
		};

		if (AdventUnlock.isDecember()) {
			splashes.add("DM Advent Calendar!?");
		}

		if (AdventUnlock.getCalendar().get(Calendar.MONTH) == Calendar.APRIL && AdventUnlock.getDate() == 1) {
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
