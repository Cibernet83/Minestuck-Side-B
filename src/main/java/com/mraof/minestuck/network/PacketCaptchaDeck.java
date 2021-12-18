package com.mraof.minestuck.network;

import com.mraof.minestuck.editmode.ServerEditHandler;
import com.mraof.minestuck.util.SylladexUtils;
import com.mraof.minestuck.inventory.captchalouge.ContainerCaptchaDeck;
import com.mraof.minestuck.util.Debug;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumSet;

public class PacketCaptchaDeck extends MinestuckPacket
{
	
	public static final byte DATA = 0;
	public static final byte MODUS = 1;
	public static final byte CAPTCHALOUGE = 2;
	public static final byte GET = 3;
	public static final byte VALUE = 4;
	public static final byte CAPTCHALOUGE_INV = 5;
	
	public byte type;
	
	public NBTTagCompound nbt;
	
	public int itemIndex;
	public boolean getCard;
	
	public byte valueType;
	public int value;
	
	public int slotIndex;
	
	@Override
	public MinestuckPacket generatePacket(Object... data)
	{
		byte type = (Byte) data[0];
		this.data.writeByte(type);	//Packet type
		if(data.length > 1)
		{
			if(type == DATA && data[1] != null)	//Server side data
			{
				try
				{
					ByteArrayOutputStream bytes = new ByteArrayOutputStream();
					CompressedStreamTools.writeCompressed((NBTTagCompound)data[1], bytes);
					this.data.writeBytes(bytes.toByteArray());
				}
				catch (IOException e)
				{
					e.printStackTrace();
					return null;
				}
			}
			else if(type == GET)
			{
				int[] slots = (int[]) data[1];
				this.data.writeByte((byte)slots.length);
				for (int slot : slots)
					this.data.writeByte(slot);
				this.data.writeBoolean((boolean)data[2]); // asCard
			}
			else if(type == VALUE)
			{
				this.data.writeByte((Byte)data[1]);
				this.data.writeInt((Integer)data[2]);
			}
			else if(type == CAPTCHALOUGE_INV && data[1] != null) {
				this.data.writeInt((Integer)data[1]);
			}
		}
		
		return this;
	}

	@Override
	public MinestuckPacket consumePacket(ByteBuf data)
	{
		this.type = data.readByte();
		
		if(data.readableBytes() > 0)
		{
			if(this.type == DATA)
			{
				byte[] bytes = new byte[data.readableBytes()];
				data.readBytes(bytes);
				try
				{
					this.nbt = CompressedStreamTools.readCompressed(new ByteArrayInputStream(bytes));
				}
				catch(IOException e)
				{
					e.printStackTrace();
					return null;
				}
			}
			else if(this.type == GET)
			{
				this.itemIndex = data.readByte();
				this.getCard = data.readBoolean();
			}
			else if(this.type == VALUE)
			{
				this.valueType = data.readByte();
				this.value = data.readInt();
			}
			else if(this.type == CAPTCHALOUGE_INV) {
				this.slotIndex = data.readInt();
			}
		}
		
		return this;
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(player != null && player.world != null && !player.world.isRemote)
		{
			if(ServerEditHandler.getData(player) != null)
				return;
			
			if(this.type == MODUS && player.openContainer instanceof ContainerCaptchaDeck)
				SylladexUtils.activateModusSlot((EntityPlayerMP) player);
			else if(this.type == CAPTCHALOUGE && !player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isEmpty())
				SylladexUtils.captchalougeItemInHand((EntityPlayerMP) player);
			else if(this.type == CAPTCHALOUGE_INV)
				SylladexUtils.captchalougeItemInInventory((EntityPlayerMP) player, slotIndex);
			else if(this.type == GET)
				SylladexUtils.retrieve((EntityPlayerMP) player, itemIndex, getCard);
//			else if(this.type == DATA)
//				MinestuckChannelHandler.sendToPlayer(MinestuckPacket.makePacket(Type.CAPTCHA, DATA, CaptchaDeckHandler.writeToNBT(CaptchaDeckHandler.getSylladex(player))), player);
			else if(this.type == VALUE && SylladexUtils.getSylladex(player) != null)
				SylladexUtils.getSylladex(player).setValue(valueType, value);
		}
		else
		{
			if(this.type == DATA)
			{
//				if(player == null) Should not be needed anymore
//					MinestuckChannelHandler.sendToServer(MinestuckPacket.makePacket(Type.CAPTCHA, DATA));
//				else
//				{
				SylladexUtils.clientSideModus = SylladexUtils.readFromNBT(nbt, true);
				if(SylladexUtils.clientSideModus != null)
					SylladexUtils.clientSideModus.getGuiHandler().updateContent();
				else Debug.debug("Lost modus");
//				}
			}
		}
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.allOf(Side.class);
	}
	
}
