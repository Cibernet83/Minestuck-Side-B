package com.mraof.minestuck.network;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.gui.MinestuckGuiHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketOpenGui extends MinestuckPacket // EntityPlayer$openGui isn't working serverside lol
{
	private int type;

	@Override
	public void generatePacket(Object... args)
	{
		data.writeInt(((MinestuckGuiHandler.GuiId)args[0]).ordinal());
	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		type = data.readInt();
	}

	@Override
	public void execute(EntityPlayer player)
	{
		BlockPos pos = player.getPosition();
		player.openGui(Minestuck.instance, type, player.getEntityWorld(), pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
