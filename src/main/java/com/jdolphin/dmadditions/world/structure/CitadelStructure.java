package com.jdolphin.dmadditions.world.structure;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.init.DMABiomes;
import com.jdolphin.dmadditions.init.DMADimensions;
import com.jdolphin.dmadditions.util.Helper;
import com.mojang.serialization.Codec;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.util.WorldUtils;
import com.swdteam.util.world.Schematic;
import com.swdteam.util.world.SchematicUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

public class CitadelStructure extends Structure<NoFeatureConfig> {
	private static final List<MobSpawnInfo.Spawners> STRUCTURE_MONSTERS = ImmutableList.of();
	private static final List<MobSpawnInfo.Spawners> STRUCTURE_CREATURES = ImmutableList.of();

	public CitadelStructure(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	public IStartFactory<NoFeatureConfig> getStartFactory() {
		return CitadelStructure.Start::new;
	}

	public Decoration step() {
		return Decoration.SURFACE_STRUCTURES;
	}

	public List<MobSpawnInfo.Spawners> getDefaultSpawnList() {
		return STRUCTURE_MONSTERS;
	}

	public List<MobSpawnInfo.Spawners> getDefaultCreatureSpawnList() {
		return STRUCTURE_CREATURES;
	}

	public static class Start extends StructureStart<NoFeatureConfig> {
		public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}

		@ParametersAreNonnullByDefault
		public void generatePieces(DynamicRegistries registries, ChunkGenerator generator, TemplateManager manager, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {
				int x = (chunkX << 4) + 7;
				int z = (chunkZ << 4) + 7;
				BlockPos blockpos = new BlockPos(x, 0, z);
				ResourceLocation location = Helper.createAdditionsRL("structures/citadel.schm");
				System.out.println("CITADEL HERE" + location);
				Schematic schematic = DMTardisRegistry.getInteriorBuildRegistry().get(location);
				SchematicUtils.generateSchematic(SchematicUtils.GenerationQueue.CITADEL, ServerLifecycleHooks.getCurrentServer().getLevel(DMADimensions.GALLIFREY), blockpos, schematic);

				this.calculateBoundingBox();
		}
	}
}
