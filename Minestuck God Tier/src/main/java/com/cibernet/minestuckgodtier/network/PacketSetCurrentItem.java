package com.cibernet.minestuckgodtier.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketSetCurrentItem extends MSGTPacket
{
	private int currentItem;

	@Override
	public MSGTPacket generatePacket(Object... args)
	{
		data.writeInt((int) args[0]);
		return this;
	}

	@Override
	public MSGTPacket consumePacket(ByteBuf data)
	{
		currentItem = data.readInt();
		return this;
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
