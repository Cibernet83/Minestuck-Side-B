package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.network.skaianet.ComputerData;
import com.mraof.minestuck.network.skaianet.SkaianetHandler;
import com.mraof.minestuck.tileentity.TileEntityComputer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageClearProgramRequest implements MinestuckMessage
{
	private ComputerData computer;
	private int program;

	private MessageClearProgramRequest() { }

	public MessageClearProgramRequest(ComputerData computer, int program){
		this.computer = computer;
		this.program = program;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(computer.getX());
		buf.writeInt(computer.getY());
		buf.writeInt(computer.getZ());
		buf.writeInt(computer.getDimension());
		buf.writeInt(program);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		computer = new ComputerData(null, buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
		program = buf.readInt();
	}

	@Override
	public void execute(EntityPlayer player)
	{
		TileEntityComputer te = SkaianetHandler.getComputer(computer);
		
		if(te != null)
		{
			te.latestmessage.put(program, "");
			te.markBlockForUpdate();
		}
	}
	
	@Override
	public Side toSide() {
		return Side.SERVER;
	}

}
