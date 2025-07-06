package com.jdolphin.dmadditions.common.util;

import com.google.common.collect.Lists;
import com.jdolphin.dmadditions.common.advent.TimedUnlock;
import com.swdteam.client.data.Splashes;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class DMASplashes {

	public static List<String> getSplashes() {
		List<String> list = Lists.newArrayList(Splashes.SPLASHES);

		list.add("I blame James");
		list.add("Bloody Torchwood!");

		list.add("I'll ask Bug");
		list.add("I'll ask Json");

		list.add("The secret is our public open-source technology!");

		list.add("Sam secretly did all the work");
		list.add(":3");

		list.add("Not to be confused with DMSubtractions!");
		list.add("Also try Wicked!");

		list.add("java.io.FileNotFoundException");
		list.add("java.lang.NullPointerException");

		list.add("Soon™");
		list.add("We do a little hacking");

		list.add("James was here");
		list.add("Don't trust Torchwood");

		list.add("Very dodgily I must add");
		list.add("Stop typing everything I say");


		if (TimedUnlock.isDecember()) {
			list.add("DM Advent Calendar!?");
		}

		if (TimedUnlock.isHalloween()) {
			list.add("Are YOU trick-or-treating!?");
			list.add("Get yourself some sweets!");
			list.add("Is that dalek wearing a pumpkin?");
			list.add("Spoopy");
		}

		if (TimedUnlock.getCalendar().get(Calendar.MONTH) == Calendar.APRIL && TimedUnlock.getDate() == 1) {
			Random random = new Random();

			if (random.nextBoolean()) {
				list.replaceAll(string -> new StringBuilder(string).reverse().toString());
			}

			if (random.nextFloat() < 0.02) {
				list.replaceAll(string -> "§k" + string);
			}
		}
		return list;
	}
}
