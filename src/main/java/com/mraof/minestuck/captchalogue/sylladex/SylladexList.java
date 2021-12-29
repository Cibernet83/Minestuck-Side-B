package com.mraof.minestuck.captchalogue.sylladex;

import com.mraof.minestuck.util.MinestuckUtils;

import java.util.LinkedList;

public class SylladexList<SYLLADEX extends ISylladex> extends LinkedList<SYLLADEX>
{
	public SYLLADEX getFirstWithSlots()
	{
		for (SYLLADEX sylladex : this)
			if (sylladex.getTotalSlots() > 0)
				return sylladex;
		return null;
	}

	public SYLLADEX getLastWithSlots()
	{
		for (SYLLADEX sylladex : MinestuckUtils.reverse(this))
			if (sylladex.getTotalSlots() > 0)
				return sylladex;
		return null;
	}

	public SYLLADEX removeFirstWithSlots()
	{
		SYLLADEX rtn = getFirstWithSlots();
		remove(rtn);
		return rtn;
	}

	public SYLLADEX removeLastWithSlots()
	{
		SYLLADEX rtn = getLastWithSlots();
		remove(rtn);
		return rtn;
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
}
