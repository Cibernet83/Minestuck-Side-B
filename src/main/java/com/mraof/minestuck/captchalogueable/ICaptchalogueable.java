package com.mraof.minestuck.captchalogueable;

import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import com.mraof.minestuck.sylladex.BottomSylladex;
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
	void eject(BottomSylladex fromSylladex, int cardIndex, EntityPlayer player);
	ItemStack captchalogueIntoCardItem();
	NBTTagCompound writeToNBT();
	void readFromNBT(NBTTagCompound nbt);
	@SideOnly(Side.CLIENT)
	void draw(SylladexGuiHandler gui);
	@SideOnly(Side.CLIENT)
	String getDisplayName();

	/**
	 * Determines what content texture is used (ghost, item, abstract, etc.)
	 */
	@SideOnly(Side.CLIENT)
	String getTextureKey();
}
