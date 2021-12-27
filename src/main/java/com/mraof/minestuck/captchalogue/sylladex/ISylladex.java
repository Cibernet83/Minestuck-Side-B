package com.mraof.minestuck.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.ModusLayer;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.SylladexGuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public interface ISylladex
{
	ICaptchalogueable get(int[] slots, int index, boolean asCard);
	boolean canGet(int[] slots, int index);
	ICaptchalogueable peek(int[] slots, int index);
	ICaptchalogueable tryGetEmptyCard(int[] slots, int index);
	void put(ICaptchalogueable object, EntityPlayer player);
	void grow(ICaptchalogueable object);
	void eject(EntityPlayer player);
	void ejectAll(EntityPlayer player, boolean asCards, boolean onlyFull);
	int getFreeSlots();
	int getTotalSlots();
	NBTTagCompound writeToNBT();
	@SideOnly(Side.CLIENT)
	ArrayList<SylladexGuiContainer> generateSubContainers(ArrayList<CardGuiContainer.CardTextureIndex[]> textureIndices);

	@Nonnull
	static MultiSylladex readFromNBT(@Nonnull NBTTagCompound nbt)
	{
		return nbt.getBoolean("IsBottom") ? new BottomSylladex(nbt) : new UpperSylladex(nbt);
	}

	@Nonnull
	static MultiSylladex newSylladex(@Nonnull ModusLayer... modusLayers)
	{
		return modusLayers[0].getLength() < 0 ? new BottomSylladex(modusLayers[0]) : new UpperSylladex(modusLayers, 0);
	}
}
