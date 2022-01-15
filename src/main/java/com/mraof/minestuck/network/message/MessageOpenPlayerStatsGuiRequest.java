package com.mraof.minestuck.network.message;

import com.mraof.minestuck.editmode.ServerEditHandler;
import com.mraof.minestuck.inventory.ContainerHandler;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;

public class MessageOpenPlayerStatsGuiRequest implements MinestuckMessage
{
	private int id;

	public MessageOpenPlayerStatsGuiRequest() { }

	public MessageOpenPlayerStatsGuiRequest(int id)
	{
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(id);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP)
		{
			EntityPlayerMP playerMP = (EntityPlayerMP) player;
			playerMP.openContainer = ContainerHandler.getPlayerStatsContainer(playerMP, id, ServerEditHandler.getData(playerMP) != null);
			playerMP.openContainer.windowId = ContainerHandler.windowIdStart + id;
			playerMP.addSelfToInternalCraftingInventory(); // Must be placed after setting the window id!!
		}
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}

}
