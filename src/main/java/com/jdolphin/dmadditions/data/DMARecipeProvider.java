package com.jdolphin.dmadditions.data;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.init.DMABlocks;
import com.swdteam.common.crafting.RoundelBuilderRecipe;
import com.swdteam.common.init.DMItems;
import com.swdteam.main.DalekMod;
import net.minecraft.data.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class DMARecipeProvider implements IDataProvider {
	protected final DataGenerator generator;
	protected final ExistingFileHelper fileHelper;

	public DMARecipeProvider(DataGenerator p_i48262_1_, ExistingFileHelper helper) {
		this.generator = p_i48262_1_;
		this.fileHelper = helper;
	}

	@Override
	public void run(DirectoryCache cache) throws IOException {
		Path path = this.generator.getOutputFolder();
		Set<ResourceLocation> set = Sets.newHashSet();
		roundelContainerRecipes(consumer -> {
			if (!set.add(consumer.getId())) {
				throw new IllegalStateException("Duplicate recipe " + consumer.getId());
			} else {
				saveRecipe(cache, consumer.serializeRecipe(), path.resolve("data/dmadditions/recipes/roundel_container/" + consumer.getId().getPath() + ".json"));

			}
		});
	}

	@Override
	public String getName() {
		return "DMA Recipe Provider";
	}

	private static void saveRecipe(DirectoryCache p_208311_0_, JsonObject p_208311_1_, Path p_208311_2_) {
		try {
			String s = DalekMod.GSON.toJson((JsonElement)p_208311_1_);
			String s1 = SHA1.hashUnencodedChars(s).toString();
			if (!Objects.equals(p_208311_0_.getHash(p_208311_2_), s1) || !Files.exists(p_208311_2_)) {
				Files.createDirectories(p_208311_2_.getParent());

				try (BufferedWriter bufferedwriter = Files.newBufferedWriter(p_208311_2_)) {
					bufferedwriter.write(s);
				}
			}

			p_208311_0_.putNew(p_208311_2_, s1);
		} catch (IOException ioexception) {
			DmAdditions.LOGGER.error("Couldn't save recipe {}", p_208311_2_, ioexception);
		}

	}

	protected void roundelContainerRecipes(Consumer<IFinishedRecipe> consumer) {
		DMABlocks.ROUNDEL_CONTAINERS.forEach(block -> {
			String roundelName = block.getRegistryName().toString().replace("_roundel_container", "");
			ResourceLocation rl = new ResourceLocation(DmAdditions.MODID, "recipes/rounde_container" + block.getRegistryName() + ".json");
			if (!fileHelper.exists(rl, ResourcePackType.CLIENT_RESOURCES)) {
				ForgeRegistries.BLOCKS.getEntries().stream().forEach(thing -> {
					if (thing.getValue().getRegistryName().getPath().contains(roundelName)) {
					NonNullList<Ingredient> list = NonNullList.of(Ingredient.of(thing.getValue()), Ingredient.of(DMItems.ROUNDEL_MOLD.get()));
					new RoundelBuilderRecipe(rl, list, block.asItem().getDefaultInstance());
					}
                });
			}
		});
	}
}
