package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.MinestuckPlayerData;
import com.mraof.minestuck.util.MinestuckUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;

public class MessagePorkhollowWithdraw implements MinestuckMessage
{
	private EntityPlayer reciever;
	private int amount;
	private int count;

	public MessagePorkhollowWithdraw() { }

	public MessagePorkhollowWithdraw(EntityPlayer reciever, int amount, int count)
	{
		this.reciever = reciever;
		this.amount = amount;
		this.count = Math.max(0, count);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		reciever = IdentifierHandler.getById(buf.readInt()).getPlayer();
		amount = buf.readInt();
		count = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		IdentifierHandler.PlayerIdentifier identifier = IdentifierHandler.encode(reciever);
		buf.writeInt(identifier.getId());
		buf.writeInt(amount);
		buf.writeInt(count);
	}

	@Override
	public void execute(EntityPlayer sender)
	{
		if (MinestuckPlayerData.addBoondollars(sender, -amount))
		{
			int split = 0;
			if (count > 0)
				split = amount / count;
			for (int i = 0; i < count; i++)
				MinestuckUtils.giveBoonItem(sender, split);
			if (split * count != amount)
				MinestuckUtils.giveBoonItem(sender, amount - split * count);
			//sender.sendMessage(new TextComponentTranslation("message.atm.withdrawSuccess", amount));
		}
		else
			sender.sendMessage(new TextComponentTranslation("commands.porkhollow.notEnough"));
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
