package com.mraof.minestuck.network;

import com.mraof.minestuck.util.MSUUtils;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.MinestuckPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketPorkhollowWithdraw extends MinestuckPacket
{
	EntityPlayer reciever;
	int amount;
	int n;
	
	@Override
	public void generatePacket(Object... dat)
	{
		IdentifierHandler.PlayerIdentifier identifier = IdentifierHandler.encode((EntityPlayer) dat[0]);
		int n = 1;
		this.data.writeInt(identifier.getId());
		this.data.writeInt((int)dat[1]);
		if(dat.length > 3)
			n = Math.max(0,(int)dat[2]);
		this.data.writeInt(n);

	}
	
	@Override
	public void consumePacket(ByteBuf data)
	{
		reciever = IdentifierHandler.getById(data.readInt()).getPlayer();
		amount = data.readInt();
		n = data.readInt();

	}
	
	@Override
	public void execute(EntityPlayer sender)
	{
		if(MinestuckPlayerData.addBoondollars(sender, -amount))
		{
			int split = 0;
			if(n > 0)
				split = (int) Math.floor(amount/n);
			for(int i = 0; i < n; i++)
				MSUUtils.giveBoonItem(sender, split);
			if(split*n != amount)
				MSUUtils.giveBoonItem(sender, amount-split*n);
			//sender.sendMessage(new TextComponentTranslation("message.atm.withdrawSuccess", amount));
		} else sender.sendMessage(new TextComponentTranslation("commands.porkhollow.notEnough"));
	}
	
	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
	
	
	
	public enum Type
	{
		SEND,
		TAKE
		;
	}
}
