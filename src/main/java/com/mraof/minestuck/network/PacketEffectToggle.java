package com.mraof.minestuck.network;

import java.util.EnumSet;

import com.mraof.minestuck.util.Debug;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.MinestuckPlayerData;
import com.mraof.minestuck.util.IdentifierHandler.PlayerIdentifier;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;

public class PacketEffectToggle extends MinestuckPacket
{

	@Override
	public void generatePacket(Object... data) {}

	@Override
	public void consumePacket(ByteBuf data) {}

	@Override
	public void execute(EntityPlayer player) 
	{
		IdentifierHandler.PlayerIdentifier handler = IdentifierHandler.encode(player);
		MinestuckPlayerData.setEffectToggle(handler, !MinestuckPlayerData.getEffectToggle(handler));
		if(MinestuckPlayerData.getData(handler).effectToggle)
		{
			player.sendStatusMessage(new TextComponentTranslation("aspectEffects.on"), true);
		}
		else {
			player.sendStatusMessage(new TextComponentTranslation("aspectEffects.off"), true);
		}
	}

	@Override
	public EnumSet<Side> getSenderSide() {return EnumSet.of(Side.CLIENT);}

}
