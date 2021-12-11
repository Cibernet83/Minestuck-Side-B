package com.cibernet.minestuckgodtier.network;

import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;
import java.util.UUID;

public class PacketUpdateAllBadgeEffects extends MSGTPacket
{
	private NBTTagCompound received;

	@Override
	public MSGTPacket generatePacket(Object... args)
	{
		EntityLivingBase entity = (EntityLivingBase) args[0];
		NBTTagCompound nbt = entity.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).writeToNBT();
		nbt.setUniqueId("UUID", entity.getUniqueID());
		ByteBufUtils.writeTag(data, nbt);
		return this;
	}

	@Override
	public MSGTPacket consumePacket(ByteBuf data)
	{
		received = ByteBufUtils.readTag(data);
		return this;
	}

	@Override
	public void execute(EntityPlayer player)
	{
		UUID uuid = received.getUniqueId("UUID");
		for (EntityLivingBase entity : player.world.getEntities(EntityLivingBase.class, (entity) -> entity.getUniqueID().equals(uuid)))
			entity.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).readFromNBT(received);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.SERVER);
	}
}
