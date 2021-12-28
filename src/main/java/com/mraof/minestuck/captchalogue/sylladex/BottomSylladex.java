package com.mraof.minestuck.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.ModusLayer;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

public class BottomSylladex extends MultiSylladex
{
	private final LinkedList<CardSylladex> cards = new LinkedList<>(); // Keep these as LL instead of array because we have to add/remove cards :/

	BottomSylladex(ModusLayer modi)
	{
		super(modi);
	}

	BottomSylladex(NBTTagCompound nbt)
	{
		super(new ModusLayer(nbt.getCompoundTag("Modus")));

		NBTTagList cardsTag = nbt.getTagList("Cards", 10);
		for (NBTBase cardTagBase : cardsTag)
			cards.add(new CardSylladex(this, (NBTTagCompound) cardTagBase));
	}

	@Override
	public ICaptchalogueable get(int[] slots, int index, boolean asCard)
	{
		ICaptchalogueable rtn = super.get(slots, index, asCard);
		if (rtn != null && asCard)
			cleanUpMarkedCards(slots, index);
		return rtn;
	}

	@Override
	public ICaptchalogueable tryGetEmptyCard(int[] slots, int index)
	{
		ICaptchalogueable rtn = cards.get(slots[index]).tryGetEmptyCard(slots, index + 1);
		cleanUpMarkedCards(slots, index);
		return rtn;
	}

	@Override
	public void put(ICaptchalogueable object, EntityPlayer player)
	{
		if (cards.size() > 0)
			modi.put(getSylladices(), object, player);
		else
			object.eject(this, player);
	}

	@Override
	public void grow(ICaptchalogueable object)
	{
		if (cards.size() > 0)
			super.grow(object);
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
	protected void getModusLayers(List<ModusLayer> modusLayers)
	{
		modusLayers.add(modi);
	}

	@Override
	protected LinkedList<CardSylladex> getSylladices()
	{
		return cards;
	}

	@Override
	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setTag("Modus", modi.writeToNBT());

		NBTTagList cardsTag = new NBTTagList();
		for (CardSylladex card : cards)
			cardsTag.appendTag(card.writeToNBT());
		nbt.setTag("Cards", cardsTag);

		nbt.setBoolean("IsBottom", true);

		return nbt;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getName(boolean plural)
	{
		return modi.getName(plural);
	}
}
