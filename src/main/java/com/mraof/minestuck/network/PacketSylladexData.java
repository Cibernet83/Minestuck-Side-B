package com.mraof.minestuck.network;

import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import com.mraof.minestuck.sylladex.ISylladex;
import com.mraof.minestuck.sylladex.MultiSylladex;
import com.mraof.minestuck.util.MinestuckPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketSylladexData extends MinestuckPacket
{
	private NBTTagCompound nbt;

	@Override
	public void generatePacket(Object... args)
	{
		ByteBufUtils.writeTag(data, (NBTTagCompound)args[0]);
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
			MultiSylladex sylladex = ISylladex.readFromNBT(nbt);
			MinestuckPlayerData.clientData.sylladex = sylladex;
			if (Minecraft.getMinecraft().currentScreen instanceof SylladexGuiHandler)
				((SylladexGuiHandler)Minecraft.getMinecraft().currentScreen).updateSylladex(sylladex);
		}
		else
		{
			MinestuckPlayerData.clientData.sylladex = null;
			if (Minecraft.getMinecraft().currentScreen instanceof SylladexGuiHandler)
				Minecraft.getMinecraft().currentScreen = null;
		}
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.SERVER);
	}
}
