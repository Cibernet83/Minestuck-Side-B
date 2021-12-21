package com.mraof.minestuck.network;

import com.mraof.minestuck.world.MinestuckDimensionHandler;
import com.mraof.minestuck.world.lands.LandAspectRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class PacketLandRegister extends MinestuckPacket
{
	public HashMap<Integer, LandAspectRegistry.AspectCombination> aspectMap;
	public HashMap<Integer, BlockPos> spawnMap;
	
	@Override
	public void generatePacket(Object... dat)
	{
		for(Map.Entry<Integer, LandAspectRegistry.AspectCombination> entry : MinestuckDimensionHandler.getLandSet())
		{
			this.data.writeInt(entry.getKey());
			ByteBufUtils.writeUTF8String(data, entry.getValue().aspectTerrain.getPrimaryName());
			ByteBufUtils.writeUTF8String(data, entry.getValue().aspectTitle.getPrimaryName());
			BlockPos spawn = MinestuckDimensionHandler.getSpawn(entry.getKey());
			data.writeInt(spawn.getX());
			data.writeInt(spawn.getY());
			data.writeInt(spawn.getZ());
		}

	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		aspectMap = new HashMap<>();
		spawnMap = new HashMap<>();
		while(data.readableBytes() > 0)
		{
			int dim = data.readInt();
			String aspect1 = ByteBufUtils.readUTF8String(data);
			String aspect2 = ByteBufUtils.readUTF8String(data);
			BlockPos spawn = new BlockPos(data.readInt(), data.readInt(), data.readInt());
			aspectMap.put(dim, new LandAspectRegistry.AspectCombination(LandAspectRegistry.fromNameTerrain(aspect1), LandAspectRegistry.fromNameTitle(aspect2)));
			spawnMap.put(dim, spawn);
		}
		

	}
	
	@Override
	public void execute(EntityPlayer player) 
	{
		MinestuckDimensionHandler.onLandPacket(this);
	}
	
	@Override
	public EnumSet<Side> getSenderSide() {
		return EnumSet.of(Side.SERVER);
	}

}
