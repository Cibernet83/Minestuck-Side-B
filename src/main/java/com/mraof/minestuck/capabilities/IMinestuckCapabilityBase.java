package com.mraof.minestuck.capabilities;

import net.minecraft.nbt.NBTTagCompound;

public interface IMinestuckCapabilityBase<OWNER>
{
	NBTTagCompound writeToNBT();
	void readFromNBT(NBTTagCompound nbt);
	default void setOwner(OWNER owner) {}
}
