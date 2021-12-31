package com.mraof.minestuck.captchalogue;

import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.modus.Modus;
import com.mraof.minestuck.captchalogue.sylladex.ISylladex;
import com.mraof.minestuck.captchalogue.sylladex.SylladexList;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.MultiSylladexGuiContainer;
import com.mraof.minestuck.util.MinestuckUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class ModusLayer
{
	private final int length;
	private final ModusSettings[] modi;

	public ModusLayer(int length, ModusSettings... modi)
	{
		this.length = length;
		this.modi = modi;
	}

	public ModusLayer(NBTTagCompound nbt)
	{
		this.length = nbt.hasKey("Length") ? nbt.getInteger("Length") : -1;

		NBTTagList modiTag = nbt.getTagList("Modi", 10);
		this.modi = new ModusSettings[modiTag.tagCount()];
		for (int i = 0; i < modi.length; i++)
			modi[i] = new ModusSettings(modiTag.getCompoundTagAt(i));
	}

	public int getLength()
	{
		return length;
	}

	public <SYLLADEX extends ISylladex> ICaptchalogueable get(SylladexList<SYLLADEX> sylladices, int[] slots, int index, boolean asCard)
	{
		for (ModusSettings modus : MinestuckUtils.reverse(modi))
			if (modus.canGet(sylladices, slots, index))
				return modus.get(sylladices, slots, index, asCard);
		return null;
	}

	public <SYLLADEX extends ISylladex> boolean canGet(SylladexList<SYLLADEX> sylladices, int[] slots, int index)
	{
		for (ModusSettings modus : MinestuckUtils.reverse(modi))
			if (modus.canGet(sylladices, slots, index))
				return true;
		return false;
	}

	public <SYLLADEX extends ISylladex> void put(SylladexList<SYLLADEX> sylladexes, ICaptchalogueable object)
	{
		modi[modi.length - 1].put(sylladexes, object);
	}

	public <SYLLADEX extends ISylladex> void grow(SylladexList<SYLLADEX> sylladexes, ICaptchalogueable object)
	{
		modi[modi.length - 1].grow(sylladexes, object);
	}

	public <SYLLADEX extends ISylladex> void eject(SylladexList<SYLLADEX> sylladexes)
	{
		modi[modi.length - 1].eject(sylladexes);
	}

	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();

		if (length >= 0)
			nbt.setInteger("Length", length);

		NBTTagList modiTag = new NBTTagList();
		for (ModusSettings modus : modi)
			modiTag.appendTag(modus.writeToNBT());
		nbt.setTag("Modi", modiTag);

		return nbt;
	}

	public Modus[] getModi()
	{
		Modus[] modi = new Modus[this.modi.length];
		for (int i = 0; i < modi.length; i++)
			modi[i] = this.modi[i].getModus();
		return modi;
	}

	@SideOnly(Side.CLIENT)
	public String getName(boolean plural)
	{
		LinkedHashSet<Modus> distinctModi = new LinkedHashSet<>();
		Collections.addAll(distinctModi, getModi());

		if (distinctModi.size() == 1)
			return distinctModi.iterator().next().getName(true, false, plural);
		else
		{
			StringBuilder nameBuilder = new StringBuilder();
			Iterator<Modus> iterator = distinctModi.iterator();
			for (int i = 0; iterator.hasNext(); i++)
				nameBuilder.append(iterator.next().getName(false, i == 0, plural && i == distinctModi.size() - 1));
			return nameBuilder.toString();
		}
	}

	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex[] getTextureIndices()
	{
		CardGuiContainer.CardTextureIndex[] indices = new CardGuiContainer.CardTextureIndex[modi.length];
		for (int i = 0; i < modi.length; i++)
			indices[i] = modi[i].getCardTextureIndex();
		return indices;
	}

	@SideOnly(Side.CLIENT)
	public <SYLLADEX extends ISylladex> MultiSylladexGuiContainer getGuiContainer(SylladexList<SYLLADEX> sylladices, int[] slots, int index, CardGuiContainer.CardTextureIndex[] textureIndices)
	{
		return modi[0].getGuiContainer(sylladices, slots, index, textureIndices, getTextureIndices());
	}
}
