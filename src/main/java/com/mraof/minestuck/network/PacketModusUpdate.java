package com.mraof.minestuck.network;

import com.mraof.minestuck.inventory.captchalouge.CaptchaDeckHandler;
import com.mraof.minestuck.util.Debug;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumSet;

public class PacketModusUpdate extends MinestuckPacket
{
	public NBTTagCompound nbt;
	
	public PacketModusUpdate() {
	}
	
	public MinestuckPacket generatePacket(Object... data)
	{
		if (data.length > 0)
		{
			if (data[0] != null)
			{
				try {
					ByteArrayOutputStream bytes = new ByteArrayOutputStream();
					CompressedStreamTools.writeCompressed((NBTTagCompound)data[0], bytes);
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
				this.nbt = CompressedStreamTools.readCompressed(new ByteArrayInputStream(bytes));
			} catch (IOException var4) {
				var4.printStackTrace();
				return null;
			}
		}
		
		return this;
	}
	
	public void execute(EntityPlayer player)
	{
		CaptchaDeckHandler.clientSideModus = CaptchaDeckHandler.readFromNBT(this.nbt, true);
		if (CaptchaDeckHandler.clientSideModus != null)
			CaptchaDeckHandler.clientSideModus.getGuiHandler().updateContent();
		else Debug.debug("Lost modus");
	}
	
	public EnumSet<Side> getSenderSide() {
		return EnumSet.allOf(Side.class);
	}
}
