package com.mraof.minestuck.network.message;

import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.MinestuckPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class MessageGristCache implements MinestuckMessage
{
	public GristSet gristSet;
	public boolean targetGrist;

	public MessageGristCache() { }

	public MessageGristCache(GristSet gristSet, boolean targetGrist)
	{
		this.gristSet = gristSet;
		this.targetGrist = targetGrist;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(gristSet.gristTypes.size());
		gristSet.getMap().forEach((Grist grist, Integer amount) -> {
			ByteBufUtils.writeRegistryEntry(buf, grist);
			buf.writeInt(amount);
		});
		buf.writeBoolean(targetGrist);

	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		gristSet = new GristSet();
		int length = buf.readInt();
		for (int i = 0; i < length; i++)
			gristSet.setGrist(ByteBufUtils.readRegistryEntry(buf, Grist.REGISTRY), buf.readInt());
		targetGrist = buf.readBoolean();
	}

	@Override
	public void execute(EntityPlayer player)
	{
		MinestuckPlayerData.setGristCache(gristSet, targetGrist);
	}

	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}
