package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.SylladexUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class MessageSylladexFetch extends MinestuckMessage
{
	private int[] slots;
	private boolean asCard;

	@Override
	public void generatePacket(Object... args)
	{
		int[] slots = (int[]) args[0];
		data.writeByte((byte)slots.length);
		for (int slot : slots)
			data.writeByte(slot);
		data.writeBoolean((boolean)args[1]); // asCard
	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		slots = new int[data.readByte() & 0xff];
		for (int i = 0; i < slots.length; i++)
			slots[i] = data.readByte() & 0xff;
		asCard = data.readBoolean();
	}

	@Override
	public void execute(EntityPlayer player)
	{
		SylladexUtils.fetch((EntityPlayerMP) player, slots, asCard);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
