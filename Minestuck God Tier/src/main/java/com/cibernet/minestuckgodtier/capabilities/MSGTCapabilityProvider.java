package com.cibernet.minestuckgodtier.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MSGTCapabilityProvider<HANDLER extends MSGTICapabilityBase<OWNER>, OWNER> implements ICapabilitySerializable<NBTTagCompound>
{
	private final Capability<HANDLER> capability;
	private final HANDLER instance;

	public MSGTCapabilityProvider(Capability<HANDLER> capability, OWNER owner)
	{
		this.capability = capability;
		this.instance = getCapability().getDefaultInstance();
		this.instance.setOwner(owner);
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
	public NBTTagCompound serializeNBT() {
		return (NBTTagCompound) getCapability().writeNBT(instance, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		getCapability().readNBT(instance, null, nbt);
	}

	public Capability<HANDLER> getCapability()
	{
		return capability;
	}

	public static class Storage<HANDLER extends MSGTICapabilityBase> implements Capability.IStorage<HANDLER>
	{
		@Nullable
		@Override
		public NBTBase writeNBT(Capability<HANDLER> capability, HANDLER instance, EnumFacing side)
		{
			return instance.writeToNBT();
		}

		@Override
		public void readNBT(Capability<HANDLER> capability, HANDLER instance, EnumFacing side, NBTBase nbt) {
			instance.readFromNBT((NBTTagCompound) nbt);
		}
	}
}
