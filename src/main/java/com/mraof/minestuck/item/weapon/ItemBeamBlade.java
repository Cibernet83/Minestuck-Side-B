package com.mraof.minestuck.item.weapon;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.item.properties.PropertyElectric;
import com.mraof.minestuck.item.properties.PropertySweep;
import com.mraof.minestuck.item.properties.WeaponProperty;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

import java.util.ArrayList;
import java.util.List;

import static com.mraof.minestuck.item.weapon.ItemDualClaw.isDrawn;

public class ItemBeamBlade extends MSWeaponBase
{
    public EnumDyeColor color = null;
    public ItemBeamBlade(String name, int maxUses, double damageVsEntity, double weaponSpeed, int enchantability) {
        super(name, maxUses, damageVsEntity, weaponSpeed, enchantability);
        addProperties(new PropertySweep(), new PropertyElectric(10, 2, 0.6f, false));
        setRepairMaterials(new ItemStack(MinestuckItems.battery));
    }

    public ItemBeamBlade setColor(EnumDyeColor color)
    {
        this.color = color;
        return this;
    }

    public EnumDyeColor getColor()
    {
        return color;
    }
    
    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        String color = "";
        if(this.color != null)
            color = "."+this.color.getUnlocalizedName();
        return I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + color + ".name").trim();
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        if(entityIn instanceof  EntityLivingBase && isDrawn(stack) && worldIn.getTotalWorldTime() % 80 == 0)
            stack.damageItem(1, (EntityLivingBase) entityIn);
    }

    @Override
    public void setDamage(ItemStack stack, int damage)
    {
        if(!isDrawn(stack) && damage > getDamage(stack))
            return;
        if(damage >= getMaxDamage(stack)+1)
            changeState(stack, false);
        else super.setDamage(stack, damage);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
    
        if(getDamage(stack) < getMaxDamage(stack))
            changeState(stack, !isDrawn(stack));
        return new ActionResult(EnumActionResult.SUCCESS, stack);


    }

    public double getAttackDamage(ItemStack stack) {
        return isDrawn(stack) ? super.getAttackDamage(stack) : 0;
    }

    public double getAttackSpeed(ItemStack stack) {
        return isDrawn(stack) ? super.getAttackSpeed(stack) : 0;
    }

    public static void changeState(ItemStack stack, boolean drawn) {
        NBTTagCompound tagCompound = ItemDualClaw.checkTagCompound(stack);
        tagCompound.setBoolean("IsDrawn", drawn);
    }

    @Override
    public ItemBeamBlade setTool(MSToolClass cls, int harvestLevel, float harvestSpeed) {
        super.setTool(cls, harvestLevel, harvestSpeed);
        return this;
    }

    @Override
    public boolean isRepairable() {
        return true;
    }

    public static class BladeColorHandler implements IItemColor
    {

        @Override
        public int colorMultiplier(ItemStack stack, int tintIndex)
        {
            if(tintIndex != 1 || ((ItemBeamBlade) stack.getItem()).getColor() == null)
                return -1;

            return ((ItemBeamBlade) stack.getItem()).getColor().getColorValue();
        }
    }

    @Override
    public List<WeaponProperty> getProperties(ItemStack stack) {
        return isDrawn(stack) ? super.getProperties() : new ArrayList<>();
    }

    @Override
    public void registerModel()
    {
        super.registerModel();
        String on = getRegistryName().getResourcePath();
        String off = getRegistryName().getResourcePath() + "_off";
        ModelLoader.registerItemVariants(this,
                new ResourceLocation(Minestuck.MODID, on),
                new ResourceLocation(Minestuck.MODID, off));
        ModelLoader.setCustomMeshDefinition(this, (ItemStack stack) -> new ModelResourceLocation(new ResourceLocation(Minestuck.MODID, isDrawn(stack) ? on : off), "inventory"));
    }
}
