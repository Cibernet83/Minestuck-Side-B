package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;

import java.util.EnumSet;

import com.mraof.minestuck.editmode.ServerEditHandler;
import com.mraof.minestuck.inventory.ContainerHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;

public class MessageMiscContainer extends MinestuckMessage
{
	
	int i;
	
	@Override
	public void generatePacket(Object... data)
	{
		this.data.writeInt((Integer) data[0]);

	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		i = data.readInt();

	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(player instanceof EntityPlayerMP)
		{
			EntityPlayerMP playerMP = (EntityPlayerMP) player;
			playerMP.openContainer = ContainerHandler.getPlayerStatsContainer(playerMP, i, ServerEditHandler.getData(playerMP) != null);
			playerMP.openContainer.windowId = ContainerHandler.windowIdStart + i;
			playerMP.addSelfToInternalCraftingInventory();	//Must be placed after setting the window id!!
		}
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}

}
