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

import java.util.EnumSet;

public class MessageUpdateSylladexData extends MinestuckMessage
{
	private NBTTagCompound nbt;

	@Override
	public void generatePacket(Object... args)
	{
		ByteBufUtils.writeTag(data, SylladexUtils.getSylladex((EntityPlayer)args[0]).writeToNBT());
	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		nbt = ByteBufUtils.readTag(data);
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
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.SERVER);
	}
}
