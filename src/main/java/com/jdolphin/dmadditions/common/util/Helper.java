package com.jdolphin.dmadditions.common.util;

import com.jdolphin.dmadditions.DMAdditions;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.main.DalekMod;
import com.swdteam.util.world.Schematic;
import com.swdteam.util.world.SchematicUtils;
import com.swdteam.util.world.TileData;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.FolderName;

import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Map;

public class Helper {
	public static ResourceLocation BUTTONS_LOCATION = Helper.createAdditionsRL("textures/gui/buttons.png");
	public static void renderWidgets(MatrixStack stack, int pMouseX, int pMouseY, float pPartialTick, Widget... widgets) {
		for (Widget widget : widgets) {
			widget.render(stack, pMouseX, pMouseY, pPartialTick);
		}
	}

	public static Schematic getDMASchematic(String path) {
		InputStream stream = SchematicUtils.class.getResourceAsStream("/data/dmadditions/structures/schem/" + path + ".schm");

		try {
			ObjectInputStream ois = new ObjectInputStream(stream);
			Object o = ois.readObject();
			ois.close();
			if (o instanceof Schematic) {
				return (Schematic) o;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return null;
	}

	public static  <K, V> K getKeyByValue(Map<K, V> map, V value) {
		for (Map.Entry<K, V> entry : map.entrySet()) {
			if (entry.getValue().equals(value)) {
				return entry.getKey();
			}
		}
		return null;
	}

	public static void drawWhiteString(MatrixStack stack, String text, int x, int y) {
		AbstractGui.drawString(stack, Minecraft.getInstance().font, text, x, y, Color.WHITE.getRGB());
	}

	public static void drawWhiteCenteredString(MatrixStack stack, ITextComponent text, int x, int y) {
		drawWhiteCenteredString(stack, text.getString(), x, y);
	}

	public static void drawWhiteCenteredString(MatrixStack stack, String text, int x, int y) {
		AbstractGui.drawCenteredString(stack, Minecraft.getInstance().font, text, x, y, Color.WHITE.getRGB());
	}

	public static void renderTooltip(Screen screen, MatrixStack stack, ITextComponent component, Widget widget) {
		if (widget.isHovered()) screen.renderTooltip(stack, component, widget.x, widget.y);
	}

	public static Style getStyle(Screen screen, int x, int y) {
		return screen.getMinecraft().gui.getChat().getClickedComponentStyleAt(x, y);
	}


	public static ResourceLocation createAdditionsRL(String string) {
		return new ResourceLocation(DMAdditions.MODID, string);
	}

	public static ResourceLocation createDMRL(String string) {
		return new ResourceLocation(DalekMod.MODID, string);
	}

	public static boolean isTardis(World world) {
		return world.dimension().equals(DMDimensions.TARDIS);
	}

	public static Vector3d blockPosToVec3(BlockPos pos) {
		return new Vector3d(pos.getX(), pos.getY(), pos.getZ());
	}

	public static BlockPos vec3ToBlockPos(Vector3d vec) {
		return new BlockPos(vec.x, vec.y, vec.z);
	}

	public static void playSound(World world, BlockPos pos, SoundEvent soundEvent, SoundCategory category) {
		playSound(world, pos, soundEvent, category, 1.0f);
	}

	public static void playSound(World world, BlockPos pos, SoundEvent soundEvent, SoundCategory category, float pitch) {
		world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), soundEvent, category, 1.0f, pitch);
	}

	public static int minutes(int min) {
		return seconds(60) * min;
	}

	public static int seconds(int s) {
		return 20 * s;
	}

	public static void addEntities(World world, Entity... entities) {
		for (Entity entity : entities) {
			world.addFreshEntity(entity);
		}
	}

	public static DMAData getDMAData(MinecraftServer server) {
		File file = new File(server.getWorldPath(FolderName.ROOT) + "/dma/data.json");
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			file.createNewFile();
			FileReader reader = new FileReader(file);
			return DMAdditions.GSON.fromJson(reader, DMAData.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DMAData();
	}

	public static void saveDMAData(MinecraftServer server, DMAData data) {
		File file = new File(server.getWorldPath(FolderName.ROOT) + "/dma/data.json");
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(DMAdditions.GSON.toJson(data));
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void generateSchematic(SchematicUtils.GenerationQueue queue, ServerWorld world, BlockPos pPos, Schematic schem, BlockState[] blocksToIgnore, boolean citadel) {
		if (schem != null) {
			int index = 0;
			SchematicUtils.SchematicChunk chunk = new SchematicUtils.SchematicChunk(world);

			for(int y = 0; y <= schem.getSchemDimY(); ++y) {
				for(int x = 0; x <= schem.getSchemDimX(); ++x) {
					for(int z = 0; z <= schem.getSchemDimZ(); ++z) {
						int chunkSize = citadel ? 4096 : 512;
						if (index != 0 && index % chunkSize == 0) {
							queue.getList().add(chunk);
							chunk = new SchematicUtils.SchematicChunk(world);
						}

						BlockPos pos = new BlockPos(x, y, z).offset(pPos.getX(), pPos.getY(), pPos.getZ());
						BlockState block = schem.getStateFromID(schem.blockMap[index]);
						if (blocksToIgnore == null || !Arrays.asList(blocksToIgnore).contains(block)) {
							chunk.getBlocks().add(new SchematicUtils.SchematicChunk.SchematicChunkBlockState(pos, block));
							TileEntity tile = world.getBlockEntity(pos);
							if (tile instanceof StructureBlockTileEntity) {
								StructureBlockTileEntity structureTile = (StructureBlockTileEntity) tile;
								structureTile.loadStructure(world);
							}
						}
							++index;
					}
				}
			}

			queue.getList().add(chunk);
			chunk = new SchematicUtils.SchematicChunk(world);

			for(int i = 0; i < schem.getTileData().size(); ++i) {
				TileData tileData = schem.getTileData().get(i);
				BlockPos pos = new BlockPos(pPos.getX() + tileData.getPos()[0], pPos.getY() + tileData.getPos()[1], pPos.getZ() + tileData.getPos()[2]);
				chunk.getBlocks().add(new SchematicUtils.SchematicChunk.SchematicChunkBlockState(pos, tileData.getNbtDataString()));
			}

			queue.getList().add(chunk);
			SchematicUtils.SchematicChunk cc = new SchematicUtils.SchematicChunk(world);
			queue.getList().add(cc);
		}
	}

}
