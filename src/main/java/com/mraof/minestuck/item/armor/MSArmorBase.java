package com.mraof.minestuck.item.armor;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.item.IRegistryItem;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;

public class MSArmorBase extends ItemArmor implements IRegistryItem
{
	private final String registryName;
	private final ResourceLocation texture;
	ArrayList<ItemStack> repairMaterials = new ArrayList<>();
	private ModelBiped model;

	public MSArmorBase(String name, ArmorMaterial material, EntityEquipmentSlot equipmentSlot)
	{
		this(name, material, equipmentSlot, material.getDurability(equipmentSlot));
	}

	//@ Ciber on the side b discord if you're coding an addon and this is giving you a pain in the backside
	public MSArmorBase(String name, ArmorMaterial material, EntityEquipmentSlot equipmentSlot, int maxUses)
	{
		this(name, material, equipmentSlot, maxUses, new ResourceLocation(Minestuck.MODID, IRegistryObject.unlocToReg(name)));
	}

	public MSArmorBase(String name, ArmorMaterial material, EntityEquipmentSlot equipmentSlot, int maxUses, ResourceLocation texture)
	{
		super(material, 0, equipmentSlot);
		setCreativeTab(MinestuckTabs.minestuck);
		setMaxDamage(maxUses);
		this.texture = texture;
		setUnlocalizedName(name);
		registryName = IRegistryObject.unlocToReg(name);
		MinestuckItems.items.add(this);
	}

	public void setArmorModel(ModelBiped model)
	{
		this.model = model;
	}

	@Nullable
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
	{
		return texture.getResourceDomain() + ":textures/models/armor/" + texture.getResourcePath() + (type == null || type.isEmpty() ? "" : "_" + type) + ".png";
	}

	@Override
	public ModelBiped getArmorModel(EntityLivingBase entity, ItemStack stack, EntityEquipmentSlot slot,
									ModelBiped _default)
	{
		if (model == null) return super.getArmorModel(entity, stack, slot, _default);

		if (!stack.isEmpty())
		{
			if (stack.getItem() instanceof MSArmorBase)
			{
				ModelBiped model = this.model;

				model.bipedRightLeg.showModel = slot == EntityEquipmentSlot.LEGS || slot == EntityEquipmentSlot.FEET;
				model.bipedLeftLeg.showModel = slot == EntityEquipmentSlot.LEGS || slot == EntityEquipmentSlot.FEET;

				model.bipedBody.showModel = slot == EntityEquipmentSlot.CHEST;
				model.bipedLeftArm.showModel = slot == EntityEquipmentSlot.CHEST;
				model.bipedRightArm.showModel = slot == EntityEquipmentSlot.CHEST;

				model.bipedHead.showModel = slot == EntityEquipmentSlot.HEAD;
				model.bipedHeadwear.showModel = slot == EntityEquipmentSlot.HEAD;


				model.isSneak = _default.isSneak;
				model.isRiding = _default.isRiding;
				model.isChild = _default.isChild;

				model.rightArmPose = _default.rightArmPose;
				model.leftArmPose = _default.leftArmPose;

				return model;
			}
		}

		return null;
	}


	public MSArmorBase setRepairMaterials(ItemStack... stacks)
	{
		for (ItemStack i : stacks)
			repairMaterials.add(i);
		return this;
	}

	public MSArmorBase setRepairMaterial(String oredic)
	{
		if (OreDictionary.doesOreNameExist(oredic))
			setRepairMaterials(OreDictionary.getOres(oredic));
		return this;
	}

	public MSArmorBase setRepairMaterials(Collection<ItemStack> stacks)
	{
		repairMaterials.addAll(stacks);
		return this;
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{

		for (ItemStack mat : repairMaterials)
			if (!mat.isEmpty() && OreDictionary.itemMatches(mat, repair, false)) return true;

		return super.getIsRepairable(toRepair, repair);
	}

	@Override
	public void register(IForgeRegistry<Item> registry)
	{
		setRegistryName(registryName);
		registry.register(this);
	}
}
