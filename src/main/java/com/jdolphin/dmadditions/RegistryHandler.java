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
	public static DMABiomes dmaBiomes = new DMABiomes();
	public static DMAEntities dmaEntities = new DMAEntities();
	public static DMABlockEntities dmaTiles = new DMABlockEntities();
	public static DMAWorldCarvers dmaCarvers = new DMAWorldCarvers();
	public static DMAItems dmaItems = new DMAItems();
	public static DMASoundEvents dmaSounds = new DMASoundEvents();
	public static DMABlocks dmaBlocks = new DMABlocks();
	public static DMALootConditionManager dmaLootConditionManager = new DMALootConditionManager();


	public static void init() {
		DMAProjectiles.init();
		DMARegistries.register();

		ItemTags.createOptional(new ResourceLocation(DmAdditions.MODID, "tardis_keys"));
	}

	public static class DMARegistries {
		public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, DmAdditions.MODID);
		public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DmAdditions.MODID);
		public static final DeferredRegister<WorldCarver<?>> WORLD_CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, DmAdditions.MODID);
		public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, DmAdditions.MODID);
		public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, DmAdditions.MODID);

		public static void register() {
			IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

			ENTITY_TYPES.register(modEventBus);
			TILE_ENTITY_TYPES.register(modEventBus);
			ITEMS.register(modEventBus);
			WORLD_CARVERS.register(modEventBus);
			BIOMES.register(modEventBus);
		}
	}
}
