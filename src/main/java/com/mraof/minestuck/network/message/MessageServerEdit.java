package com.mraof.minestuck.network.message;

import com.mraof.minestuck.editmode.ClientEditHandler;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.IdentifierHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class MessageServerEdit implements MinestuckMessage
{
	private String target;
	private int posX, posZ;
	private NBTTagCompound deployTags;
	private boolean[] givenItems;

	public MessageServerEdit()
	{
		this(new boolean[0]);
	}

	public MessageServerEdit(boolean[] givenItems)
	{
		this.givenItems = givenItems;
	}

	public MessageServerEdit(IdentifierHandler.PlayerIdentifier target, int posX, int posZ, NBTTagCompound deployTags, boolean[] givenItems)
	{
		this.target = target.toString();
		this.posX = posX;
		this.posZ = posZ;
		this.deployTags = deployTags;
		this.givenItems = givenItems;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		if (buf.readBoolean())
		{
			target = ByteBufUtils.readUTF8String(buf);
			posX = buf.readInt();
			posZ = buf.readInt();
			deployTags = ByteBufUtils.readTag(buf);
		}
		givenItems = new boolean[buf.readInt()];
		for (int i = 0; i < givenItems.length; i++)
			givenItems[i] = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(target != null);
		if (target != null)
		{
			ByteBufUtils.writeUTF8String(buf, target);
			buf.writeInt(posX);
			buf.writeInt(posZ);
			ByteBufUtils.writeTag(buf, deployTags);
		}
		buf.writeInt(givenItems.length);
		for (boolean givenItem : givenItems)
			buf.writeBoolean(givenItem);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		ClientEditHandler.onClientPackage(target, posX, posZ, givenItems, deployTags);
	}

	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}
