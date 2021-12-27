package com.mraof.minestuck.captchalogue;

import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.modus.Modus;
import com.mraof.minestuck.captchalogue.sylladex.ISylladex;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.SylladexGuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.LinkedList;

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

	public <SYLLADEX extends ISylladex> ICaptchalogueable get(LinkedList<SYLLADEX> sylladices, int[] slots, int index, boolean asCard)
	{
		return modus.get(sylladices, settings, slots, index, asCard);
	}

	public <SYLLADEX extends ISylladex> boolean canGet(LinkedList<SYLLADEX> sylladices, int[] slots, int index)
	{
		return modus.canGet(sylladices, settings, slots, index);
	}

	public <SYLLADEX extends ISylladex> void put(LinkedList<SYLLADEX> sylladexes, ICaptchalogueable object, EntityPlayer player)
	{
		modus.put(sylladexes, settings, object, player);
	}

	public <SYLLADEX extends ISylladex> void grow(LinkedList<SYLLADEX> sylladexes, ICaptchalogueable object)
	{
		modus.grow(sylladexes, settings, object);
	}

	public <SYLLADEX extends ISylladex> void eject(LinkedList<SYLLADEX> sylladexes, EntityPlayer player)
	{
		modus.eject(sylladexes, settings, player);
	}

	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("Modus", modus.getRegistryName().toString());
		nbt.setTag("Settings", settings);
		return nbt;
	}

	@SideOnly(Side.CLIENT)
	public SylladexGuiContainer getGuiContainer(ArrayList<CardGuiContainer.CardTextureIndex[]> textureIndices, ISylladex sylladex)
	{
		return modus.getGuiContainer(textureIndices, sylladex, settings);
	}

	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getCardTextureIndex()
	{
		return modus.getCardTextureIndex(settings);
	}
}
