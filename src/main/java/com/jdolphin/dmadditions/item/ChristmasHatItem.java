package com.jdolphin.dmadditions.item;

import net.minecraft.nbt.CompoundNBT;
import org.jetbrains.annotations.Nullable;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.client.model.armor.ChristmasHatModel;
import com.jdolphin.dmadditions.init.DMAArmorMaterial;
import com.swdteam.common.init.DMTabs;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChristmasHatItem extends DyeableArmorItem {

	// Standard colours for use in Christmas crackers
	public static final int[] colors = new int[]{
		0xC04E3D,
		0xF98E36,
		0xFDCD60,
		0x8ECD37,
		0x71A346,
		0x4FBBDE,
		0x575EB6,
		0x9648BF,
		0xC35595,
		0xF497B3
	};

	public ChristmasHatItem(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}

	public ChristmasHatItem(){
		super(DMAArmorMaterial.CHRISTMAS_HAT, EquipmentSlotType.HEAD, new Item.Properties().tab(DMTabs.DM_CLOTHES));
	}

	@Nullable
	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
		return (A) new ChristmasHatModel(1f, ((IDyeableArmorItem) itemStack.getItem()).getColor(itemStack));
	}

	@Nullable
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return new ResourceLocation(DmAdditions.MODID, "textures/models/armor/christmas_hat.png").toString();
	}

	@Override
	public boolean canEquip(ItemStack stack, EquipmentSlotType armorType, Entity entity) {
		if (armorType.equals(EquipmentSlotType.HEAD)) return true;
		return super.canEquip(stack, armorType, entity);
	}

	@Override
	public int getColor(ItemStack p_200886_1_) {
		CompoundNBT compoundnbt = p_200886_1_.getTagElement("display");
		return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 0xFFFFFF;
	}

	public static int itemColor(ItemStack stack, int tint){
		if(tint == 0) {
			CompoundNBT compoundnbt = stack.getTagElement("display");
			return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 0xFFFFFF;
		}
		return 0xFFFFFF;
	}
}
