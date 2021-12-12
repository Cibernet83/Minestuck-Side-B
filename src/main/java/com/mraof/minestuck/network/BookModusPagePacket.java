package com.mraof.minestuck.network;

import com.mraof.minestuck.inventory.captchalouge.BookModus;
import com.mraof.minestuck.util.MinestuckPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class BookModusPagePacket extends MinestuckPacket
{
	int page;
	
	public MinestuckPacket generatePacket(Object... args)
	{
		this.data.writeInt((Integer) args[0]);
		
		return this;
	}
	
	public MinestuckPacket consumePacket(ByteBuf data)
	{
		
		page = data.readInt();
		
		return this;
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
