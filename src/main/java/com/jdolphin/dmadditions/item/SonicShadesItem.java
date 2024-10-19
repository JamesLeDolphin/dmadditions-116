package com.jdolphin.dmadditions.item;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;

import com.swdteam.common.item.ClothesItem;
import com.swdteam.common.sonic.SonicCategory;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

public class SonicShadesItem extends ClothesItem {

	public SonicShadesItem(EquipmentSlotType equipmentSlotType, Properties properties) {
		super(properties, equipmentSlotType, null);
	}

	@Override
	public boolean canEquip(ItemStack stack, EquipmentSlotType armorType, Entity entity) {
		if (armorType == EquipmentSlotType.HEAD) return true;
		return super.canEquip(stack, armorType, entity);
	}

	public static void checkIsSetup(ItemStack stack) {
		CompoundNBT compound = stack.getOrCreateTag();

		if (!compound.contains("xp")) {
			compound.putInt("xp", 0);
		}

		ListNBT list;
		CompoundNBT tag;
		if (!compound.contains("unlocked")) {
			list = new ListNBT();
			tag = new CompoundNBT();
			tag.putString("skin", stack.getItem().getRegistryName().toString().toLowerCase());
			list.add(tag);
			compound.put("unlocked", list);
		}

		if (!compound.contains("perms")) {
			list = new ListNBT();
			tag = new CompoundNBT();
			tag.putString("perm", SonicCategory.REDSTONE.perm);
			list.add(tag);
			compound.put("perms", list);
		}

		if (!compound.contains("energy")) {
			compound.putInt("energy", 100);
		}
	}

	public static @NotNull RayTraceResult rayTraceBlock(World world, PlayerEntity player, RayTraceContext.@NotNull FluidMode fluidMode) {
		float f = player.xRot;
		float f1 = player.yRot;
		Vector3d vector3d = player.getEyePosition(1.0F);
		float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
		float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
		float f4 = -MathHelper.cos(-f * 0.017453292F);
		float f5 = MathHelper.sin(-f * 0.017453292F);
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		double d0 = player.getAttribute((Attribute) ForgeMod.REACH_DISTANCE.get()).getValue();
		Vector3d vector3d1 = vector3d.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
		return world.clip(new RayTraceContext(vector3d, vector3d1, RayTraceContext.BlockMode.VISUAL, fluidMode, player));
	}

	public void appendHoverText(ItemStack stack, World w, List<ITextComponent> list, ITooltipFlag flag) {
		CompoundNBT tag = stack.getOrCreateTag();
		if (tag.contains("xp")) {
			list.add((new StringTextComponent("XP: " + tag.getInt("xp"))).withStyle(TextFormatting.GOLD));
		}

		list.add((new StringTextComponent("Abilities")).withStyle(TextFormatting.GOLD).withStyle(TextFormatting.BOLD));

		for (int i = 0; i < SonicCategory.values().length; ++i) {
			SonicCategory cat = SonicCategory.values()[i];
			if (!SonicCategory.canExecute(stack, cat) && cat.xpRequired > 0) {
				list.add(cat.unlockName.withStyle(TextFormatting.RED));
			} else {
				list.add(cat.permName.withStyle(TextFormatting.GREEN));
			}
		}

	}

	public static LivingEntity rayTraceEntity(ClientWorld world, ClientPlayerEntity player) {
		double reach = player.getAttribute((Attribute) ForgeMod.REACH_DISTANCE.get()).getValue();
		Vector3d eyePos = player.getEyePosition(1);
		Vector3d forward = eyePos.add(player.getForward().scale(reach));
		LogManager.getLogger().debug("Raytrace Positions:\n\teye: {}\n\tforward: {}", eyePos, forward);

		EntityPredicate target = new EntityPredicate();
		target.selector((e) -> e.getBoundingBox()
				.clip(eyePos, forward)
				.isPresent());

		List<LivingEntity> entities = StreamSupport.stream(world
				.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(reach))
				.spliterator(), false)
				.filter(e -> e instanceof LivingEntity)
				.collect(Collectors.toList());

		return world.getNearestEntity(entities, target, null, eyePos.x, eyePos.y, eyePos.z);
	}

}
