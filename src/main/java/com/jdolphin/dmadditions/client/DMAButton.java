package com.jdolphin.dmadditions.client;

import com.jdolphin.dmadditions.util.Helper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DMAButton extends AbstractButton {
	public static final ResourceLocation BUTTON_BACK = Helper.createAdditionsRL("textures/gui/main/icons/button_back.png");
	public static final ResourceLocation BUTTON_BACK_HOVER = Helper.createAdditionsRL("textures/gui/main/icons/button_back.png");
	private static Minecraft minecraft = Minecraft.getInstance();
	public static final Button.ITooltip NO_TOOLTIP = (button, matrixStack, i, p_238488_3_) -> {};
	protected final DMAButton.IPressable onPress;
	protected final Button.ITooltip onTooltip;

	public DMAButton(int i, int i1, int i2, int i3, ITextComponent iTextComponent, DMAButton.IPressable iPressable) {
		this(i, i1, i2, i3, iTextComponent, iPressable, NO_TOOLTIP);
	}

	public DMAButton(int i, int i1, int i2, int i3, ITextComponent iTextComponent, DMAButton.IPressable iPressable, Button.ITooltip iTooltip) {
		super(i, i1, i2, i3, iTextComponent);
		this.onPress = iPressable;
		this.onTooltip = iTooltip;
	}

	public void onPress() {
		this.onPress.onPress(this);
	}

	@Override
	public void renderButton(MatrixStack stack, int i1, int i2, float v) {
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer fontrenderer = minecraft.font;
		minecraft.getTextureManager().bind(this.isFocused() ? BUTTON_BACK_HOVER : BUTTON_BACK);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
		int i = this.getYImage(this.isHovered());
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		this.blit(stack, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
		this.blit(stack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
		this.renderBg(stack, minecraft, i1, i2);
		int j = getFGColor();
		drawCenteredString(stack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
	}

	@OnlyIn(Dist.CLIENT)
	public interface IPressable {
		void onPress(DMAButton p_onPress_1_);
	}

}
