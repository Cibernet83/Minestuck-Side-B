package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.tileentity.TileEntityAlchemiter;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class MessageAlchemiter extends MinestuckMessage
{
	
	public BlockPos tePos;
	public int quantity;
	
	@Override
	public void generatePacket(Object... dat)
	{
		TileEntity te = ((TileEntity) dat[0]);
		data.writeInt(te.getPos().getX());
		data.writeInt(te.getPos().getY());
		data.writeInt(te.getPos().getZ());
		data.writeInt((int) dat[1]);
	}
	
	@Override
	public void consumePacket(ByteBuf data)
	{
		tePos = new BlockPos(data.readInt(), data.readInt(), data.readInt());
		
		quantity = data.readInt();
		

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
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}