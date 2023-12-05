package com.jdolphin.dmadditions.item;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.client.model.armor.ChristmasHatModel;
import com.jdolphin.dmadditions.init.DMAArmorMaterial;
import com.swdteam.common.init.DMTabs;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class ChristmasHatItem extends DyeableArmorItem {
	public ChristmasHatItem(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}
	public ChristmasHatItem(){
		super(DMAArmorMaterial.CHRISTMAS_HAT, EquipmentSlotType.HEAD, new Item.Properties().tab(DMTabs.DM_CLOTHES));
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if(getItemCategory() == group) {
			ItemStack stack = new ItemStack(this);
			setColor(stack, 0xFFFFFF);
			items.add(stack);
		}
	}

	@Nullable
	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
		return (A) new ChristmasHatModel(1f);
	}

	@Nullable
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		int col = getColor(stack);
		return new ResourceLocation(DmAdditions.MODID, "textures/models/armor/christmas_hat.png").toString();
	}

	@Override
	public boolean canEquip(ItemStack stack, EquipmentSlotType armorType, Entity entity) {
		if (armorType.equals(EquipmentSlotType.HEAD))
			return true;
		return super.canEquip(stack, armorType, entity);
	}



}
