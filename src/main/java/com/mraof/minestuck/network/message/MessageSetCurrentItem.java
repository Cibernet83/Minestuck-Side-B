package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class MessageSetCurrentItem extends MinestuckMessage
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
