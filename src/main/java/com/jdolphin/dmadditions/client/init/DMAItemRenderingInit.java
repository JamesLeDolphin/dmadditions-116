package com.jdolphin.dmadditions.client.init;

import com.jdolphin.dmadditions.init.DMAItems;
import com.swdteam.client.init.ItemRenderingRegistry;
import com.swdteam.client.init.ItemRenderingRegistry.ItemRenderInfo;

import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;

public class DMAItemRenderingInit{
	public static void addRegisties(){
		ItemRenderInfo IRI_TOP_HAT = ItemRenderingRegistry.addItemRenderer(DMAItems.TOP_HAT); 
		IRI_TOP_HAT.addTransformType("worn", TransformType.HEAD);
	}

}
