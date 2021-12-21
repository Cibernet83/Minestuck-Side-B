package com.mraof.minestuck.network;

import com.mraof.minestuck.editmode.ClientEditHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketServerEdit extends MinestuckPacket
{

	private String target;
	private int posX, posZ;
	private boolean[] givenItems;
	private NBTTagCompound deployTags;
	
	@Override
	public void generatePacket(Object... args)
	{
		if(args.length == 1 || args.length == 2)
		{
			data.writeBoolean(true);
			boolean[] booleans = (boolean[]) args[0];
			data.writeInt(booleans.length);
			for(boolean b : booleans)
				data.writeBoolean(b);
		}
		else if (args.length > 2)
		{
			data.writeBoolean(false);
			ByteBufUtils.writeUTF8String(data, args[0].toString());
			data.writeInt((Integer) args[1]);
			data.writeInt((Integer) args[2]);
			boolean[] booleans = (boolean[]) args[3];
			data.writeInt(booleans.length);
			for (boolean b : booleans)
				data.writeBoolean(b);
			ByteBufUtils.writeTag(data, (NBTTagCompound) args[4]);
		}
	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		if(data.readBoolean())
		{
			givenItems = new boolean[data.readableBytes()];
			for(int i = 0; i < givenItems.length; i++)
				givenItems[i] = data.readBoolean();
		}
		else
		{
			target = ByteBufUtils.readUTF8String(data);
			posX = data.readInt();
			posZ = data.readInt();
			givenItems = new boolean[data.readInt()];
			for (int i = 0; i < givenItems.length; i++)
			{
				givenItems[i] = data.readBoolean();
			}
			deployTags = ByteBufUtils.readTag(data);
		}
	}

	@Override
	public void execute(EntityPlayer player)
	{
		ClientEditHandler.onClientPackage(target, posX, posZ, givenItems, deployTags);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.SERVER);
	}

}
