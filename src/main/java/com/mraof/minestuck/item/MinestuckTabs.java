package com.mraof.minestuck.item;

import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.MinestuckPlayerData;
import com.mraof.minestuck.util.Title;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
}
