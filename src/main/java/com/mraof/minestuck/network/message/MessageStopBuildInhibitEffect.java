package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.MinestuckUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageStopBuildInhibitEffect implements MinestuckMessage
{
	public MessageStopBuildInhibitEffect() { }

	@Override
	public void toBytes(ByteBuf buf) { }

	@Override
	public void fromBytes(ByteBuf buf) { }

	@Override
	public void execute(EntityPlayer player)
	{
		if(!player.isCreative())
			player.capabilities.allowEdit = !MinestuckUtils.getPlayerGameType(player).hasLimitedInteractions();
	}

	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}
