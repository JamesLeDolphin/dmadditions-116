package com.jdolphin.dmadditions.client.init;

import com.jdolphin.dmadditions.client.render.tileentity.RenderCoordPanel;
import com.jdolphin.dmadditions.client.render.tileentity.RenderDimensionSelectorPanel;
import com.jdolphin.dmadditions.client.render.tileentity.SpecimenJarRenderer;
import com.jdolphin.dmadditions.init.DMABlockEntities;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.render.tileentity.RenderTileEntityBase;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.ArrayList;
import java.util.function.Function;


public class DMATileRenderRegistry {

	public static void init() {
		registerModel(DMBlockEntities.TILE_COORD_PANEL.get(), RenderCoordPanel::new);
		registerModel(DMABlockEntities.TILE_SPECIMEN_JAR.get(), SpecimenJarRenderer::new);
		registerModel(DMBlockEntities.TILE_DIMENSION_SELECTOR.get(), RenderDimensionSelectorPanel::new);
		registerModel(DMABlockEntities.TILE_REDDASH_STATUE.get(), Helper.createAdditionsRL("models/tileentity/reddash_statue.json"));
	}

	public static final ArrayList<TileEntityType<?>> MIXIN_RENDERERS = new ArrayList<TileEntityType<?>>() {{
		add(DMBlockEntities.TILE_COORD_PANEL.get());
		add(DMBlockEntities.TILE_DIMENSION_SELECTOR.get());
	}};

	public static <T extends TileEntity> void registerModel(TileEntityType<T> tileEntityType, Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<? super T>> rendererFactory) {
		ClientRegistry.bindTileEntityRenderer(tileEntityType, rendererFactory);
	}

	public static <T extends TileEntity> void registerModel(TileEntityType<T> tileEntityType, ResourceLocation location) {
		JSONModel model = ModelLoader.loadModel(location);
		if (model != null) {
			registerModel(tileEntityType, (s) -> new RenderTileEntityBase(s, giveWrapper(location)));
		}
	}

	public static ModelWrapper giveWrapper(ResourceLocation location) {
		JSONModel model = ModelLoader.loadModel(location);
		return model != null && model.getModelData() != null ? model.getModelData().getModel() : null;
	}
}
