package com.mraof.minestuck.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MinestuckCapabilityProvider<HANDLER extends IMinestuckCapabilityBase<OWNER>, OWNER> implements ICapabilitySerializable<NBTTagCompound>
{
	private final Capability<HANDLER> capability;
	private final HANDLER instance;

	public MinestuckCapabilityProvider(Capability<HANDLER> capability, OWNER owner)
	{
		this.capability = capability;
		this.instance = getCapability().getDefaultInstance();
		this.instance.setOwner(owner);
	}

	public Capability<HANDLER> getCapability()
	{
		return capability;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == getCapability();
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
	{
		return capability == getCapability() ? getCapability().cast(instance) : null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		try
		{
			return (NBTTagCompound) getCapability().writeNBT(instance, null);
		}
		catch (Throwable e)
		{
			RuntimeException e2 = new RuntimeException("Error saving capability " + instance, e);
			e2.printStackTrace();
			throw e2;
		}
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		getCapability().readNBT(instance, null, nbt);
	}

	public static class Storage<H extends IMinestuckCapabilityBase> implements Capability.IStorage<H>
	{
		@Override
		public NBTBase writeNBT(Capability<H> capability, H instance, EnumFacing side)
		{
			return instance.writeToNBT();
		}

		@Override
		public void readNBT(Capability<H> capability, H instance, EnumFacing side, NBTBase nbt)
		{
			instance.readFromNBT((NBTTagCompound) nbt);
		}
	}
}
