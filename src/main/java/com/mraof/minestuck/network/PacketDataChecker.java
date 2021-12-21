package com.mraof.minestuck.network;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.client.gui.playerStats.GuiDataChecker;
import com.mraof.minestuck.network.skaianet.SessionHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketDataChecker extends MinestuckPacket
{
	
	public static int index = 0;
	public static NBTTagCompound nbtData;
	
	/**
	 * Used to avoid confusion when the client sends several requests during a short period
	 */
	public int packetIndex;
	
	@Override
	public void generatePacket(Object... dat)
	{
		if(dat.length == 0)	//Cient request to server
			data.writeByte(index = (index + 1) % 100);
		else
		{
			data.writeByte((Integer) dat[0]);
			ByteBufUtils.writeTag(data, (NBTTagCompound)dat[1]);
		}
	}
	
	@Override
	public void consumePacket(ByteBuf data)
	{
		packetIndex = data.readByte();
		nbtData = ByteBufUtils.readTag(data);
	}
	
	@Override
	public void execute(EntityPlayer player)
	{
		if(player.world.isRemote)
		{
			if(packetIndex == index)
				GuiDataChecker.activeComponent = new GuiDataChecker.MainComponent(nbtData);
		} else if(player instanceof EntityPlayerMP && MinestuckConfig.getDataCheckerPermissionFor((EntityPlayerMP) player))
		{
			NBTTagCompound data = SessionHandler.createDataTag(player.getServer());
			MinestuckChannelHandler.sendToPlayer(MinestuckPacket.makePacket(Type.DATA_CHECKER, packetIndex, data), player);
		}
	}
	
	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.allOf(Side.class);
	}
	
}