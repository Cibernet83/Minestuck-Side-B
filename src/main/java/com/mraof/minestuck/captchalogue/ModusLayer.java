package com.mraof.minestuck.captchalogue;

import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.modus.Modus;
import com.mraof.minestuck.captchalogue.sylladex.Sylladex;
import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
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

	public ICaptchalogueable get(SylladexList<? extends Sylladex> sylladices, int[] slots, int index, boolean asCard)
	{
		for (ModusSettings modus : MinestuckUtils.reverse(modi))
			if (modus.canGet(sylladices, slots, index))
				return modus.get(sylladices, slots, index, asCard);
		return null;
	}

	public boolean canGet(SylladexList<? extends Sylladex> sylladices, int[] slots, int index)
	{
		for (ModusSettings modus : MinestuckUtils.reverse(modi))
			if (modus.canGet(sylladices, slots, index))
				return true;
		return false;
	}

	public boolean canGet(SylladexList<? extends Sylladex> sylladices, int index)
	{
		for (ModusSettings modus : MinestuckUtils.reverse(modi))
			if (modus.canGet(sylladices, index))
				return true;
		return false;
	}

	public void put(SylladexList<? extends Sylladex> sylladexes, ICaptchalogueable object)
	{
		modi[modi.length - 1].put(sylladexes, object);
	}

	public void grow(SylladexList<? extends Sylladex> sylladexes, ICaptchalogueable object)
	{
		modi[modi.length - 1].grow(sylladexes, object);
	}

	public void eject(SylladexList<? extends Sylladex> sylladexes)
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

	public Modus[] getModi()
	{
		Modus[] modi = new Modus[this.modi.length];
		for (int i = 0; i < modi.length; i++)
			modi[i] = this.modi[i].getModus();
		return modi;
	}

	@SideOnly(Side.CLIENT)
	public MultiSylladexGuiContainer getGuiContainer(MultiSylladex<? extends Sylladex> sylladex, CardGuiContainer.CardTextureIndex[] textureIndices)
	{
		return modi[0].getGuiContainer(sylladex, textureIndices, getTextureIndices());
	}

	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex[] getTextureIndices()
	{
		CardGuiContainer.CardTextureIndex[] indices = new CardGuiContainer.CardTextureIndex[modi.length];
		for (int i = 0; i < modi.length; i++)
			indices[i] = modi[i].getCardTextureIndex();
		return indices;
	}
}
