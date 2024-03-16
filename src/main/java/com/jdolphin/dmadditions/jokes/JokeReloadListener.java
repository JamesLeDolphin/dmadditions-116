package com.jdolphin.dmadditions.jokes;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jdolphin.dmadditions.item.JokeItem;
import com.swdteam.main.DalekMod;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JokeReloadListener extends JsonReloadListener {
	List<Joke> loadedJokes = new ArrayList<Joke>();

	public JokeReloadListener(Gson gson, String s) {
		super(gson, s);
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
		JokeItem.jokes.clear();
		Iterator<Map.Entry<ResourceLocation, JsonElement>> i = objectIn.entrySet().iterator();

		while(i.hasNext()) {
			Map.Entry<ResourceLocation, JsonElement> entry = (Map.Entry<ResourceLocation, JsonElement>)i.next();
			ResourceLocation resourcelocation = (ResourceLocation)entry.getKey();

			addJokeFromJson(entry.getValue(), resourcelocation);
		}

		JokeItem.jokes = loadedJokes;
	}

	public static Joke getJokeFromJSON(JsonElement json){
		return DalekMod.GSON.fromJson(json, Joke.class);
	}

	public void addJokeFromJson(JsonElement json, ResourceLocation resourceLocation){
		if(json.isJsonArray()){
			json.getAsJsonArray().forEach(j -> addJokeFromJson(j, resourceLocation));
			return;
		}

		Joke joke = getJokeFromJSON(json);
		if(joke.question == null || joke.answer == null){
			LogManager.getLogger().error("Unable to load joke from {} in {}", json.toString(), resourceLocation);
			return;
		}

		loadedJokes.add(joke);
	}
}
