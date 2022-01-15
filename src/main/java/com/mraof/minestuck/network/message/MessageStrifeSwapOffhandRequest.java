package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.strife.StrifePortfolioHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageStrifeSwapOffhandRequest implements MinestuckMessage
{
	private int specibusIndex;
	private int weaponIndex;

	public MessageStrifeSwapOffhandRequest() { }

	public MessageStrifeSwapOffhandRequest(int specibusIndex, int weaponIndex)
	{
		this.specibusIndex = specibusIndex;
		this.weaponIndex = weaponIndex;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		specibusIndex = buf.readInt();
		weaponIndex = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(specibusIndex);
		buf.writeInt(weaponIndex);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		StrifePortfolioHandler.swapOffhandWeapon(player, specibusIndex, weaponIndex);
		MinestuckNetwork.sendTo(new MessageStrifePortfolio(player), player);
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
