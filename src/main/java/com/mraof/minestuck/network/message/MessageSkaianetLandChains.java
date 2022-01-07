package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.network.skaianet.SkaiaClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

public class MessageSkaianetLandChains implements MinestuckMessage
{
	private List<List<Integer>> landChains;

	private MessageSkaianetLandChains() { }

	public MessageSkaianetLandChains(List<List<Integer>> landChains)
	{
		this.landChains = landChains;
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		for(List<Integer> list : landChains)
		{
			buf.writeInt(list.size());
			for(int i : list)
				buf.writeInt(i);
		}
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		landChains = new ArrayList<>();
		while(buf.readableBytes() > 0)
		{
			int size = buf.readInt();
			List<Integer> list = new ArrayList<>();
			for(int i = 0; i < size; i++)
				list.add(buf.readInt());
			landChains.add(list);
		}
	}

	@Override
	public void execute(EntityPlayer player)
	{
		SkaiaClient.setLandChains(landChains);
	}

	@Override
	public Side toSide() {
		return Side.CLIENT;
	}
}
