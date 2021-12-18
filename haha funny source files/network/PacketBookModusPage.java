package com.mraof.minestuck.network;

import com.mraof.minestuck.inventory.captchalouge.BookModus;
import com.mraof.minestuck.util.MinestuckPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketBookModusPage extends MinestuckPacket
{
	int page;
	
	public void generatePacket(Object... args)
	{
		this.data.writeInt((Integer) args[0]);
		

	}
	
	public void consumePacket(ByteBuf data)
	{
		
		page = data.readInt();
		

	}
	
	@Override
	public void execute(EntityPlayer player)
	{
		if(MinestuckPlayerData.getData(player).modus instanceof BookModus)
			((BookModus) MinestuckPlayerData.getData(player).modus).page = page;
	}
	
	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
