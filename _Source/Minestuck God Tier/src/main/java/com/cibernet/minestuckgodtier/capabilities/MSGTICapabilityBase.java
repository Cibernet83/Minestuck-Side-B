package com.cibernet.minestuckgodtier.capabilities;

import net.minecraft.nbt.NBTTagCompound;

public interface MSGTICapabilityBase<OWNER>
{
	NBTTagCompound writeToNBT();
	void readFromNBT(NBTTagCompound nbt);
	default void setOwner(OWNER owner) { }
}
