package com.mraof.minestuck.item.armor;

import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MSArmorBase extends ItemArmor implements IRegistryItem<Item>
{
    private final String registryName;
    private ModelBiped model;
    ArrayList<ItemStack> repairMaterials = new ArrayList<>();

    private final ResourceLocation texture;

    public MSArmorBase(String name, ArmorMaterial material, EntityEquipmentSlot equipmentSlot, int maxUses, ResourceLocation texture)
    {
        super(material, 0, equipmentSlot);
        setMaxDamage(maxUses);
        this.texture = texture;
        setUnlocalizedName(name);
        registryName = IRegistryItem.unlocToReg(name);
        MinestuckItems.items.add(this);
    }

    public MSArmorBase(String name, ArmorMaterial material, EntityEquipmentSlot equipmentSlot, int maxUses)
    {
        this(name, material, equipmentSlot, maxUses, new ResourceLocation(IRegistryItem.unlocToReg(name)));
    }

    public MSArmorBase(String name, ArmorMaterial material, EntityEquipmentSlot equipmentSlot)
    {
        this(name, material, equipmentSlot, material.getDurability(equipmentSlot));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        String key = getUnlocalizedName()+".tooltip";
        if(!I18n.translateToLocal(key).equals(key))
            tooltip.add(I18n.translateToLocal(key));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    public void setArmorModel(ModelBiped model) {this.model = model;}

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
        if(model == null) return super.getArmorModel(entity, stack, slot, _default);

        if(!stack.isEmpty())
        {
            if(stack.getItem() instanceof MSArmorBase)
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
        for(ItemStack i : stacks)
            repairMaterials.add(i);
        return this;
    }

    public MSArmorBase setRepairMaterials(Collection<ItemStack> stacks)
    {
        repairMaterials.addAll(stacks);
        return this;
    }

    public MSArmorBase setRepairMaterial(String oredic)
    {
        if(OreDictionary.doesOreNameExist(oredic))
            setRepairMaterials(OreDictionary.getOres(oredic));
        return this;
    }


    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {

        for(ItemStack mat : repairMaterials)
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