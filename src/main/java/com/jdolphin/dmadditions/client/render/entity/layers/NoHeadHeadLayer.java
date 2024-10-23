package com.jdolphin.dmadditions.client.render.entity.layers;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public abstract class NoHeadHeadLayer<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
	private final float scaleX;
	private final float scaleY;
	private final float scaleZ;

	public NoHeadHeadLayer(IEntityRenderer<T, M> renderer) {
		this(renderer, 1.0F, 1.0F, 1.0F);
	}

	public NoHeadHeadLayer(IEntityRenderer<T, M> renderer, float scaleX, float scaleY, float scaleZ) {
		super(renderer);
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
	}

	public abstract ModelRenderer getHead(T entity, M model);

	public void render(MatrixStack stack, IRenderTypeBuffer buf, int p_225628_3_, T entity,
			float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_,
			float p_225628_10_) {
		ItemStack itemstack = entity.getItemBySlot(EquipmentSlotType.HEAD);
		if (!itemstack.isEmpty()) {
			Item item = itemstack.getItem();
			stack.pushPose();
			stack.scale(this.scaleX, this.scaleY, this.scaleZ);
			boolean flag = entity instanceof VillagerEntity || entity instanceof ZombieVillagerEntity;
			if (entity.isBaby() && !(entity instanceof VillagerEntity)) {
				float f = 2.0F;
				float f1 = 1.4F;
				stack.translate(0.0D, 0.03125D, 0.0D);
				stack.scale(0.7F, 0.7F, 0.7F);
				stack.translate(0.0D, 1.0D, 0.0D);
			}

			this.getHead(entity, this.getParentModel()).translateAndRotate(stack);
			if (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof AbstractSkullBlock) {
				float f3 = 1.1875F;
				stack.scale(1.1875F, -1.1875F, -1.1875F);
				if (flag) {
					stack.translate(0.0D, 0.0625D, 0.0D);
				}

				GameProfile gameprofile = null;
				if (itemstack.hasTag()) {
					CompoundNBT compoundnbt = itemstack.getTag();
					if (compoundnbt.contains("SkullOwner", 10)) {
						gameprofile = NBTUtil.readGameProfile(compoundnbt.getCompound("SkullOwner"));
					} else if (compoundnbt.contains("SkullOwner", 8)) {
						String s = compoundnbt.getString("SkullOwner");
						if (!StringUtils.isBlank(s)) {
							gameprofile = SkullTileEntity.updateGameprofile(new GameProfile((UUID) null, s));
							compoundnbt.put("SkullOwner", NBTUtil.writeGameProfile(new CompoundNBT(), gameprofile));
						}
					}
				}

				stack.translate(-0.5D, 0.0D, -0.5D);
				SkullTileEntityRenderer.renderSkull((Direction) null, 180.0F,
						((AbstractSkullBlock) ((BlockItem) item).getBlock()).getType(), gameprofile, p_225628_5_,
						stack, buf, p_225628_3_);
			} else if (!(item instanceof ArmorItem) || ((ArmorItem) item).getSlot() != EquipmentSlotType.HEAD) {
				float f2 = 0.625F;
				stack.translate(0.0D, -0.25D, 0.0D);
				stack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
				stack.scale(0.625F, -0.625F, -0.625F);
				if (flag) {
					stack.translate(0.0D, 0.1875D, 0.0D);
				}

				Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, itemstack,
						ItemCameraTransforms.TransformType.HEAD, false, stack, buf, p_225628_3_);
			}

			stack.popPose();
		}
	}
}
