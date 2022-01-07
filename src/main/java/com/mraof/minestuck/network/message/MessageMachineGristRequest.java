package com.mraof.minestuck.network.message;

import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.inventory.miniMachines.ContainerMiniSburbMachine;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.tileentity.TileEntityMiniSburbMachine;
import com.mraof.minestuck.util.Debug;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class MessageMachineGristRequest implements MinestuckMessage
{
	private Grist grist;

	public MessageMachineGristRequest() { }

	public MessageMachineGristRequest(Grist grist)
	{
		this.grist = grist;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		grist = ByteBufUtils.readRegistryEntry(buf, Grist.REGISTRY);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeRegistryEntry(buf, grist);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if (!(player.openContainer instanceof ContainerMiniSburbMachine))
			return;

		TileEntityMiniSburbMachine te = ((ContainerMiniSburbMachine) player.openContainer).tileEntity;

		if (te == null)
			Debug.warnf("Invalid TE in container for player %s!", player.getName());
		else
			te.selectedGrist = grist;
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}

}
