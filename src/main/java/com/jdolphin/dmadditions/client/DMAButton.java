package com.jdolphin.dmadditions.client;

import com.jdolphin.dmadditions.DmAdditions;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.util.Graphics;
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
	public static final ResourceLocation BUTTON_BACK = new ResourceLocation(DmAdditions.MODID, "textures/gui/main/icons/button_back.png");
	public static final ResourceLocation BUTTON_BACK_HOVER = new ResourceLocation(DmAdditions.MODID, "textures/gui/main/icons/button_back.png");
	private static Minecraft minecraft = Minecraft.getInstance();
	public static final Button.ITooltip NO_TOOLTIP = (p_238488_0_, p_238488_1_, p_238488_2_, p_238488_3_) -> {};
	protected final DMAButton.IPressable onPress;
	protected final Button.ITooltip onTooltip;

	public DMAButton(int p_i232255_1_, int p_i232255_2_, int p_i232255_3_, int p_i232255_4_, ITextComponent p_i232255_5_, DMAButton.IPressable p_i232255_6_) {
		this(p_i232255_1_, p_i232255_2_, p_i232255_3_, p_i232255_4_, p_i232255_5_, p_i232255_6_, NO_TOOLTIP);
	}

	public DMAButton(int p_i232256_1_, int p_i232256_2_, int p_i232256_3_, int p_i232256_4_, ITextComponent p_i232256_5_, DMAButton.IPressable p_i232256_6_, Button.ITooltip p_i232256_7_) {
		super(p_i232256_1_, p_i232256_2_, p_i232256_3_, p_i232256_4_, p_i232256_5_);
		this.onPress = p_i232256_6_;
		this.onTooltip = p_i232256_7_;
	}

	public void onPress() {
		this.onPress.onPress(this);
	}

	@Override
	public void renderButton(MatrixStack stack, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
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
		this.renderBg(stack, minecraft, p_230431_2_, p_230431_3_);
		int j = getFGColor();
		drawCenteredString(stack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
	}

	@OnlyIn(Dist.CLIENT)
	public interface IPressable {
		void onPress(DMAButton p_onPress_1_);
	}

}
