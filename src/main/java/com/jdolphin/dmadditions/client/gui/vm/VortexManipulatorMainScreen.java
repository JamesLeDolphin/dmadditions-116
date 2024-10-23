package com.jdolphin.dmadditions.client.gui.vm;

import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.init.DMAPackets;
import com.jdolphin.dmadditions.network.SBSetDestinationPacket;
import com.jdolphin.dmadditions.util.DMALocation;
import com.jdolphin.dmadditions.util.GuiHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class VortexManipulatorMainScreen extends Screen {
	private TextFieldWidget dimInput, xInput, yInput, zInput;

	public VortexManipulatorMainScreen() {
		super(new TranslationTextComponent("screen.dmadditions.vortex_manipulator"));
	}

	public boolean isPauseScreen() {
		return false;
	}

	protected void init() {
		this.addButton(new Button(this.width / 2 - 136, this.height / 2 + 32,128, 20,
			new TranslationTextComponent("dmadditions.button.select"), (button) -> {
			this.setCoords();
			this.onClose();

		}));
		this.addButton(new Button(this.width / 2 + 8, this.height / 2 + 32, 128, 20,
			new TranslationTextComponent("dmadditions.button.cancel"), (button) -> {
			this.onClose();
		}));

		this.dimInput = this.addWidget(new TextFieldWidget(this.font,
			this.width / 2 - 32, this.height / 2 - 32, 112, 12,
			 new TranslationTextComponent("chat.editBox")));
		this.xInput = this.addWidget(new TextFieldWidget(this.font,
			this.width / 2 - 32, this.height / 2 - 16, 64, 12,
			new TranslationTextComponent("chat.editBox")));
		this.yInput = this.addWidget(new TextFieldWidget(this.font,
			this.width / 2 - 32, this.height / 2, 64, 12,
			new TranslationTextComponent("chat.editBox")));
		this.zInput = this.addWidget(new TextFieldWidget(this.font,
			this.width / 2 - 32, this.height / 2 + 16, 64, 12,
			new TranslationTextComponent("chat.editBox")));
	}

	public void render(@NotNull MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		super.render(stack, mouseX, mouseY, partialTicks);

		dimInput.render(stack, mouseX, mouseY, partialTicks);
		xInput.render(stack, mouseX, mouseY, partialTicks);
		yInput.render(stack, mouseX, mouseY, partialTicks);
		zInput.render(stack, mouseX, mouseY, partialTicks);

		Style style = GuiHelper.getStyle(this, mouseX, mouseY);
		if (style != null && style.getHoverEvent() != null) {
			this.renderComponentHoverEffect(stack, style, mouseX, mouseY);
		}

	}

	public void setCoords() {
		try {
			assert minecraft != null && minecraft.player != null;
			ClientPlayerEntity player = minecraft.player;
			if (dimInput.getValue().equals("end")) { dimInput.setValue("the_end"); }
			if (dimInput.getValue().equals("nether")) { dimInput.setValue("the_nether"); }
			ResourceLocation resourceLocation =
				new ResourceLocation(dimInput.getValue().isEmpty() ? player.level.dimension().location().toString() : dimInput.getValue());
			int x = Integer.parseInt(xInput.getValue().isEmpty() ? String.valueOf(((int) player.getX())) : xInput.getValue());
			int y = Integer.parseInt(yInput.getValue().isEmpty() ? String.valueOf(((int) player.getY())) : yInput.getValue());
			int z = Integer.parseInt(zInput.getValue().isEmpty() ? String.valueOf(((int) player.getZ())) : zInput.getValue());

			ItemStack itemStack = player.getMainHandItem();
			if (!itemStack.getItem().equals(DMAItems.VORTEX_MANIPULATOR.get()))
				throw new NullPointerException("Vortex manipulator cannot be null!");
			SBSetDestinationPacket packet = new SBSetDestinationPacket(new BlockPos(x, y, z), resourceLocation);
			DMAPackets.INSTANCE.sendToServer(packet);
			this.onClose();
		} catch (Exception error) {
			dimInput.setSuggestion("§c" + error.getLocalizedMessage());
		}
	}

	@Override
	public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
		switch (pKeyCode) {
			case GLFW.GLFW_KEY_ENTER:
			case GLFW.GLFW_KEY_KP_ENTER:
				if(this.getFocused() instanceof Button)
					return super.keyPressed(pKeyCode, pScanCode, pModifiers);
				this.setCoords();
				break;
		}
		return super.keyPressed(pKeyCode, pScanCode, pModifiers);
	}
}
