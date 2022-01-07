package com.mraof.minestuck.network.message;

import com.mraof.minestuck.inventory.ContainerEditmode;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageInventoryChangedRequest implements MinestuckMessage
{
	private boolean increase;

	public MessageInventoryChangedRequest() { }

	public MessageInventoryChangedRequest(boolean increase)
	{
		this.increase = increase;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		increase = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(increase);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if (player.openContainer instanceof ContainerEditmode)
			((ContainerEditmode) player.openContainer).updateScroll(increase);
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
