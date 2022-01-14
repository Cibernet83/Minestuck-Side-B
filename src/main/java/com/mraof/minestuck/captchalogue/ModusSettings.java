package com.mraof.minestuck.captchalogue;

import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.modus.Modus;
import com.mraof.minestuck.captchalogue.sylladex.Sylladex;
import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
import com.mraof.minestuck.captchalogue.sylladex.SylladexList;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.MultiSylladexGuiContainer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModusSettings
{
	private final Modus modus;
	private final NBTTagCompound settings;

	public ModusSettings(Modus modus, NBTTagCompound settings)
	{
		this.modus = modus;
		this.settings = settings;
	}

	public ModusSettings(NBTTagCompound nbt)
	{
		this.modus = Modus.REGISTRY.getValue(new ResourceLocation(nbt.getString("Modus")));
		this.settings = nbt.getCompoundTag("Settings");
	}

	public Modus getModus()
	{
		return modus;
	}

	public ICaptchalogueable get(SylladexList<? extends Sylladex> sylladices, int[] slots, int index, boolean asCard)
	{
		return modus.get(sylladices, settings, slots, index, asCard);
	}

	public boolean canGet(SylladexList<? extends Sylladex> sylladices, int[] slots, int index)
	{
		return modus.canGet(sylladices, settings, slots, index);
	}

	public boolean canGet(SylladexList<? extends Sylladex> sylladices, int index)
	{
		return modus.canGet(sylladices, settings, index);
	}

	public void put(SylladexList<? extends Sylladex> sylladexes, ICaptchalogueable object)
	{
		modus.put(sylladexes, settings, object);
	}

	public void grow(SylladexList<? extends Sylladex> sylladexes, ICaptchalogueable object)
	{
		modus.grow(sylladexes, settings, object);
	}

	public void eject(SylladexList<? extends Sylladex> sylladexes)
	{
		modus.eject(sylladexes, settings);
	}

	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("Modus", modus.getRegistryName().toString());
		nbt.setTag("Settings", settings);
		return nbt;
	}

	@SideOnly(Side.CLIENT)
	public MultiSylladexGuiContainer getGuiContainer(MultiSylladex<? extends Sylladex> sylladex, CardGuiContainer.CardTextureIndex[] firstTextureIndices, CardGuiContainer.CardTextureIndex[] lowerTextureIndices)
	{
		return modus.getGuiContainer(sylladex, firstTextureIndices, lowerTextureIndices);
	}

	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getCardTextureIndex()
	{
		return modus.getCardTextureIndex(settings);
	}
}
