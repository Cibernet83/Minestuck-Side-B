package com.cibernet.minestuckgodtier.network;

import com.cibernet.minestuckgodtier.potions.PotionMouseSensitivityAdjusterBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketSetMouseSensitivity extends MSGTPacket
{
	private NBTTagCompound received;

	@Override
	public MSGTPacket generatePacket(Object... args)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("Potion", (int) args[0]);
		ByteBufUtils.writeTag(data, nbt);
		return this;
	}

	@Override
	public MSGTPacket consumePacket(ByteBuf data)
	{
		received = ByteBufUtils.readTag(data);
		return this;
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
