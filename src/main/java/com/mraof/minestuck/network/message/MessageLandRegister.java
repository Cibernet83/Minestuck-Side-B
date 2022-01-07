package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.world.MinestuckDimensionHandler;
import com.mraof.minestuck.world.lands.LandAspectRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashMap;

public class MessageLandRegister implements MinestuckMessage
{
	private HashMap<Integer, Tuple<LandAspectRegistry.AspectCombination, BlockPos>> lands;

	public MessageLandRegister() { }
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		MinestuckDimensionHandler.forEachLand((Integer dim, LandAspectRegistry.AspectCombination type) -> {
			buf.writeInt(dim);
			ByteBufUtils.writeUTF8String(buf, type.aspectTerrain.getPrimaryName());
			ByteBufUtils.writeUTF8String(buf, type.aspectTitle.getPrimaryName());
			BlockPos spawn = MinestuckDimensionHandler.getSpawn(dim);
			buf.writeInt(spawn.getX());
			buf.writeInt(spawn.getY());
			buf.writeInt(spawn.getZ());
		});
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		lands = new HashMap<>();
		while(buf.readableBytes() > 0)
		{
			int dimID = buf.readInt();
			String aspect1 = ByteBufUtils.readUTF8String(buf);
			String aspect2 = ByteBufUtils.readUTF8String(buf);
			BlockPos spawn = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
			lands.put(dimID, new Tuple<>(new LandAspectRegistry.AspectCombination(LandAspectRegistry.fromNameTerrain(aspect1), LandAspectRegistry.fromNameTitle(aspect2)), spawn));
		}
	}
	
	@Override
	public void execute(EntityPlayer player) 
	{
		MinestuckDimensionHandler.onLandPacket(lands);
	}
	
	@Override
	public Side toSide() {
		return Side.CLIENT;
	}

}
