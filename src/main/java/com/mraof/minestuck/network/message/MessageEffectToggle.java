package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.MinestuckPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;

public class MessageEffectToggle implements MinestuckMessage
{
	public MessageEffectToggle() { }

	@Override
	public void toBytes(ByteBuf buf) { }

	@Override
	public void fromBytes(ByteBuf buf) { }

	@Override
	public void execute(EntityPlayer player) 
	{
		IdentifierHandler.PlayerIdentifier handler = IdentifierHandler.encode(player);
		MinestuckPlayerData.setEffectToggle(handler, !MinestuckPlayerData.getEffectToggle(handler));
		if(MinestuckPlayerData.getData(handler).effectToggle)
			player.sendStatusMessage(new TextComponentTranslation("aspectEffects.on"), true);
		else
			player.sendStatusMessage(new TextComponentTranslation("aspectEffects.off"), true);
	}

	@Override
	public Side toSide() {
		return Side.SERVER;
	}
}
