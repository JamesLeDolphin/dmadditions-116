package com.jdolphin.dmadditions.client.render.tileentity;


import com.jdolphin.dmadditions.DmAdditions;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.client.tardis.data.ClientTardisFlightCache;
import com.swdteam.common.block.tardis.CoordPanelBlock;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.tileentity.tardis.CoordPanelTileEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;

public class RenderCoordPanel extends TileEntityRenderer<CoordPanelTileEntity> {
	public RenderCoordPanel(TileEntityRendererDispatcher dispatcher) {
		super(dispatcher);
	}

	public void render(CoordPanelTileEntity te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
		float blockRot = te.getBlockState().getValue(CoordPanelBlock.FACING).toYRot();
		AttachFace blockFace = te.getBlockState().getValue(BlockStateProperties.ATTACH_FACE);

		matrixStack.pushPose();
		switch (blockFace) {
			case FLOOR:
				matrixStack.translate(0.5D, 0.127D, 0.5D);
				matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
				matrixStack.mulPose(Vector3f.YP.rotationDegrees(blockRot));
				break;
			case CEILING:
				matrixStack.translate(0.5D, 1 - 0.127D, 0.5D);
				matrixStack.mulPose(Vector3f.XP.rotationDegrees(0.0F));
				matrixStack.mulPose(Vector3f.YP.rotationDegrees(blockRot));
				break;
			case WALL:
				matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
				matrixStack.mulPose(Vector3f.YP.rotationDegrees(180F));

				double translateX = 0.5;
				double translateY = 0.5;
				double translateZ = 0.5;

				float rotateZ = blockRot;

				switch (te.getBlockState().getValue(CoordPanelBlock.FACING)) {
					case NORTH:
						translateX = -0.5;
						translateY = 1 - 0.127;
						rotateZ = 180 - blockRot;
						break;
					case SOUTH:
						translateX = -0.5;
						translateY = 0.127;
						rotateZ = 180 - blockRot;
						break;

					case EAST:
						translateX = -0.127;

						break;
					case WEST:
					default:
						translateX = -1 + 0.127;
						break;
				}

				matrixStack.translate(translateX, translateY, translateZ);
				matrixStack.mulPose(Vector3f.ZP.rotationDegrees(rotateZ));

				break;
		}
		FontRenderer font = this.renderer.font;
		matrixStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
		matrixStack.scale(0.005F, 0.005F, 0.005F);
		if (te.getLevel().dimension() == DMDimensions.TARDIS) {
			TardisData data = ClientTardisCache.getTardisData(te.getBlockPos());
			if (data != null && data.getCurrentLocation() != null) {

				String x = "X: " + data.getCurrentLocation().getBlockPosition().getX();
				String y = "Y: " + data.getCurrentLocation().getBlockPosition().getY();
				String z = "Z: " + data.getCurrentLocation().getBlockPosition().getZ();

				if (ClientTardisFlightCache.hasTardisFlightData(data.getGlobalID())) {

					TardisFlightData flight = ClientTardisFlightCache.getTardisFlightData(data.getGlobalID());
					x = "X: " + (int) flight.getPos(Direction.Axis.X);
					y = "Y: " + (int) flight.getPos(Direction.Axis.Y);
					z = "Z: " + (int) flight.getPos(Direction.Axis.Z);
				}

				font.draw(matrixStack, x, (float) (-font.width(x) / 2), -4.0F, -1);
				font.draw(matrixStack, y, (float) (-font.width(y) / 2), 34.0F, -1);
				font.draw(matrixStack, z, (float) (-font.width(z) / 2), 72.0F, -1);
			}
		}

		if (DmAdditions.hasNTM() && net.tardis.mod.helper.WorldHelper.areDimensionTypesSame(te.getLevel(), net.tardis.mod.world.dimensions.TDimensions.DimensionTypes.TARDIS_TYPE)) {
			if (net.tardis.mod.helper.TardisHelper.getConsoleInWorld(te.getLevel()).isPresent()) {
				net.tardis.mod.helper.TardisHelper.getConsoleInWorld(te.getLevel()).ifPresent(tile -> {
					BlockPos dest = tile.getDestinationPosition();
					if (dest != null) {
						if (tile.hasNavCom()) {
							String x = "X: " + dest.getX();
							String y = "Y: " + dest.getY();
							String z = "Z: " + dest.getZ();

							font.draw(matrixStack, x, (float) (-font.width(x) / 2), -4.0F, -1);
							font.draw(matrixStack, y, (float) (-font.width(y) / 2), 34.0F, -1);
							font.draw(matrixStack, z, (float) (-font.width(z) / 2), 72.0F, -1);
						} else {
							String x = "X: UNKNOWN";
							String y = "Y: UNKNOWN";
							String z = "Z: UNKNOWN";

							font.draw(matrixStack, x, (float) (-font.width(x) / 2), -4.0F, -1);
							font.draw(matrixStack, y, (float) (-font.width(y) / 2), 34.0F, -1);
							font.draw(matrixStack, z, (float) (-font.width(z) / 2), 72.0F, -1);
						}
					}
				});
			}
		}
		matrixStack.popPose();
	}
}
