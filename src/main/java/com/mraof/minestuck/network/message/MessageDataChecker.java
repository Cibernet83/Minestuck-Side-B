package com.mraof.minestuck.network.message;

import com.mraof.minestuck.client.gui.playerStats.GuiDataChecker;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class MessageDataChecker implements MinestuckMessage
{
	private static NBTTagCompound nbtData;
	
	/**
	 * Used to avoid confusion when the client sends several requests during a short period
	 */
	private int packetIndex;

	private MessageDataChecker() { }

	public MessageDataChecker(int packetIndex, NBTTagCompound nbtData)
	{
		this.packetIndex = packetIndex;
		MessageDataChecker.nbtData = nbtData;
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(packetIndex);
		ByteBufUtils.writeTag(buf, nbtData);
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		packetIndex = buf.readByte();
		nbtData = ByteBufUtils.readTag(buf);
	}
	
	@Override
	public void execute(EntityPlayer player)
	{
		if(packetIndex == MessageDataCheckerRequest.index)
			GuiDataChecker.activeComponent = new GuiDataChecker.MainComponent(nbtData);
	}
	
	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}