package com.mraof.minestuck.network.message;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class MessageBeamData implements MinestuckMessage
{
	private int worldId;
	private NBTTagCompound nbt;

	public MessageBeamData() { }

	public MessageBeamData(World world)
	{
		this.worldId = world.provider.getDimension();
		this.nbt = world.getCapability(MinestuckCapabilities.BEAM_DATA, null).writeToNBT();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(worldId);
		ByteBufUtils.writeTag(buf, nbt);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		worldId = buf.readInt();
		nbt = ByteBufUtils.readTag(buf);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(player.world.provider.getDimension() == worldId)
			player.world.getCapability(MinestuckCapabilities.BEAM_DATA, null).readFromNBT(nbt);
	}

	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}
