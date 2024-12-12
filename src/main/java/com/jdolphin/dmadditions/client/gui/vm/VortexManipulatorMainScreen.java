package com.jdolphin.dmadditions.client.gui.vm;

import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.init.DMAPackets;
import com.jdolphin.dmadditions.item.VortexManipulatorItem;
import com.jdolphin.dmadditions.network.SBVMInteractionPacket;
import com.jdolphin.dmadditions.util.Helper;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class VortexManipulatorMainScreen extends Screen {
	private TextFieldWidget dimInput, xInput, yInput, zInput;
	private ImageButton cancel, select;
	private int fuel = 0, maxFuel = 0;
	public static ResourceLocation BG_LOCATION = Helper.createAdditionsRL("textures/gui/vm_bg.png");

	public VortexManipulatorMainScreen() {
		super(new TranslationTextComponent("screen.dmadditions.vortex_manipulator"));
	}

	public boolean isPauseScreen() {
		return false;
	}

	protected void init() {
		@SuppressWarnings("resource")
		ClientPlayerEntity player = Minecraft.getInstance().player;
		if (player != null) {
			ItemStack stack = player.getMainHandItem();
			if (stack.getItem().equals(DMAItems.VORTEX_MANIPULATOR.get())) {
				maxFuel = VortexManipulatorItem.MAX_FUEL;
				CompoundNBT tag = stack.getOrCreateTag();
				if (tag.contains(VortexManipulatorItem.TAG_FUEL)) {
					fuel = tag.getInt(VortexManipulatorItem.TAG_FUEL);
				}
			}
		}
		this.select = this.addButton(new ImageButton(this.width / 2 - 70, this.height / 2 + 28 , 20, 20, 0, 0, 20,
			Helper.BUTTONS_LOCATION, 256, 256, (button) -> {
			this.setCoords();
			this.onClose();
		},
			new TranslationTextComponent("dmadditions.button.select")));

		this.cancel = this.addButton(new ImageButton( this.width / 2 - 38, this.height / 2 + 28, 20, 20, 0, 0, 20,
			Helper.BUTTONS_LOCATION, 256, 256, (button) -> {
			this.onClose();
		},
			new TranslationTextComponent("dmadditions.button.cancel")));


		this.dimInput = this.addWidget(new TextFieldWidget(this.font,
			this.width / 2 + 17, this.height / 2 - 31, 78, 20,
			 new TranslationTextComponent("chat.editBox")));
		this.xInput = this.addWidget(new TextFieldWidget(this.font,
			this.width / 2 - 84, this.height / 2 - 32, 64, 12,
			new TranslationTextComponent("chat.editBox")));
		this.yInput = this.addWidget(new TextFieldWidget(this.font,
			this.width / 2 - 84, this.height / 2 - 16, 64, 12,
			new TranslationTextComponent("chat.editBox")));
		this.zInput = this.addWidget(new TextFieldWidget(this.font,
			this.width / 2 - 84, this.height / 2, 64, 12,
			new TranslationTextComponent("chat.editBox")));
	}

	public void render(@NotNull MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(stack);
		Minecraft instance = Minecraft.getInstance();
		instance.textureManager.bind(BG_LOCATION);
		blit(stack, this.width / 2 - 128, this.height / 2 - 64, 0, 0, 0, 256, 128,128, 256);

		instance.textureManager.bind(BG_LOCATION);
		dimInput.render(stack, mouseX, mouseY, partialTicks);
		xInput.render(stack, mouseX, mouseY, partialTicks);
		yInput.render(stack, mouseX, mouseY, partialTicks);
		zInput.render(stack, mouseX, mouseY, partialTicks);

		cancel.render(stack, mouseX, mouseY, partialTicks);
		select.render(stack, mouseX, mouseY, partialTicks);
		Helper.drawWhiteString(stack, "X:", this.width / 2 - 94, this.height / 2 - 30);
		Helper.drawWhiteString(stack, "Y:", this.width / 2 - 94, this.height / 2 - 14);
		Helper.drawWhiteString(stack, "Z:", this.width / 2 - 94, this.height / 2 + 2);

		Helper.drawWhiteString(stack, "Dimension:", this.width / 2 + 18, this.height / 2 - 42);

		Helper.drawWhiteString(stack, "Fuel:", this.width / 2 + 58, this.height / 2 + 10);
		Helper.drawWhiteString(stack, String.format("%s/%s", this.fuel, this.maxFuel), this.width / 2 + 54, this.height / 2 + 22);
		Helper.renderTooltip(this, stack, new TranslationTextComponent("dmadditions.button.select"), select);
		Helper.renderTooltip(this, stack, new TranslationTextComponent("dmadditions.button.cancel"), cancel);

		Style style = Helper.getStyle(this, mouseX, mouseY);
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
			SBVMInteractionPacket packet = new SBVMInteractionPacket(new BlockPos(x, y, z), resourceLocation);
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
