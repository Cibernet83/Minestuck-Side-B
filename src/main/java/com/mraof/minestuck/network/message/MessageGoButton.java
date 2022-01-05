package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import com.mraof.minestuck.inventory.ContainerGristWidget;
import com.mraof.minestuck.inventory.miniMachines.ContainerMiniSburbMachine;
import com.mraof.minestuck.tileentity.TileEntityMachine;
import com.mraof.minestuck.util.Debug;

public class MessageGoButton extends MinestuckMessage
{
	
	public boolean newMode;
	public boolean overrideStop;
	
	@Override
	public void generatePacket(Object... dat)
	{
		data.writeBoolean((Boolean) dat[0]);
		data.writeBoolean((Boolean) dat[1]);
		

	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		newMode = data.readBoolean();
		overrideStop = data.readBoolean();
		

	}

	@Override
	public void execute(EntityPlayer player)
	{
		TileEntityMachine te;
		if(player.openContainer instanceof ContainerMiniSburbMachine)
				te = ((ContainerMiniSburbMachine) player.openContainer).tileEntity;
		else if(player.openContainer instanceof ContainerGristWidget)
			te = ((ContainerGristWidget) player.openContainer).tileEntity;
		else return;
		
		if (te == null)
		{
			System.out.println("Invalid TE in container for player %s");
			Debug.warnf("Invalid TE in container for player %s!", player.getName());
		} else
		{
			System.out.println("Button pressed. Alchemiter going!");
			Debug.debug("Button pressed. Alchemiter going!");
			te.ready = newMode;
			te.overrideStop = overrideStop;
		}
	}

	@Override
	public EnumSet<Side> getSenderSide() {
		return EnumSet.of(Side.CLIENT);
	}

}
