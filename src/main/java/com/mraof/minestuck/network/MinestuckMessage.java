package com.mraof.minestuck.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;

public interface MinestuckMessage extends IMessage
{
	void execute(EntityPlayer player);
	Side toSide();
}
