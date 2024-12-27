package com.jdolphin.dmadditions.world.structure;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.init.DMADimensions;
import com.mojang.serialization.Codec;
import com.swdteam.util.world.Schematic;
import com.swdteam.util.world.SchematicUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.util.List;

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
			System.out.println(blockpos);
			Schematic schematic = SchematicUtils.loadSchematic("citadel", SchematicUtils.FileLocation.INTERNAL);
			SchematicUtils.generateSchematic(SchematicUtils.GenerationQueue.CITADEL, ServerLifecycleHooks.getCurrentServer().getLevel(DMADimensions.GALLIFREY), blockpos, schematic);

			this.calculateBoundingBox();
		}
	}
}
