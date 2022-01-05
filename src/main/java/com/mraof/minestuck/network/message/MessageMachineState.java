package com.mraof.minestuck.network.message;

import com.mraof.minestuck.inventory.miniMachines.ContainerMiniSburbMachine;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.tileentity.TileEntityMiniSburbMachine;
import com.mraof.minestuck.util.Debug;
import com.mraof.minestuck.alchemy.Grist;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class MessageMachineState extends MinestuckMessage
{

	public int xCoord;
	public int yCoord;
	public int zCoord;
	public Grist grist;
	
	@Override
	public void generatePacket(Object... dat)
	{
		data.writeInt(Grist.REGISTRY.getID((Grist) dat[0]));
		

	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		grist = Grist.REGISTRY.getValue(data.readInt());
		

	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(!(player.openContainer instanceof ContainerMiniSburbMachine))
			return;
		
		TileEntityMiniSburbMachine te = ((ContainerMiniSburbMachine) player.openContainer).tileEntity;
		
		if (te == null)
		{
			Debug.warnf("Invalid TE in container for player %s!", player.getName());
		}
		else
		{
			te.selectedGrist = grist;
		}
	}

	@Override
	public EnumSet<Side> getSenderSide() {
		return EnumSet.of(Side.CLIENT);
	}

}
