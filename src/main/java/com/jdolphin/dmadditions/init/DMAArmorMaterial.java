package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.common.init.DMItems;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
public enum DMAArmorMaterial implements IArmorMaterial {
	MATTS_PINK_THONG("matts_pink_thong", 20, new int[4], 0, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F,
		() -> Ingredient.EMPTY),
	CYBERMAN_ARMOUR("cyberman_armor", 28, new int[]{3, 6, 7}, 9, SoundEvents.ARMOR_EQUIP_CHAIN,
		2.0F, 0.3F, () -> Ingredient.of(DMItems.STEEL_INGOT.get())),

	WEDDING_DRESS("wedding_dress", 20, new int[4], 0, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f,
		() -> Ingredient.EMPTY),

	SONIC_SHADES("sonic_shades", 1, new int[4], 0, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f,
		() -> Ingredient.EMPTY);

	private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
	private final String name;
	private final int durabilityMultiplier;
	private final int[] slotProtections;
	private final int enchantmentValue;
	private final SoundEvent sound;
	private final float toughness;
	private final float knockbackResistance;
	private final LazyValue<Ingredient> repairIngredient;

	DMAArmorMaterial(String pName, int pDurabilityMultiplier, int[] pSlotProtections, int pEnchantmentValue, SoundEvent pSound, float pToughness, float pKnockbackResistance, Supplier<Ingredient> pRepairIngredient) {
		this.name = pName;
		this.durabilityMultiplier = pDurabilityMultiplier;
		this.slotProtections = pSlotProtections;
		this.enchantmentValue = pEnchantmentValue;
		this.sound = pSound;
		this.toughness = pToughness;
		this.knockbackResistance = pKnockbackResistance;
		this.repairIngredient = new LazyValue<>(pRepairIngredient);
	}

	public int getDurabilityForSlot(EquipmentSlotType equipmentSlotType) {
		return HEALTH_PER_SLOT[equipmentSlotType.getIndex()] * this.durabilityMultiplier;
	}

	public int getDefenseForSlot(EquipmentSlotType equipmentSlotType) {
		return this.slotProtections[equipmentSlotType.getIndex()];
	}

	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	public SoundEvent getEquipSound() {
		return this.sound;
	}

	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}

	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return Helper.createDMRL(this.name).toString();
	}

	public float getToughness() {
		return this.toughness;
	}

	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
}
