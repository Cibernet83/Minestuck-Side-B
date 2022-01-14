package com.mraof.minestuck.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.ModusLayer;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BottomSylladex extends MultiSylladex<CardSylladex>
{
	private final SylladexList<CardSylladex> cards = new SylladexList<>();

	BottomSylladex(EntityPlayer player, ModusLayer modi)
	{
		super(player, modi);
	}

	BottomSylladex(EntityPlayer player, NBTTagCompound nbt)
	{
		super(player, new ModusLayer(nbt.getCompoundTag("Modus")));

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
	public void put(ICaptchalogueable object)
	{
		modi.put(cards, object);
	}

	@Override
	protected SylladexList<CardSylladex> getSylladices()
	{
		return cards;
	}

	@Override
	public void addCard(ICaptchalogueable object)
	{
		cards.add(new CardSylladex(this, object));
	}

	@Override
	protected void getModusLayers(List<ModusLayer> modusLayers)
	{
		modusLayers.add(modi);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getName(boolean plural)
	{
		return modi.getName(plural);
	}

	@Override
	public BottomSylladex getFirstBottomSylladex()
	{
		return this;
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
	public ICaptchalogueable tryGetEmptyCard(int[] slots, int index)
	{
		ICaptchalogueable rtn = cards.get(slots[index]).tryGetEmptyCard(slots, index + 1);
		cleanUpMarkedCards(slots, index);
		return rtn;
	}

	@Override
	public void ejectAll(boolean asCards, boolean onlyFull)
	{
		for (int i = 0; i < cards.size(); i++)
		{
			cards.get(i).ejectAll(asCards, onlyFull);
			if (asCards)
				cleanUpMarkedCards(i);
		}
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

	public void addCard(int index, ICaptchalogueable object, EntityPlayer player)
	{
		cards.add(index, new CardSylladex(this, object));
	}
}
