package com.mraof.minestuck.network;

import com.mraof.minestuck.inventory.captchalouge.ChatModus;
import com.mraof.minestuck.util.MinestuckPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketChatModusEject extends MinestuckPacket
{
	String message;
	boolean isOwner;
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(data, (String)args[0]);
		data.writeBoolean((Boolean) args[1]);

	}
	
	@Override
	public void consumePacket(ByteBuf buffer)
	{
		message = ByteBufUtils.readUTF8String(buffer);
		isOwner = buffer.readBoolean();

	}
	
	@Override
	public void execute(EntityPlayer player)
	{
		if(MinestuckPlayerData.getData(player).modus instanceof ChatModus)
			if(isOwner)
				((ChatModus) MinestuckPlayerData.getData(player).modus).handleChatSend(message);
			else ((ChatModus) MinestuckPlayerData.getData(player).modus).handleChatReceived(message);
	}
	
	@Override
	public Side toSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
