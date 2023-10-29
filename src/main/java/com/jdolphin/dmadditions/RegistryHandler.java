package com.jdolphin.dmadditions;

import com.jdolphin.dmadditions.init.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, DmAdditions.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DmAdditions.MODID);
	public static final DeferredRegister<WorldCarver<?>> WORLD_CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, DmAdditions.MODID);
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, DmAdditions.MODID);
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, DmAdditions.MODID);

	public static void init() {
		DMAProjectiles.init();
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ENTITY_TYPES.register(modEventBus);
		TILE_ENTITY_TYPES.register(modEventBus);
		ITEMS.register(modEventBus);
		WORLD_CARVERS.register(modEventBus);
		BIOMES.register(modEventBus);
		ItemTags.createOptional(new ResourceLocation(DmAdditions.MODID, "tardis_keys"));
	}
}
