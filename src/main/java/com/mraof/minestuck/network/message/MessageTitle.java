package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumClass;
import com.mraof.minestuck.util.MinestuckPlayerData;
import com.mraof.minestuck.util.Title;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageTitle implements MinestuckMessage
{
	private Title title;

	public MessageTitle() { }

	public MessageTitle(Title title)
	{
		this.title = title;
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(title.getHeroClass().ordinal());
		buf.writeInt(title.getHeroAspect().ordinal());
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		title = new Title(EnumClass.values()[buf.readInt()], EnumAspect.values()[buf.readInt()]);
	}
	
	@Override
	public void execute(EntityPlayer player)
	{
		MinestuckPlayerData.clientData.title = title;
	}
	
	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}