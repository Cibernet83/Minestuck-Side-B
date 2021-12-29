package com.mraof.minestuck.capabilities.caps;

import com.mraof.minestuck.capabilities.api.ISylladexData;
import com.mraof.minestuck.captchalogue.sylladex.ISylladex;
import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class SylladexData implements ISylladexData
{
	private EntityPlayer owner;

	private MultiSylladex sylladex;

	@Override
	public void setOwner(EntityPlayer owner)
	{
		this.owner = owner;
	}

	@Override
	public MultiSylladex getSylladex()
	{
		return sylladex;
	}

	public void setSylladex(MultiSylladex sylladex)
	{
		this.sylladex = sylladex;
	}

	@Override
	public NBTTagCompound writeToNBT()
	{
		return sylladex.writeToNBT();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		sylladex = ISylladex.readFromNBT(owner, nbt);
	}
}
