package com.mraof.minestuck.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.ModusLayer;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.SylladexGuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ISylladex
{
	ICaptchalogueable get(int[] slots, int index, boolean asCard);
	boolean canGet(int[] slots, int index);
	ICaptchalogueable peek(int[] slots, int index);
	ICaptchalogueable tryGetEmptyCard(int[] slots, int index);
	void put(ICaptchalogueable object);
	void grow(ICaptchalogueable object);
	void eject();
	void ejectAll(boolean asCards, boolean onlyFull);
	boolean tryEjectCard();
	int getFreeSlots();
	int getTotalSlots();
	NBTTagCompound writeToNBT();
	@SideOnly(Side.CLIENT)
	SylladexGuiContainer generateSubContainer(int[] slots, int index, CardGuiContainer.CardTextureIndex[] textureIndices);

	static MultiSylladex readFromNBT(EntityPlayer player, NBTTagCompound nbt)
	{
		return nbt.hasKey("Modus") ? nbt.getBoolean("IsBottom") ? new BottomSylladex(player, nbt) : new UpperSylladex(player, nbt) : null;
	}

	static MultiSylladex newSylladex(EntityPlayer player, ModusLayer... modusLayers)
	{
		return modusLayers[0].getLength() < 0 ? new BottomSylladex(player, modusLayers[0]) : new UpperSylladex(player, modusLayers, 0);
	}
}
