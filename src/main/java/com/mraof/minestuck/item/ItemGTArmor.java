package com.mraof.minestuck.item;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.model.armor.ModelGTAbstract;
import com.mraof.minestuck.item.armor.MSArmorBase;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumClass;
import com.mraof.minestuck.util.MSGTArmorModels;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.mraof.minestuck.item.MinestuckItems.*;

public class ItemGTArmor extends MSArmorBase
{

	public ItemGTArmor(String name, ArmorMaterial material, EntityEquipmentSlot equipmentSlot)
	{
		super(name, material, equipmentSlot);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		String heroClass = TextFormatting.OBFUSCATED + "Class" + TextFormatting.RESET;
		String heroAspect = TextFormatting.OBFUSCATED + "Thing" + TextFormatting.RESET;

		if(stack.hasTagCompound())
		{
			NBTTagCompound nbt = stack.getTagCompound();
			
			if(nbt.hasKey("class"))
			{
				int c = nbt.getInteger("class");
				if(c >= 0 && c < EnumClass.values().length)
					heroClass = EnumClass.values()[c].getDisplayName();
			}
			if(nbt.hasKey("aspect"))
			{
				int a = nbt.getInteger("aspect");
				if(a >= 0 && a < EnumAspect.values().length)
					heroAspect = EnumAspect.values()[a].getDisplayName();
			}
		}
		return I18n.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(stack) + ".name", I18n.translateToLocalFormatted("title.format", heroClass, heroAspect)).trim();
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if(getHideExtras(stack))
		{
			if((getHeroClass(stack) == (EnumClass.ROGUE) && getType().equals("hood")))
				tooltip.add(I18n.translateToLocal("item.gtHood.hiddenExtras.rogue"));
			if((getHeroClass(stack) == (EnumClass.LORD) && getType().equals("shirt")))
				tooltip.add(I18n.translateToLocal("item.gtShirt.hiddenExtras.lord"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) 
	{
		EnumClass heroClass = getHeroClass(stack);
		
		if(heroClass == null)
			return super.getArmorTexture(stack, entity, slot, type);
		
		return Minestuck.MODID + ":textures/models/armor/gt_" + heroClass.toString() + ".png";
	}
	
	@Nullable
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack, EntityEquipmentSlot armorSlot, ModelBiped _default)
	{
		EnumClass heroClass = getHeroClass(stack);
		EnumAspect heroAspect = getHeroAspect(stack);
		
		if(heroClass == null)
			return super.getArmorModel(entityLiving, stack, armorSlot, _default);
		
		ModelGTAbstract model = MSGTArmorModels.models.get(heroClass);
		
		model.heroAspect = heroAspect;
		
		model.head.showModel = armorSlot == EntityEquipmentSlot.HEAD;
		model.hood.showModel = armorSlot == EntityEquipmentSlot.HEAD;
		model.neck.showModel = armorSlot == EntityEquipmentSlot.HEAD;
		
		model.symbol.showModel = armorSlot == EntityEquipmentSlot.CHEST;
		model.torso.showModel = armorSlot == EntityEquipmentSlot.CHEST;
		model.leftArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
		model.rightArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
		model.cape.showModel = armorSlot == model.getCapeSlot();
		
		model.skirtFront.showModel = armorSlot == model.getSkirtSlot();
		model.skirtMiddle.showModel = model.skirtFront.showModel;
		model.skirtBack.showModel = model.skirtFront.showModel;
		
		model.belt.showModel = armorSlot == EntityEquipmentSlot.LEGS;
		model.leftLeg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
		model.rightLeg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
		
		model.leftFoot.showModel = armorSlot == EntityEquipmentSlot.FEET;
		model.rightFoot.showModel = armorSlot == EntityEquipmentSlot.FEET;
		
		model.isSneak = _default.isSneak;
		model.isRiding = _default.isRiding;
		model.isChild = _default.isChild;
		
		model.rightArmPose = _default.rightArmPose;
		model.leftArmPose = _default.leftArmPose;
		
		model.hideExtras = getHideExtras(stack);
		
		model.addExtraInfo(entityLiving, stack, armorSlot);
		
		return model;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		if(playerIn.isSneaking() && ((getHeroClass(stack) == (EnumClass.ROGUE) && getType().equals("hood")) || (getHeroClass(stack) == (EnumClass.LORD) && getType().equals("shirt"))))
		{
			setHideExtras(stack, !getHideExtras(stack));
			return new ActionResult<>(EnumActionResult.SUCCESS, stack);
		}
		else return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	public String getType()
	{
		return this.equals(gtHood) ? "hood" : this.equals(gtShirt) ? "shirt" : this.equals(gtPants) ? "pants" : "shoes";
	}
	
	public static EnumClass getHeroClass(ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null || !nbt.hasKey("class"))
			return null;
		
		int c = nbt.getInteger("class");
		if(c >= 0 && c < EnumClass.values().length)
			return EnumClass.values()[c];
		return null;
	}
	
	public static boolean getHideExtras(ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null || !nbt.hasKey("hideExtras"))
			return false;
		
		return nbt.getBoolean("hideExtras");
	}
	public static void setHideExtras(ItemStack stack, boolean v)
	{
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbt = stack.getTagCompound();
		
		nbt.setBoolean("hideExtras", v);
	}
	
	public static EnumAspect getHeroAspect(ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null || !nbt.hasKey("aspect"))
			return null;
		
		int a = nbt.getInteger("aspect");
		if(a >= 0 && a < EnumAspect.values().length)
			return EnumAspect.values()[a];
		return null;
	}

	@Override
	public void registerModel()
	{
		String name = getType();
		List<ResourceLocation> locs = new ArrayList<>();
		locs.add(new ResourceLocation(Minestuck.MODID,"god_tier_" + name));
		for(EnumClass i : EnumClass.values())
			locs.add(new ResourceLocation(Minestuck.MODID,i.toString() + "_" + name));

		locs.add(new ResourceLocation(Minestuck.MODID,"rogue_hood_hidden"));
		locs.add(new ResourceLocation(Minestuck.MODID,"lord_shirt_hidden"));

		ModelLoader.registerItemVariants(this, locs.toArray(new ResourceLocation[]{}));
		ModelLoader.setCustomMeshDefinition(this, (ItemStack stack) -> {
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt == null) return new ModelResourceLocation(new ResourceLocation(Minestuck.MODID, "god_tier_" + name), "inventory");
			String clss = EnumClass.values()[nbt.getInteger("class")].getDisplayName().toLowerCase();
			return new ModelResourceLocation(new ResourceLocation(Minestuck.MODID, clss + "_" + name + (ItemGTArmor.getHideExtras(stack) ? "_hidden" : "")), "inventory");
		});
	}
}
