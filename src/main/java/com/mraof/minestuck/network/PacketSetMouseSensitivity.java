package com.mraof.minestuck.network;

import com.mraof.minestuck.potions.PotionMouseSensitivityAdjusterBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketSetMouseSensitivity extends MinestuckPacket
{
	private NBTTagCompound received;

	@Override
	public void generatePacket(Object... args)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("Potion", (int) args[0]);
		ByteBufUtils.writeTag(data, nbt);

	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		received = ByteBufUtils.readTag(data);

	}

	@Override
	public void execute(EntityPlayer player)
	{
		int potion = received.getInteger("Potion");
		if (potion == -1)
			PotionMouseSensitivityAdjusterBase.resetMouseSensitivity(player);
		else
			((PotionMouseSensitivityAdjusterBase) Potion.getPotionById(potion)).setMouseSensitivity(player);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.SERVER);
	}
}
