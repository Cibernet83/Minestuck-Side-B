package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.tileentity.TileEntityBoondollarRegister;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class MessageBoondollarRegisterAutoRequest implements MinestuckMessage
{
	private BlockPos pos;

	public MessageBoondollarRegisterAutoRequest() { }

	public MessageBoondollarRegisterAutoRequest(TileEntityBoondollarRegister te)
	{
		this.pos = te.getPos();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(player.world.getTileEntity(pos) instanceof TileEntityBoondollarRegister)
		{
			TileEntityBoondollarRegister vault = (TileEntityBoondollarRegister) player.world.getTileEntity(pos);
			vault.auto = !vault.auto;
			//player.world.scheduleUpdate(pos, vault.getBlockType(), vault.getBlockType().tickRate(player.world));
			player.world.notifyBlockUpdate(vault.getPos(), player.world.getBlockState(pos), player.world.getBlockState(pos), 3);
		}
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
