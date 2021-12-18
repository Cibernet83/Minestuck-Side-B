package com.mraof.minestuck.network;

import com.mraof.minestuck.util.SylladexUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketSylladexCaptchalogue extends MinestuckPacket
{
	private int slotIndex;

	@Override
	public void generatePacket(Object... args)
	{
		data.writeByte((int)args[0]);
	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		slotIndex = data.readByte();
	}

	@Override
	public void execute(EntityPlayer player)
	{
		SylladexUtils.captchalouge(slotIndex, (EntityPlayerMP)player);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
