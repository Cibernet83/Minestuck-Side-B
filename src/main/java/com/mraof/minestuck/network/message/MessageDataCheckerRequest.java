package com.mraof.minestuck.network.message;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.skaianet.SessionHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;

public class MessageDataCheckerRequest implements MinestuckMessage
{
	public static int index = 0;

	/**
	 * Used to avoid confusion when the client sends several requests during a short period
	 */
	private int packetIndex;

	public MessageDataCheckerRequest() { }

	@Override
	public void fromBytes(ByteBuf buf)
	{
		packetIndex = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(index = (index + 1) % 100);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP && MinestuckConfig.getDataCheckerPermissionFor((EntityPlayerMP) player))
		{
			NBTTagCompound data = SessionHandler.createDataTag(player.getServer());
			MinestuckNetwork.sendTo(new MessageDataChecker(packetIndex, data), player);
		}
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}

}