package com.mraof.minestuck.network;

import com.mraof.minestuck.inventory.captchalouge.ISylladex;
import com.mraof.minestuck.util.SylladexUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketSylladexData extends MinestuckPacket
{
	private NBTTagCompound nbt;

	@Override
	public void generatePacket(Object... args)
	{
		ByteBufUtils.writeTag(data, (NBTTagCompound)args[0]);
	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		nbt = ByteBufUtils.readTag(data);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if (nbt != null)
		{
			ISylladex.Sylladex sylladex = new ISylladex.Sylladex(nbt);
			SylladexUtils.setSylladex(player, sylladex);
		}
		else
			SylladexUtils.setSylladex(player, null);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.SERVER);
	}
}
