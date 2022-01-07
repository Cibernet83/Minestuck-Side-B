package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.tileentity.TileEntityAlchemiter;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class MessageAlchemize implements MinestuckMessage
{
	private BlockPos tePos;
	private int quantity;

	public MessageAlchemize() { }

	public MessageAlchemize(TileEntityAlchemiter alchemiter, int quantity)
	{
		this.tePos = alchemiter.getPos();
		this.quantity = quantity;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(tePos.getX());
		buf.writeInt(tePos.getY());
		buf.writeInt(tePos.getZ());
		buf.writeInt(quantity);
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		tePos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		quantity = buf.readInt();
	}
	
	@Override
	public void execute(EntityPlayer player)
	{
		if(player.getEntityWorld().isBlockLoaded(tePos))
		{
			TileEntity te;
			te = player.getEntityWorld().getTileEntity(tePos);
			if(te instanceof TileEntityAlchemiter)
			{
				((TileEntityAlchemiter) te).processContents(quantity, player);
			}
		}
	}
	
	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}