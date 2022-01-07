package com.mraof.minestuck.network.message;

import com.mraof.minestuck.inventory.ContainerGristWidget;
import com.mraof.minestuck.inventory.miniMachines.ContainerMiniSburbMachine;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.tileentity.TileEntityMachine;
import com.mraof.minestuck.util.Debug;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageGoButton implements MinestuckMessage
{
	private boolean newMode;
	private boolean overrideStop;

	public MessageGoButton() { }

	public MessageGoButton(boolean newMode, boolean overrideStop)
	{
		this.newMode = newMode;
		this.overrideStop = overrideStop;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		newMode = buf.readBoolean();
		overrideStop = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(newMode);
		buf.writeBoolean(overrideStop);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		TileEntityMachine te;
		if (player.openContainer instanceof ContainerMiniSburbMachine)
			te = ((ContainerMiniSburbMachine) player.openContainer).tileEntity;
		else if (player.openContainer instanceof ContainerGristWidget)
			te = ((ContainerGristWidget) player.openContainer).tileEntity;
		else return;

		if (te == null)
		{
			System.out.println("Invalid TE in container for player %s");
			Debug.warnf("Invalid TE in container for player %s!", player.getName());
		}
		else
		{
			System.out.println("Button pressed. Alchemiter going!");
			Debug.debug("Button pressed. Alchemiter going!");
			te.ready = newMode;
			te.overrideStop = overrideStop;
		}
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}

}
