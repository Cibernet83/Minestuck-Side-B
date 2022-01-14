package com.mraof.minestuck.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.ModusLayer;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.SylladexGuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class Sylladex
{
	public static MultiSylladex readFromNBT(EntityPlayer player, NBTTagCompound nbt)
	{
		return nbt.hasKey("Modus") ? nbt.getBoolean("IsBottom") ? new BottomSylladex(player, nbt) : new UpperSylladex(player, nbt) : null;
	}
	public static MultiSylladex newSylladex(EntityPlayer player, ModusLayer... modusLayers)
	{
		return modusLayers[0].getLength() < 0 ? new BottomSylladex(player, modusLayers[0]) : new UpperSylladex(player, modusLayers, 0);
	}

	public abstract ICaptchalogueable get(int[] slots, int index, boolean asCard);
	public abstract boolean canGet(int[] slots, int index);
	public abstract boolean canGet(int index);
	public abstract ICaptchalogueable peek(int[] slots, int index);
	public abstract ICaptchalogueable tryGetEmptyCard(int[] slots, int index);
	public abstract void put(ICaptchalogueable object);
	public abstract void grow(ICaptchalogueable object);
	public abstract void eject();
	public abstract void ejectAll(boolean asCards, boolean onlyFull);
	public abstract boolean tryEjectCard();
	public abstract int getFreeSlots();
	public abstract int getTotalSlots();
	public abstract EntityPlayer getPlayer();
	public abstract NBTTagCompound writeToNBT();
	@SideOnly(Side.CLIENT)
	public abstract SylladexGuiContainer generateSubContainer(CardGuiContainer.CardTextureIndex[] textureIndices);
}
