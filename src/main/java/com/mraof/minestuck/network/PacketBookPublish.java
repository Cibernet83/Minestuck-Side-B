package com.mraof.minestuck.network;

import com.mraof.minestuck.inventory.captchalouge.BookModus;
import com.mraof.minestuck.util.SylladexUtils;
import com.mraof.minestuck.inventory.captchalouge.Modus;
import com.mraof.minestuck.util.MinestuckPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketBookPublish extends MinestuckPacket
{
	@Override
	public MinestuckPacket generatePacket(Object... var1)
	{
		return this;
	}
	
	@Override
	public MinestuckPacket consumePacket(ByteBuf var1)
	{
		return this;
	}
	
	@Override
	public void execute(EntityPlayer player)
	{
		Modus modus = MinestuckPlayerData.getData(player).modus;
		
		if(modus instanceof BookModus)
		{
			ItemStack book = ((BookModus) modus).createBook();
			
			if(!player.addItemStackToInventory(book))
				SylladexUtils.launchItem(player, book);
			
			((BookModus) modus).clear();
		}
	}
	
	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
