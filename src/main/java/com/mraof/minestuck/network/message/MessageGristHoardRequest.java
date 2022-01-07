package com.mraof.minestuck.network.message;

import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class MessageGristHoardRequest implements MinestuckMessage
{
	private Grist grist;

	public MessageGristHoardRequest() { }

	public MessageGristHoardRequest(Grist grist)
	{
		this.grist = grist;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		grist = ByteBufUtils.readRegistryEntry(buf, Grist.REGISTRY);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeRegistryEntry(buf, grist);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).setGristHoard(grist);
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
