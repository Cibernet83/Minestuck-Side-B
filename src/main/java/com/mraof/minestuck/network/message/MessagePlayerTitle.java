package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumClass;
import com.mraof.minestuck.util.MinestuckPlayerData;
import com.mraof.minestuck.util.Title;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class MessagePlayerTitle implements MinestuckMessage
{
	private EnumClass clazz;
	private EnumAspect aspect;

	public MessagePlayerTitle() { }

	public MessagePlayerTitle(EnumClass clazz, EnumAspect aspect)
	{
		this.clazz = clazz;
		this.aspect = aspect;
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		data.writeInt(clazz.ordinal());
		data.writeInt(aspect.ordinal());
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		clazz = EnumClass.values()[data.readInt()];
		aspect = EnumAspect.values()[data.readInt()];
	}
	
	@Override
	public void execute(EntityPlayer player)
	{
		MinestuckPlayerData.clientData.title = new Title(clazz, aspect);
	}
	
	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}