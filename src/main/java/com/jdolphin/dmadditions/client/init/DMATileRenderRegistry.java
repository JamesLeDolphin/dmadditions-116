package com.jdolphin.dmadditions.client.init;

import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.client.render.tileentity.RenderCoordPanel;
import com.jdolphin.dmadditions.client.render.tileentity.RenderDimensionSelectorPanel;
import com.jdolphin.dmadditions.client.render.tileentity.RenderTardisMonitor;
import com.jdolphin.dmadditions.init.DMABlockEntities;
import com.swdteam.client.render.tileentity.RenderTileEntityBase;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelJSONTile;
import com.swdteam.model.javajson.ModelLoader;
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
		registerModel(DMBlockEntities.TILE_DIMENSION_SELECTOR.get(), RenderDimensionSelectorPanel::new);

		if(AdventUnlock.unlockAt(23)){
			registerModel(DMABlockEntities.TILE_SCANNER.get(), RenderTardisMonitor::new);
		}
	}

	public static final ArrayList<TileEntityType<?>> MIXIN_RENDERERS = new ArrayList<TileEntityType<?>>() {{
		add(DMBlockEntities.TILE_COORD_PANEL.get());
		add(DMBlockEntities.TILE_DIMENSION_SELECTOR.get());
	}};

	public static <T extends TileEntity> void registerModel(TileEntityType<T> tileEntityType, Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<? super T>> rendererFactory) {
		ClientRegistry.bindTileEntityRenderer(tileEntityType, rendererFactory);
	}

	public static <T extends TileEntity> void registerModel(TileEntityType<T> tileEntityType, String location) {
		JSONModel model = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/" + location + ".json"));
		if (model != null) {
			registerModel(tileEntityType, (s) -> {
				return new RenderTileEntityBase(s, ModelJSONTile.giveWrapper(location));
			});
		}

	}
}
