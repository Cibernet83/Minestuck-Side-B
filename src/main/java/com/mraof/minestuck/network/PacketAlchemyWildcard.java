package com.mraof.minestuck.network;

import com.mraof.minestuck.inventory.captchalouge.AlchemyModus;
import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.util.MinestuckPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketAlchemyWildcard extends MinestuckPacket
{
	Grist grist;
	
	@Override
	public MinestuckPacket generatePacket(Object... args)
	{
		this.data.writeInt(Grist.REGISTRY.getID((Grist)args[0]));
		return this;
	}
	
	@Override
	public MinestuckPacket consumePacket(ByteBuf buffer)
	{
		grist = Grist.REGISTRY.getValue(buffer.readInt());
		return this;
	}
	
	@Override
	public void execute(EntityPlayer player)
	{
		if(MinestuckPlayerData.getData(player).modus instanceof AlchemyModus)
			((AlchemyModus) MinestuckPlayerData.getData(player).modus).setWildcardGrist(grist);
	}
	
	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
