package com.mraof.minestuck.item;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

import static com.mraof.minestuck.item.MinestuckItems.boondollars;

public class ItemBoondollars extends MSItemBase
{
	public ItemBoondollars()	//TODO Add custom crafting recipe that merges boondollar stacks
	{
		super("boondollars", true);
		setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		if(!worldIn.isRemote)
		{
			MinestuckPlayerData.addBoondollars(playerIn, getCount(playerIn.getHeldItem(handIn)));
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, ItemStack.EMPTY);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if(isInCreativeTab(tab))
		{
			items.add(new ItemStack(this));
			items.add(setCount(new ItemStack(this), 10));
			items.add(setCount(new ItemStack(this), 100));
			items.add(setCount(new ItemStack(this), 1000));
			items.add(setCount(new ItemStack(this), 10000));
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		int amount = getCount(stack);
		tooltip.add(I18n.translateToLocalFormatted("item.boondollars.amount", amount));
	}
	
	public static int getCount(ItemStack stack)
	{
		if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey("value", 99))
			return 1;
		else return stack.getTagCompound().getInteger("value");
	}
	
	public static ItemStack setCount(ItemStack stack, int value)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null)
		{
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		nbt.setInteger("value", value);
		return stack;
	}

	@Override
	public void registerModel()
	{
		ModelLoader.registerItemVariants(boondollars,
				new ResourceLocation(Minestuck.MODID, "boondollars0"),
				new ResourceLocation(Minestuck.MODID, "boondollars1"),
				new ResourceLocation(Minestuck.MODID, "boondollars2"),
				new ResourceLocation(Minestuck.MODID, "boondollars3"),
				new ResourceLocation(Minestuck.MODID, "boondollars4"),
				new ResourceLocation(Minestuck.MODID, "boondollars5"),
				new ResourceLocation(Minestuck.MODID, "boondollars6"));
		ModelLoader.setCustomMeshDefinition(boondollars, (ItemStack stack) ->
		{
			int size = ItemBoondollars.getCount(stack);
			String str;
			if(size < 5)
				str = "boondollars0";
			else if(size < 15)
				str = "boondollars1";
			else if(size < 50)
				str = "boondollars2";
			else if(size < 100)
				str = "boondollars3";
			else if(size < 250)
				str = "boondollars4";
			else if(size < 1000)
				str = "boondollars5";
			else
				str = "boondollars6";

			return new ModelResourceLocation(new ResourceLocation(Minestuck.MODID, str), "inventory");
		});
	}
}
