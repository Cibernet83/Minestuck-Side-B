package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.tileentity.TileEntityMachineChassis;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class MessageAssembleMachineChassisRequest implements MinestuckMessage
{
	private BlockPos pos;

	public MessageAssembleMachineChassisRequest() { }

	public MessageAssembleMachineChassisRequest(TileEntityMachineChassis machineChassis)
	{
		pos = machineChassis.getPos();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if (player.getEntityWorld().isBlockLoaded(this.pos))
		{
			TileEntityMachineChassis te = (TileEntityMachineChassis) player.getEntityWorld().getTileEntity(pos);
			if (te != null)
				te.assemble();
		}
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
