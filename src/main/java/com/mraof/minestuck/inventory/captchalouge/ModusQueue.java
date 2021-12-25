package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.client.gui.captchalogue.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;

public class ModusQueue extends Modus
{
	public ModusQueue(String name)
	{
		super(name);
	}

	@Override
	public ICaptchalogueable get(LinkedList<ISylladex> sylladices, int[] slots, int i, boolean asCard)
	{
		ISylladex sylladex = sylladices.removeLast();
		sylladices.addFirst(sylladex);
		slots[i] = 0;
		return sylladex.get(slots, i + 1, asCard);
	}

	@Override
	public boolean canGet(LinkedList<ISylladex> sylladices, int[] slots, int i)
	{
		return slots[i] + 1 == sylladices.size() && sylladices.getLast().canGet(slots, i + 1);
	}

	@Override
	public void put(LinkedList<ISylladex> sylladices, ICaptchalogueable object, EntityPlayer player)
	{
		ISylladex mostFreeSlotsSylladex = getSylladexWithMostFreeSlots(sylladices, player);
		mostFreeSlotsSylladex.put(object, player);
	}

	@Override
	protected ISylladex getSylladexWithMostFreeSlots(LinkedList<ISylladex> sylladices, EntityPlayer player)
	{
		int mostFreeSlots = 0;
		ISylladex mostFreeSlotsSylladex = null;
		for (int i = sylladices.size() - 1; i >= 0; i--)
		{
			int slots = sylladices.get(i).getFreeSlots();
			if (slots > mostFreeSlots)
			{
				mostFreeSlots = slots;
				mostFreeSlotsSylladex = sylladices.get(i);
			}
		}
		if (mostFreeSlots == 0)
		{
			mostFreeSlotsSylladex = sylladices.getLast();
			sylladices.remove(mostFreeSlotsSylladex);
			sylladices.addFirst(mostFreeSlotsSylladex);
			int oldSize = sylladices.size();
			mostFreeSlotsSylladex.eject(player);
			int newSize = sylladices.size();
			sylladices.remove(mostFreeSlotsSylladex);
			sylladices.add(newSize - oldSize, mostFreeSlotsSylladex);
		}

		return mostFreeSlotsSylladex;
	}

	@Override
	public void grow(LinkedList<ISylladex> sylladices, ICaptchalogueable other)
	{
		sylladices.getLast().grow(other);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getCardTextureIndex()
	{
		if (cardTextureIndex == null)
			cardTextureIndex = new CardGuiContainer.CardTextureIndex(SylladexGuiHandler.CARD_TEXTURE, 54);
		return cardTextureIndex;
	}
}