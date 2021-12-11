package com.cibernet.minestuckgodtier.network;

import com.cibernet.minestuckgodtier.MSGTConfig;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketUpdateConfig extends MSGTPacket
{
	@Override
	public MSGTPacket generatePacket(Object... args)
	{
		MSGTConfig.writeToBuffer(data);
		return this;
	}

	@Override
	public MSGTPacket consumePacket(ByteBuf data)
	{
		MSGTConfig.readFromBuffer(data);
		return this;
	}

	@Override
	public void execute(EntityPlayer player)
	{

	}

	@Override
	public EnumSet<Side> getSenderSide() {
		return EnumSet.of(Side.SERVER);
	}
}
