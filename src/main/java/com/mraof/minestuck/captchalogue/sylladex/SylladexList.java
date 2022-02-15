package com.mraof.minestuck.captchalogue.sylladex;

import com.mraof.minestuck.util.MinestuckUtils;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.LinkedList;

public class SylladexList<SYLLADEX extends Sylladex> extends LinkedList<SYLLADEX>
{
	public SYLLADEX removeFirstWithSlots()
	{
		SYLLADEX rtn = getFirstWithSlots();
		remove(rtn);
		return rtn;
	}

	public SYLLADEX getFirstWithSlots()
	{
		for (SYLLADEX sylladex : this)
			if (sylladex.getTotalSlots() > 0)
				return sylladex;
		return null;
	}

	public SYLLADEX removeLastWithSlots()
	{
		SYLLADEX rtn = getLastWithSlots();
		remove(rtn);
		return rtn;
	}

	public SYLLADEX getLastWithSlots()
	{
		for (SYLLADEX sylladex : MinestuckUtils.reverse(this))
			if (sylladex.getTotalSlots() > 0)
				return sylladex;
		return null;
	}

	public SYLLADEX getFirstWithObject()
	{
		for (SYLLADEX sylladex : this)
			if (sylladex.getTotalSlots() != sylladex.getFreeSlots())
				return sylladex;
		return null;
	}

	public SYLLADEX getLastWithObject()
	{
		for (SYLLADEX sylladex : MinestuckUtils.reverse(this))
			if (sylladex.getTotalSlots() != sylladex.getFreeSlots())
				return sylladex;
		return null;
	}

	public int sizeWithSlots()
	{
		int size = 0;
		for (SYLLADEX sylladex : this)
			if (sylladex.getTotalSlots() > 0)
				size++;
		return size;
	}

	public int transformIndexToWithSlots(int i)
	{
		if (get(i).getTotalSlots() == 0)
			return -1;

		int newi = i;
		for (int j = 0; j < i; j++)
			if (get(j).getTotalSlots() == 0)
				newi--;
		return newi;
	}

	public EntityPlayerMP getPlayer()
	{
		return (EntityPlayerMP) get(0).getPlayer();
	}
}
