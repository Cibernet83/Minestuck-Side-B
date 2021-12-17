package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.client.gui.captchalogue.ModusGuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;

public class ModusStack extends Modus
{
	public ModusStack(String name)
	{
		super(name);
	}

	@Override
	public ICaptchalogueable get(LinkedList<ISylladex> sylladices, int[] slots, int i, boolean asCard)
	{
		ISylladex sylladex = sylladices.removeFirst();
		sylladices.addLast(sylladex);
		return sylladex.get(slots, i + 1, asCard);
	}

	@Override
	public boolean canGet(LinkedList<ISylladex> sylladices, int[] slots, int i)
	{
		return slots[i] == 0 && sylladices.getFirst().canGet(slots, i);
	}

	@Override
	public boolean put(LinkedList<ISylladex> sylladices, ICaptchalogueable object, EntityPlayer player)
	{
		int mostFreeSlots = 0;
		ISylladex mostSylladex = null;
		for (ISylladex sylladex : sylladices)
		{
			int slots = sylladex.getFreeSlots();
			if (slots > mostFreeSlots)
			{
				mostFreeSlots = slots;
				mostSylladex = sylladex;
			}
		}
		if (mostFreeSlots == 0)
		{
			eject(sylladices, player);
			mostSylladex = sylladices.getLast();
		}

		sylladices.remove(mostSylladex);
		sylladices.addFirst(mostSylladex);
		mostSylladex.put(object, player);

		return true;
	}

	@Override
	public void grow(LinkedList<ISylladex> sylladices, ICaptchalogueable other)
	{
		sylladices.getFirst().grow(other);
	}

	@Override
	public void eject(LinkedList<ISylladex> sylladices, EntityPlayer player)
	{
		sylladices.getLast().eject(player);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModusGuiContainer getGuiContainer(ISylladex sylladex)
	{
		return new ModusGuiContainer(sylladex);
	}
}