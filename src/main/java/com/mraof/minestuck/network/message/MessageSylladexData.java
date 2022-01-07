package com.mraof.minestuck.network.message;

import com.mraof.minestuck.captchalogue.sylladex.ISylladex;
import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.SylladexUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSylladexData implements MinestuckMessage
{
	private NBTTagCompound nbt;

	private MessageSylladexData() { }

	public MessageSylladexData(EntityPlayer player)
	{
		this.nbt = SylladexUtils.getSylladex(player).writeToNBT();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeTag(buf, nbt);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		nbt = ByteBufUtils.readTag(buf);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if (nbt != null)
		{
			MultiSylladex sylladex = ISylladex.readFromNBT(player, nbt);
			SylladexUtils.setSylladex(Minecraft.getMinecraft().player, sylladex);
			if (Minecraft.getMinecraft().currentScreen instanceof GuiSylladex)
				((GuiSylladex)Minecraft.getMinecraft().currentScreen).updateSylladex(sylladex);
		}
		else
		{
			SylladexUtils.setSylladex(Minecraft.getMinecraft().player, null);
			if (Minecraft.getMinecraft().currentScreen instanceof GuiSylladex)
				Minecraft.getMinecraft().currentScreen = null;
		}
	}

	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}
