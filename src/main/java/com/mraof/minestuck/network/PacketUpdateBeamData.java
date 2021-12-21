package com.mraof.minestuck.network;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketUpdateBeamData extends MinestuckPacket
{
	int worldId;
	NBTTagCompound nbt;

	@Override
	public void generatePacket(Object... args)
	{
		data.writeInt(((World)args[0]).provider.getDimension());
		ByteBufUtils.writeTag(data, ((World)args[0]).getCapability(MinestuckCapabilities.BEAM_DATA, null).writeToNBT());


	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		worldId = data.readInt();
		nbt = ByteBufUtils.readTag(data);


	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(player.world.provider.getDimension() == worldId)
			player.world.getCapability(MinestuckCapabilities.BEAM_DATA, null).readFromNBT(nbt);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.SERVER);
	}
}
