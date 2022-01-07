package com.mraof.minestuck.network.message;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageConfig implements MinestuckMessage
{
	private int cardCost;
	private int alchemiterStacks;
	private boolean disableGristWidget;
	private byte treeModusSetting;
	private boolean dataChecker;
	private boolean preEntryEcheladder;

	private MessageConfig() { }

	public MessageConfig(boolean dataChecker)
	{
		this.dataChecker = dataChecker;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(MinestuckConfig.cardCost);
		buf.writeInt(MinestuckConfig.alchemiterMaxStacks);
		buf.writeBoolean(MinestuckConfig.disableGristWidget);
		buf.writeByte(MinestuckConfig.treeModusSetting);
		buf.writeBoolean(dataChecker);
		buf.writeBoolean(MinestuckConfig.preEntryRungLimit <= 0);
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		cardCost = buf.readInt();
		alchemiterStacks = buf.readInt();
		disableGristWidget = buf.readBoolean();
		treeModusSetting = buf.readByte();
		dataChecker = buf.readBoolean();
		preEntryEcheladder = buf.readBoolean();
	}

	@Override
	public void execute(EntityPlayer player)
	{
		MinestuckConfig.clientCardCost = cardCost;
		MinestuckConfig.clientAlchemiterStacks = alchemiterStacks;
		MinestuckConfig.clientDisableGristWidget = disableGristWidget;
		MinestuckConfig.clientTreeAutobalance = treeModusSetting;
		MinestuckConfig.dataCheckerAccess = dataChecker;
		MinestuckConfig.preEntryEcheladder = preEntryEcheladder;
	}

	@Override
	public Side toSide() {
		return Side.CLIENT;
	}
}
