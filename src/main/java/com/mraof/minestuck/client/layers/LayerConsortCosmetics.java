package com.mraof.minestuck.client.layers;

import com.google.common.collect.Maps;
import com.mraof.minestuck.entity.IWearsCosmetics;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import vazkii.botania.client.model.armor.ModelArmor;

import java.util.Map;

public class LayerConsortCosmetics implements LayerRenderer<EntityLivingBase>
{

	private static final ModelBiped _DEFAULT = new ModelBiped();
	private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
	private final RenderLivingBase<? extends EntityLivingBase> renderer;
	private final ModelRenderer head;

	public LayerConsortCosmetics(RenderLivingBase<? extends EntityLivingBase> renderer, ModelRenderer head)
	{
		this.renderer = renderer;
		this.head = head;
	}

	public static void copyValues(ModelRenderer from, ModelRenderer to)
	{
		to.rotateAngleX = from.rotateAngleX;
		to.rotateAngleY = from.rotateAngleY;
		to.rotateAngleZ = from.rotateAngleZ;
		to.rotationPointX = from.rotationPointX;
		to.rotationPointY = from.rotationPointY;
		to.rotationPointZ = from.rotationPointZ;
	}

	@Override
	public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		if (!(entitylivingbaseIn instanceof IWearsCosmetics))
			return;

		ModelBiped base = renderer.getMainModel() instanceof ModelBiped ? (ModelBiped) renderer.getMainModel() : _DEFAULT;
		ItemStack stack = ((IWearsCosmetics) entitylivingbaseIn).getHeadStack().copy();
		boolean hasColor = stack.getItem() instanceof ItemArmor;// && ((ItemArmor) stack.getItem()).hasColor(stack);

		if (!(stack.getItem() instanceof ItemArmor))
			return;

		ModelBiped armorModel = stack.getItem().getArmorModel(entitylivingbaseIn, stack, EntityEquipmentSlot.HEAD, base);
		ResourceLocation texture = getArmorResource(entitylivingbaseIn, stack, EntityEquipmentSlot.HEAD, null);

		if (armorModel == null)
		{
			armorModel = new ModelArmor(EntityEquipmentSlot.HEAD);
			armorModel.setVisible(false);
			armorModel.bipedHead.showModel = true;
			armorModel.bipedHeadwear.showModel = true;
		}

		renderer.bindTexture(texture);

		if (hasColor)
		{
			int i = ((ItemArmor) stack.getItem()).getColor(stack);
			float r = (float) (i >> 16 & 255) / 255.0F;
			float g = (float) (i >> 8 & 255) / 255.0F;
			float b = (float) (i & 255) / 255.0F;
			GlStateManager.color(r, g, b);
		}
		else GlStateManager.color(1, 1, 1);
		head.postRender(scale);

		//armorModel.setModelAttributes(base);
		//armorModel.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
		//armorModel.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, 1.0F, entitylivingbaseIn);

		armorModel.bipedHead.render(scale);

		//armorModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		texture = getArmorResource(entitylivingbaseIn, stack, EntityEquipmentSlot.HEAD, "overlay");
		if (texture != null && (stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).hasOverlay(stack)))
		{
			renderer.bindTexture(texture);
			GlStateManager.color(1, 1, 1);
			armorModel.bipedHead.render(scale);
			//armorModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return false;
	}

	public static ResourceLocation getArmorResource(net.minecraft.entity.Entity entity, ItemStack stack, EntityEquipmentSlot slot, String type)
	{
		String s1 = null;

		if (stack.getItem() instanceof ItemArmor)
		{
			ItemArmor item = (ItemArmor) stack.getItem();
			String texture = item.getArmorMaterial().getName();
			String domain = "minecraft";
			int idx = texture.indexOf(':');
			if (idx != -1)
			{
				domain = texture.substring(0, idx);
				texture = texture.substring(idx + 1);
			}
			s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (1), type == null ? "" : String.format("_%s", type));
		}

		s1 = net.minecraftforge.client.ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);

		if (s1 == null)
			return null;

		ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s1);

		if (resourcelocation == null)
		{
			resourcelocation = new ResourceLocation(s1);
			ARMOR_TEXTURE_RES_MAP.put(s1, resourcelocation);
		}

		return resourcelocation;
	}
}
