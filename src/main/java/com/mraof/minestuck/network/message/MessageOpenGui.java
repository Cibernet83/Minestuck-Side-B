package com.mraof.minestuck.network.message;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.gui.MinestuckGuiHandler;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class MessageOpenGui implements MinestuckMessage // EntityPlayer$openGui isn't working serverside lol
{
	private int type;

	public MessageOpenGui() { }

	public MessageOpenGui(MinestuckGuiHandler.GuiId id)
	{
		type = id.ordinal();
	}

	@Override
	public void toBytes(ByteBuf data)
	{
		data.writeInt(type);
	}

	@Override
	public void fromBytes(ByteBuf data)
	{
		type = data.readInt();
	}

	@Override
	public void execute(EntityPlayer player)
	{
		BlockPos pos = player.getPosition();
		player.openGui(Minestuck.instance, type, player.getEntityWorld(), pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}
