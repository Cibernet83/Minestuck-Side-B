package com.mraof.minestuck.captchalogue;

import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.modus.Modus;
import com.mraof.minestuck.captchalogue.sylladex.ISylladex;
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

	public <SYLLADEX extends ISylladex> ICaptchalogueable get(SylladexList<SYLLADEX> sylladices, int[] slots, int index, boolean asCard)
	{
		return modus.get(sylladices, settings, slots, index, asCard);
	}

	public <SYLLADEX extends ISylladex> boolean canGet(SylladexList<SYLLADEX> sylladices, int[] slots, int index)
	{
		return modus.canGet(sylladices, settings, slots, index);
	}

	public <SYLLADEX extends ISylladex> void put(SylladexList<SYLLADEX> sylladexes, ICaptchalogueable object)
	{
		modus.put(sylladexes, settings, object);
	}

	public <SYLLADEX extends ISylladex> void grow(SylladexList<SYLLADEX> sylladexes, ICaptchalogueable object)
	{
		modus.grow(sylladexes, settings, object);
	}

	public <SYLLADEX extends ISylladex> void eject(SylladexList<SYLLADEX> sylladexes)
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
	public <SYLLADEX extends ISylladex> MultiSylladexGuiContainer getGuiContainer(SylladexList<SYLLADEX> sylladices, int[] slots, int index, CardGuiContainer.CardTextureIndex[] firstTextureIndices, CardGuiContainer.CardTextureIndex[] lowerTextureIndices)
	{
		return modus.getGuiContainer(sylladices, settings, slots, index, firstTextureIndices, lowerTextureIndices);
	}

	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getCardTextureIndex()
	{
		return modus.getCardTextureIndex(settings);
	}
}
