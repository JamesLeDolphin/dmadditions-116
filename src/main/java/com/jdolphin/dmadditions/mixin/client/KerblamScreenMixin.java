package com.jdolphin.dmadditions.mixin.client;

import com.swdteam.client.gui.GuiKerblam;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiKerblam.class)
public abstract class KerblamScreenMixin extends Screen {

	@Unique private Button mailButton;

	protected KerblamScreenMixin(ITextComponent component) {
		super(component);
	}

	@Inject(at = @At("TAIL"), remap = false, method = "init()V")
	public void init(CallbackInfo ci) {
		this.addButton(this.mailButton = new Button(this.height / 2, this.width / 2, 20, 20, new StringTextComponent("PRESS ME"), button -> {
			System.out.println("I WAS PRESSED YAY");
		}));
	}
}
