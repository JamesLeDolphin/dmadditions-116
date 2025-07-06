package com.jdolphin.dmadditions.common.world.structure;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.common.init.DMAEntities;
import com.jdolphin.dmadditions.common.util.Helper;
import com.mojang.serialization.Codec;
import com.swdteam.common.init.DMEntities;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class MondasRuin extends Structure<NoFeatureConfig> {
	private static final List<MobSpawnInfo.Spawners> STRUCTURE_MONSTERS = ImmutableList.of(
		new MobSpawnInfo.Spawners(DMEntities.CYBERMAN_ENTITY.get(), 1, 1, 3),
		new MobSpawnInfo.Spawners(DMAEntities.MONDASIAN.get(), 2, 2, 4),
		new MobSpawnInfo.Spawners(DMEntities.CYBERMANVILLAGER_ENTITY.get(), 10, 2, 3)
	);
	private static final List<MobSpawnInfo.Spawners> STRUCTURE_CREATURES = ImmutableList.of();

	public MondasRuin(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	public @NotNull IStartFactory<NoFeatureConfig> getStartFactory() {
		return MondasRuin.Start::new;
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
		public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {
			int x = (chunkX << 4) + 7;
			int z = (chunkZ << 4) + 7;
			BlockPos blockpos = new BlockPos(x, 0, z);
			JigsawManager.addPieces(dynamicRegistryManager,
				new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
					.get(Helper.createAdditionsRL("mondas_ruin/start_pool")), 10),
				AbstractVillagePiece::new, chunkGenerator, templateManagerIn, blockpos, this.pieces, this.random, false, true);
			this.pieces.forEach((piece) -> piece.move(0, 0, 0));
			this.pieces.forEach((piece) -> --piece.getBoundingBox().y0);
			this.calculateBoundingBox();
		}
	}
}
