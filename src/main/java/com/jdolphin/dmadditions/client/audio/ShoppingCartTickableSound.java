package com.jdolphin.dmadditions.client.audio;

import com.jdolphin.dmadditions.entity.ShoppingCartEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;

public class ShoppingCartTickableSound extends TickableSound{
   private final ShoppingCartEntity shoppingCart;

	public ShoppingCartTickableSound(ShoppingCartEntity entity, SoundEvent event) {
      super(event, SoundCategory.NEUTRAL);
      this.shoppingCart = entity;
      this.looping = true;
      this.delay = 0;
      this.volume = 1F;
	  if(entity != null){
		  this.x = (double)((float)entity.getX());
		  this.y = (double)((float)entity.getY());
		  this.z = (double)((float)entity.getZ());
	  }
	}

	public void tick() {
		if (this.shoppingCart == null || !this.shoppingCart.isAlive() || !this.shoppingCart.isEngineStarted()) {
			this.stop();
		} else {
			this.x = (float) this.shoppingCart.getX();
			this.y = (float) this.shoppingCart.getY();
			this.z = (float) this.shoppingCart.getZ();

			if (shoppingCart.level.isClientSide) {
				Minecraft minecraft = Minecraft.getInstance();
				if (minecraft.player == null) return;

				float distance = shoppingCart.distanceTo(minecraft.player);

				this.volume = MathHelper.abs(1f - (distance / 16f));
			}
		}
	}

}
