package com.mraof.minestuck.captchalogue.modus;

import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.sylladex.ISylladex;
import com.mraof.minestuck.client.gui.captchalogue.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.GuiSylladex;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
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
	public <SYLLADEX extends ISylladex> ICaptchalogueable get(LinkedList<SYLLADEX> sylladices, NBTTagCompound settings, int[] slots, int i, boolean asCard)
	{
		SYLLADEX sylladex = sylladices.removeLast();
		sylladices.addFirst(sylladex);
		slots[i] = 0;
		return sylladex.get(slots, i + 1, asCard);
	}

	@Override
	public <SYLLADEX extends ISylladex> boolean canGet(LinkedList<SYLLADEX> sylladices, NBTTagCompound settings, int[] slots, int i)
	{
		return slots[i] + 1 == sylladices.size() && sylladices.getLast().canGet(slots, i + 1);
	}

	@Override
	public <SYLLADEX extends ISylladex> void put(LinkedList<SYLLADEX> sylladices, NBTTagCompound settings, ICaptchalogueable object, EntityPlayer player)
	{
		getSylladexWithMostFreeSlots(sylladices, settings, player).put(object, player);
	}

	@Override
	protected <SYLLADEX extends ISylladex> SYLLADEX getSylladexWithMostFreeSlots(LinkedList<SYLLADEX> sylladices, NBTTagCompound settings, EntityPlayer player)
	{
		int mostFreeSlots = 0;
		SYLLADEX mostFreeSlotsSylladex = null;
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
	public <SYLLADEX extends ISylladex> void grow(LinkedList<SYLLADEX> sylladices, NBTTagCompound settings, ICaptchalogueable other)
	{
		sylladices.getLast().grow(other);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getCardTextureIndex(NBTTagCompound settings)
	{
		if (cardTextureIndex == null)
			cardTextureIndex = new CardGuiContainer.CardTextureIndex(GuiSylladex.CARD_TEXTURE, 54);
		return cardTextureIndex;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getPrimaryColor()
	{
		return 0xFF6000;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getDarkerColor()
	{
		return 0xCF560C;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getLighterColor()
	{
		return 0xFF8135;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getTextColor()
	{
		return 0x00F5A1;
	}
}