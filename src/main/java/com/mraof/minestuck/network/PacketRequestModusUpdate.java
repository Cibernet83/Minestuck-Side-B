package com.mraof.minestuck.network;

import com.mraof.minestuck.inventory.captchalouge.CaptchaDeckHandler;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.MinestuckPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumSet;

public class PacketRequestModusUpdate extends MinestuckPacket
{
	
	IdentifierHandler.PlayerIdentifier target;
	
	public MinestuckPacket generatePacket(Object... data)
	{
		if (data.length > 0)
		{
			NBTTagCompound nbt = null;
			
			if(data[0] instanceof EntityPlayer)
				nbt = (NBTTagCompound) IdentifierHandler.encode((EntityPlayer) data[0]).saveToNBT(new NBTTagCompound(), "Target");
			else if(data[0] instanceof IdentifierHandler)
				nbt = (NBTTagCompound) ((IdentifierHandler.PlayerIdentifier)data[0]).saveToNBT(new NBTTagCompound(), "Target");
			
			if (nbt != null)
			{
				try {
					ByteArrayOutputStream bytes = new ByteArrayOutputStream();
					CompressedStreamTools.writeCompressed(nbt, bytes);
					this.data.writeBytes(bytes.toByteArray());
				} catch (IOException var4) {
					var4.printStackTrace();
					return null;
				}
			}
		}
		
		return this;
	}
	
	public MinestuckPacket consumePacket(ByteBuf data)
	{
		if (data.readableBytes() > 0)
		{
			byte[] bytes = new byte[data.readableBytes()];
			data.readBytes(bytes);
			
			try {
				this.target = IdentifierHandler.load(CompressedStreamTools.readCompressed(new ByteArrayInputStream(bytes)), "Target");
			} catch (IOException var4) {
				var4.printStackTrace();
				return null;
			}
		}
		
		return this;
	}
	
	@Override
	public void execute(EntityPlayer player)
	{
		if(target == null)
			target = IdentifierHandler.encode(player);
		else player = target.getPlayer();
		
		if(player != null)
			MinestuckChannelHandler.sendToPlayer(MinestuckPacket.makePacket(Type.UPDATE_MODUS, CaptchaDeckHandler.writeToNBT(MinestuckPlayerData.getData(target).modus)), player);
	}
	
	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.SERVER);
	}
}