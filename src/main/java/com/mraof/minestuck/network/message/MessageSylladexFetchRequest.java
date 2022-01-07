package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.SylladexUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSylladexFetchRequest implements MinestuckMessage
{
	private int[] slots;
	private boolean asCard;

	private MessageSylladexFetchRequest() { }

	public MessageSylladexFetchRequest(int[] slots, boolean asCard)
	{
		this.slots = slots;
		this.asCard = asCard;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte((byte)slots.length);
		for (int slot : slots)
			buf.writeByte(slot);
		buf.writeBoolean(asCard);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		slots = new int[buf.readByte() & 0xff];
		for (int i = 0; i < slots.length; i++)
			slots[i] = buf.readByte() & 0xff;
		asCard = buf.readBoolean();
	}

	@Override
	public void execute(EntityPlayer player)
	{
		SylladexUtils.fetch((EntityPlayerMP) player, slots, asCard);
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
