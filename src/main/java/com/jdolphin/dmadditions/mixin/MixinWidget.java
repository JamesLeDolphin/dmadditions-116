
package com.jdolphin.dmadditions.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Widget.class)
public abstract class MixinWidget extends AbstractGui {

    @Shadow protected float alpha;

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/client/gui/widget/Widget;renderButton(Lcom/mojang/blaze3d/matrix/MatrixStack;IIF)V", cancellable = true)
    private void onRenderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, CallbackInfo info) {

        //Cancel the method at it's head, so it basically does nothing
        info.cancel();

        //Casting the Mixin class to it's real class so we can use the object instance
        Widget w = (Widget)((Object)this);

        //Injecting our altered method body to the original method
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = minecraft.font;

        //Removing the button background so just the button label gets rendered:

//        minecraft.getTextureManager().bindTexture(Widget.WIDGETS_LOCATION);
//        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
//        int i = this.getYImage(w.isHovered());
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.enableDepthTest();
//        this.blit(matrixStack, w.x, w.y, 0, 46 + i * 20, w.getWidth() / 2, w.getHeight());
//        this.blit(matrixStack, w.x + w.getWidth() / 2, w.y, 200 - w.getWidth() / 2, 46 + i * 20, w.getWidth() / 2,w.getHeight());
//        this.renderBg(matrixStack, minecraft, mouseX, mouseY);

        int j = w.getFGColor();
        drawCenteredString(matrixStack, fontrenderer, w.getMessage(), w.x + w.getWidth() / 2, w.y + (w.getHeight() - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);

    }

    @Shadow protected abstract int getYImage(boolean isHovered);

    @Shadow protected abstract void renderBg(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY);

}
