package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.SylladexUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSylladexCaptchalogueRequest implements MinestuckMessage
{
	private int slotIndex;

	public MessageSylladexCaptchalogueRequest() { }

	public MessageSylladexCaptchalogueRequest(int slotIndex)
	{
		this.slotIndex = slotIndex;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(slotIndex);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		slotIndex = buf.readByte();
	}

	@Override
	public void execute(EntityPlayer player)
	{
		SylladexUtils.captchalouge(slotIndex, (EntityPlayerMP)player);
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
