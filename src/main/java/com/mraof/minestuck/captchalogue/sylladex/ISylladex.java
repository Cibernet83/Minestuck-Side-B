package com.mraof.minestuck.captchalogue.sylladex;

import com.mraof.minestuck.client.gui.captchalogue.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.ModusGuiContainer;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.modus.Modus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public interface ISylladex
{
	ICaptchalogueable get(int[] slots, int i, boolean asCard);
	boolean canGet(int[] slots, int i);
	ICaptchalogueable peek(int[] slots, int i);
	ICaptchalogueable tryGetEmptyCard(int[] slots, int i);
	void put(ICaptchalogueable object, EntityPlayer player);
	void grow(ICaptchalogueable object);
	void eject(EntityPlayer player);
	void ejectAll(EntityPlayer player, boolean asCards, boolean onlyFull);
	int getFreeSlots();
	int getTotalSlots();
	NBTTagCompound writeToNBT();
	@SideOnly(Side.CLIENT)
	ArrayList<ModusGuiContainer> generateSubContainers(ArrayList<CardGuiContainer.CardTextureIndex[]> textureIndices);

	@Nonnull
	static MultiSylladex readFromNBT(@Nonnull NBTTagCompound nbt)
	{
		return nbt.getBoolean("isBottom") ? new BottomSylladex(nbt) : new UpperSylladex(nbt);
	}

	@Nonnull
	static MultiSylladex newSylladex(@Nonnull int[] lengths, @Nonnull Modus[][] modi)
	{
		return lengths.length == 0 ? new BottomSylladex(modi[0]) : new UpperSylladex(lengths, modi, 0);
	}
}
