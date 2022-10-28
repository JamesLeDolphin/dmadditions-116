package com.jdolphin.dmadditions.client.init;

import com.jdolphin.dmadditions.client.render.tileentity.RenderCoordPanel;
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
	}

	public static final ArrayList<TileEntityType<?>> MIXIN_RENDERERS = new ArrayList<TileEntityType<?>>() {{
		add(DMBlockEntities.TILE_COORD_PANEL.get());
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