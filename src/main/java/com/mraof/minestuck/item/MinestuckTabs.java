package com.mraof.minestuck.item;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.modus.MinestuckModi;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.MinestuckPlayerData;
import com.mraof.minestuck.util.Title;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class MinestuckTabs
{
	public static final CreativeTabs minestuck = new CreativeTabs("minestuck")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(MinestuckBlocks.genericObject);
		}
	};
	public static final CreativeTabs weapons = new CreativeTabs("minestuckWeapons")
	{
		@Override
		public ItemStack getTabIconItem() {
			return  new ItemStack(MinestuckItems.pogoHammer);
		}
	};
	public static final CreativeTabs godTier = new CreativeTabs("minestuckGodTier")
	{
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem()
		{
			ItemStack stack = new ItemStack(MinestuckItems.gtArmorKit);

			if(Minecraft.getMinecraft().player != null)
			{
				Title title = MinestuckPlayerData.getTitle(IdentifierHandler.encode(Minecraft.getMinecraft().player));

				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setInteger("aspect", title != null ? title.getHeroAspect().ordinal() : -1);
			}

			return  stack;
		}
	};
	public static final CreativeTabs fetchModi = new CreativeTabs("minestuckFetchModi")
	{
		@Override
		public ItemStack getIconItemStack()
		{
			if(Minecraft.getMinecraft().player == null)
				return getTabIconItem();

			NonNullList<ItemStack> modi = OreDictionary.getOres("modus");
			return modi.get((int) ((System.currentTimeMillis()/1000d - Minestuck.startTime) % modi.size()));
		}

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(MinestuckItems.modi.get(MinestuckModi.stack));
		}
	};
}
