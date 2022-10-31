package com.jdolphin.dmadditions.item;

import com.swdteam.common.init.DMSonicRegistry;
import com.swdteam.common.item.sonics.SonicScrewdriverItem;
import com.swdteam.common.sonic.SonicCategory;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import com.swdteam.common.item.sonics.SonicScrewdriverItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;

public class LaserScrewdriver extends Item {
	public LaserScrewdriver(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}
	private RegistryObject<SoundEvent> sonicSound;

	public LaserScrewdriver(RegistryObject<SoundEvent> _sonicSound, int xpValue) {
		super((new Item.Properties()).stacksTo(1));
		DMSonicRegistry.registerSonic(new ItemStack(this), xpValue);
		this.sonicSound = _sonicSound;
	}

	public LaserScrewdriver(int xpValue) {
		super((new Item.Properties()).stacksTo(1));
		DMSonicRegistry.registerSonic(new ItemStack(this), xpValue);
	}

	public LaserScrewdriver(ItemGroup itemGroup, int xpValue) {
		super((new Item.Properties()).stacksTo(1).tab(itemGroup));
		DMSonicRegistry.registerSonic(new ItemStack(this), xpValue);
	}

	private SoundEvent getSonicSound() {
		return (SoundEvent)this.sonicSound.get();
	}

	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (!worldIn.isClientSide) {
			this.checkIsSetup(playerIn.getItemInHand(handIn));
			worldIn.playSound((PlayerEntity)null, playerIn.blockPosition(), this.getSonicSound(), SoundCategory.PLAYERS, 0.5F, 1.0F);
		}

		return super.use(worldIn, playerIn, handIn);
	}

	public void setSonicSound(RegistryObject<SoundEvent> sonicSound) {
		this.sonicSound = sonicSound;
	}

	public void checkIsSetup(ItemStack stack) {
		if (!stack.hasTag()) {
			stack.setTag(new CompoundNBT());
		}

		if (!stack.getTag().contains("xp")) {
			stack.getTag().putInt("xp", 0);
		}

		ListNBT list;
		CompoundNBT tag;
		if (!stack.getTag().contains("unlocked")) {
			list = new ListNBT();
			tag = new CompoundNBT();
			tag.putString("skin", stack.getItem().getRegistryName().toString().toLowerCase());
			list.add(tag);
			stack.getTag().put("unlocked", list);
		}

		if (!stack.getTag().contains("perms")) {
			list = new ListNBT();
			tag = new CompoundNBT();
			tag.putString("perm", SonicCategory.REDSTONE.perm);
			list.add(tag);
			stack.getTag().put("perms", list);
		}

	}

	public void appendHoverText(ItemStack stack, World w, List<ITextComponent> list, ITooltipFlag flag) {
		if (!stack.hasTag()) {
			list.add((new StringTextComponent("Edit with Sonic Interface")).withStyle(TextFormatting.GRAY));
		}

		if (stack.hasTag() && stack.getTag().contains("xp")) {
			list.add((new StringTextComponent("XP: " + stack.getTag().getInt("xp"))).withStyle(TextFormatting.GOLD));
		}

		list.add((new StringTextComponent("Abilities")).withStyle(TextFormatting.GOLD).withStyle(TextFormatting.BOLD));

		for(int i = 0; i < SonicCategory.values().length; ++i) {
			SonicCategory cat = SonicCategory.values()[i];
			if (!SonicCategory.canExecute(stack, cat) && cat.xpRequired > 0) {
				list.add(cat.unlockName.withStyle(TextFormatting.RED));
			} else {
				list.add(cat.permName.withStyle(TextFormatting.GREEN));
			}
		}

	}
}
