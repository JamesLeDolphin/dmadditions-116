package com.jdolphin.dmadditions.client.init;

import com.jdolphin.dmadditions.init.DMAItems;
import com.swdteam.client.init.ItemRenderingRegistry;
import com.swdteam.client.init.ItemRenderingRegistry.ItemRenderInfo;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;

public class DMAItemRenderingInit {

	public static void addRegisties() {
		if (DMAItems.TOP_HAT != null) {
			ItemRenderInfo IRI_TOP_HAT = ItemRenderingRegistry.addItemRenderer(DMAItems.TOP_HAT);
			IRI_TOP_HAT.addTransformType("worn", TransformType.HEAD);
		}

		if (DMAItems.SYCORAX_STAFF != null) {
			ItemRenderInfo IRI_SYCORAX_STAFF = ItemRenderingRegistry.addItemRenderer(DMAItems.SYCORAX_STAFF);
			IRI_SYCORAX_STAFF.addTransformType("sprite", TransformType.GUI);
			IRI_SYCORAX_STAFF.addTransformType("sprite", TransformType.GROUND);
			IRI_SYCORAX_STAFF.addTransformType("sprite", TransformType.FIXED);
		}

		if (DMAItems.SONIC_SHADES != null) {
			ItemRenderInfo IRI_SONIC_SHADES = ItemRenderingRegistry.addItemRenderer(DMAItems.SONIC_SHADES);
			IRI_SONIC_SHADES.addTransformType("worn", TransformType.HEAD);
		}
	}

}
