package com.jdolphin.dmadditions.jokes;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jdolphin.dmadditions.item.JokeItem;
import com.swdteam.common.kerblam.KerblamItem;
import com.swdteam.main.DalekMod;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JokeReloadListener extends JsonReloadListener {
	public JokeReloadListener(Gson p_i51536_1_, String p_i51536_2_) {
		super(p_i51536_1_, p_i51536_2_);
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
		JokeItem.jokes.clear();
		List<Joke> loadedJokes = new ArrayList<Joke>();
		Iterator i = objectIn.entrySet().iterator();

		while(i.hasNext()) {
			Map.Entry<ResourceLocation, JsonElement> entry = (Map.Entry)i.next();
			ResourceLocation resourcelocation = (ResourceLocation)entry.getKey();
			Joke j = (Joke)DalekMod.GSON.fromJson((JsonElement)entry.getValue(), Joke.class);
			loadedJokes.add(j);
		}

		JokeItem.jokes = loadedJokes;
	}
}
