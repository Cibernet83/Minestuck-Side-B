package com.mraof.minestuck.network.message;

import com.mraof.minestuck.client.gui.GuiTitleSelector;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumClass;
import com.mraof.minestuck.util.Title;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSelectTitleGui implements MinestuckMessage
{
	private EnumClass clazz;
	private EnumAspect aspect;

	private MessageSelectTitleGui() { }

	public MessageSelectTitleGui(EnumClass clazz, EnumAspect aspect)
	{
		this.clazz = clazz;
		this.aspect = aspect;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(clazz == null ? -1 : clazz.ordinal());
		buf.writeInt(aspect == null ? -1 : aspect.ordinal());
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		int clazzOrdinal = buf.readInt();
		clazz = clazzOrdinal < 0 ? null : EnumClass.values()[clazzOrdinal];
		int aspectOrdinal = buf.readInt();
		aspect = aspectOrdinal < 0 ? null : EnumAspect.values()[aspectOrdinal];
	}
	
	@Override
	public void execute(EntityPlayer player)
	{
		Title title;
		if(clazz != null && aspect != null)
			title = new Title(clazz, aspect);
		else
			title = null;
		FMLClientHandler.instance().showGuiScreen(new GuiTitleSelector(title));
	}
	
	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}