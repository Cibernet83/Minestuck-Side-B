package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ICaptchalogueable
{
	void grow(ICaptchalogueable other);
	boolean isEmpty();
	boolean isCompatibleWith(ICaptchalogueable other);
	Object getObject();
	void eject(ISylladex from, EntityPlayer player);
	ItemStack captchalogueIntoCardItem();
	NBTTagCompound writeToNBT();
	void readFromNBT(NBTTagCompound nbt);
	@SideOnly(Side.CLIENT)
	void draw(SylladexGuiHandler gui);

	@SideOnly(Side.CLIENT)
	default String getTextureKey()
	{
		return "item"; //TODO @Jade, use this to determine what content texture to use (ghost, item, abstract, etc.)
	}

	@SideOnly(Side.CLIENT)
	String getDisplayName();
}
