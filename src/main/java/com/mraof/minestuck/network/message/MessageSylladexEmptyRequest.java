package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.util.SylladexUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class MessageSylladexEmptyRequest extends MinestuckMessage
{
	@Override
	public void generatePacket(Object... args){}

	@Override
	public void consumePacket(ByteBuf data){}

	@Override
	public void execute(EntityPlayer player)
	{
		SylladexUtils.getSylladex(player).ejectAll(false, true);
		MinestuckNetwork.sendTo(MinestuckMessage.makePacket(Type.UPDATE_SYLLADEX, player), player);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
