package com.jdolphin.dmadditions.world.structure;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.util.Helper;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;

public class CyberUndergroundStructure extends Structure<NoFeatureConfig> {
	private static final List<MobSpawnInfo.Spawners> STRUCTURE_MONSTERS = ImmutableList.of();
	private static final List<MobSpawnInfo.Spawners> STRUCTURE_CREATURES = ImmutableList.of();

	public CyberUndergroundStructure(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	public Structure.IStartFactory<NoFeatureConfig> getStartFactory() {
		return CyberUndergroundStructure.Start::new;
	}

	public GenerationStage.Decoration step() {
		return Decoration.UNDERGROUND_STRUCTURES;
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

		public void generatePieces(DynamicRegistries registries, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {

			int x = (chunkX << 4) + 7;
			int z = (chunkZ << 4) + 7;
			BlockPos blockpos = new BlockPos(x, 0, z);
			JigsawManager.addPieces(registries,
				new VillageConfig(() -> registries.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
					.get(Helper.createAdditionsRL("cyber_underground/start_pool")),
					10), AbstractVillagePiece::new, chunkGenerator, templateManagerIn, blockpos, this.pieces, this.random, false, true);
			this.pieces.forEach((piece) -> piece.move(0, -50, 0));
			this.pieces.forEach((piece) -> --piece.getBoundingBox().y0);
			this.calculateBoundingBox();
		}
	}
}
