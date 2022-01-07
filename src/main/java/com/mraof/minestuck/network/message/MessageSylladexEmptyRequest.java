package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.util.SylladexUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSylladexEmptyRequest implements MinestuckMessage
{
	@Override
	public void fromBytes(ByteBuf buf) { }

	@Override
	public void toBytes(ByteBuf buf) { }

	@Override
	public void execute(EntityPlayer player)
	{
		SylladexUtils.getSylladex(player).ejectAll(false, true);
		MinestuckNetwork.sendTo(new MessageSylladexData(player), player);
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
