package com.jdolphin.dmadditions.item;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.function.Function;

public class CustomModelArmorItem<T extends BipedModel<?>> extends ArmorItem {
	protected final Function<Float, T> modelSupllier;
	public final String TEXTURE;

	public CustomModelArmorItem(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_, Function<Float, T> model, String texture) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);

		modelSupllier = model;
		TEXTURE = texture;
	}

	@Nullable
	@Override
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
		return (A) modelSupllier.apply(1f);
	}

	@Nullable
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return TEXTURE;
	}
}
