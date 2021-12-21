package com.mraof.minestuck.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketSetCurrentItem extends MinestuckPacket
{
	private int currentItem;

	@Override
	public void generatePacket(Object... args)
	{
		data.writeInt((int) args[0]);

	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		currentItem = data.readInt();

	}

	@Override
	public void execute(EntityPlayer player)
	{
		player.inventory.currentItem = currentItem;
	}

	@Override
	public EnumSet<Side> getSenderSide() {
		return EnumSet.of(Side.SERVER);
	}
}
