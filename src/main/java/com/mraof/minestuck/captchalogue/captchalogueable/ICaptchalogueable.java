package com.mraof.minestuck.captchalogue.captchalogueable;

import com.mraof.minestuck.captchalogue.sylladex.BottomSylladex;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
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
	void fetch(EntityPlayer player);
	void eject(BottomSylladex fromSylladex, EntityPlayer player);
	void eject(EntityPlayer player);
	ItemStack captchalogueIntoCardItem();
	NBTTagCompound writeToNBT();
	@SideOnly(Side.CLIENT)
	void draw(GuiSylladex gui);
	@SideOnly(Side.CLIENT)
	String getDisplayName();
	@SideOnly(Side.CLIENT)
	void renderTooltip(GuiSylladex gui, int x, int y);

	/**
	 * Determines what content texture is used (ghost, item, abstract, etc.)
	 */
	@SideOnly(Side.CLIENT)
	String getTextureKey();
}
