package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.tileentity.TileEntityBoondollarRegister;
import com.mraof.minestuck.util.MinestuckPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class MessageBoondollarRegister implements MinestuckMessage
{
	private EnumType type;
	private BlockPos pos;
	private int mav;

	private MessageBoondollarRegister() { }

	public MessageBoondollarRegister(EnumType type, TileEntityBoondollarRegister te)
	{
		this.type = type;
		this.pos = te.getPos();
		this.mav = te.mav;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(type.ordinal());
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeInt(mav);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		type = EnumType.values()[buf.readInt()];
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		mav = buf.readInt();
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(player.world.getTileEntity(pos) instanceof TileEntityBoondollarRegister)
		{
			TileEntityBoondollarRegister vault = (TileEntityBoondollarRegister) player.world.getTileEntity(pos);

			switch (type)
			{
				case AUTO:
					vault.auto = !vault.auto;
					break;
				case TAKE:
					MinestuckPlayerData.addBoondollars(player, vault.getStoredBoons());
					vault.setStoredBoons(0);
					break;
				case MAV:
					vault.mav = mav;
					break;
			}
			//player.world.scheduleUpdate(pos, vault.getBlockType(), vault.getBlockType().tickRate(player.world));
			player.world.notifyBlockUpdate(vault.getPos(), player.world.getBlockState(pos), player.world.getBlockState(pos), 3);
		}
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}

	public enum EnumType
	{
		UPDATE,
		TAKE,
		AUTO,
		MAV,
		;
	}
}
