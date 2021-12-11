package com.cibernet.fetchmodiplus.network;

import com.cibernet.fetchmodiplus.captchalogue.BookModus;
import com.mraof.minestuck.inventory.captchalouge.CaptchaDeckHandler;
import com.mraof.minestuck.inventory.captchalouge.Modus;
import com.mraof.minestuck.util.MinestuckPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class BookPublishPacket extends FMPPacket
{
	@Override
	public FMPPacket generatePacket(Object... var1)
	{
		return this;
	}
	
	@Override
	public FMPPacket consumePacket(ByteBuf var1)
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
				CaptchaDeckHandler.launchAnyItem(player, book);
			
			((BookModus) modus).clear();
		}
	}
	
	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
