package com.mraof.minestuck.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.modus.Modus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class BottomSylladex extends MultiSylladex
{
	private final LinkedList<CardSylladex> cards = new LinkedList<>(); // Keep these as LL instead of array because we have to add/remove cards :/

	public BottomSylladex(Modus[] modi)
	{
		Collections.addAll(this.modi, modi);
	}

	public BottomSylladex(NBTTagCompound nbt)
	{
		readFromNBT(nbt);
	}

	@Override
	public ICaptchalogueable get(int[] slots, int i, boolean asCard)
	{
		for (Modus modus : modi)
			if (modus.canGet(cards, slots, i))
			{
				ICaptchalogueable rtn = modus.get(cards, slots, i, asCard);
				if (asCard)
					cleanUpMarkedCards(slots,  i);
				return rtn;
			}
		return null;
	}

	@Override
	public ICaptchalogueable tryGetEmptyCard(int[] slots, int i)
	{
		ICaptchalogueable rtn = cards.get(slots[i]).tryGetEmptyCard(slots, i + 1);
		cleanUpMarkedCards(slots, i);
		return rtn;
	}

	@Override
	public void ejectAll(EntityPlayer player, boolean asCards, boolean onlyFull)
	{
		for (int i = 0; i < cards.size(); i++)
		{
			cards.get(i).ejectAll(player, asCards, onlyFull);
			if (asCards)
				cleanUpMarkedCards(i);
		}
	}

	private void cleanUpMarkedCards(int[] slots, int i)
	{
		if (cleanUpMarkedCards(slots[i]))
			slots[i] = -1;
	}

	private boolean cleanUpMarkedCards(int i)
	{
		if (cards.get(i).getTotalSlots() == 0)
		{
			cards.remove(i);
			return true;
		}
		else
			return false;
	}

	@Override
	public void addCard(ICaptchalogueable object, EntityPlayer player)
	{
		cards.add(new CardSylladex(this, object));
	}

	@Override
	protected void getLengths(ArrayList<Integer> lengths) { } // Don't want this one to set lengths

	@Override
	public LinkedList<CardSylladex> getSylladices()
	{
		return cards;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getName(boolean plural)
	{
		return getNameForLayer(plural);
	}

	@Override
	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound nbt = super.writeToNBT();
		nbt.setBoolean("isBottom", true);
		return nbt;
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		modi.clear();
		cards.clear();

		NBTTagList modusTypes = nbt.getTagList("modusTypes", 8);
		for (NBTBase modusType : modusTypes)
			modi.add(Modus.REGISTRY.getValue(new ResourceLocation(((NBTTagString)modusType).getString())));

		NBTTagList sylladices = nbt.getTagList("sylladices", 10);
		for (NBTBase sylladexTagBase : sylladices)
			this.cards.add(new CardSylladex(this, (NBTTagCompound) sylladexTagBase));
	}
}
