package com.mraof.minestuck.network.message;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.editmode.DeployList;
import com.mraof.minestuck.inventory.ContainerHandler;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;

public class MessageConfigInitial implements MinestuckMessage
{
	private int overWorldEditRange;
	private int landEditRange;
	private int windowIdStart;
	private int oreMultiplier;
	private boolean giveItems;
	private boolean hardMode;
	private boolean[] deployValues;

	public MessageConfigInitial() { }

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(MinestuckConfig.overworldEditRange);
		buf.writeInt(MinestuckConfig.landEditRange);
		buf.writeInt(ContainerHandler.windowIdStart);
		buf.writeInt(MinestuckConfig.oreMultiplier);
		buf.writeBoolean(MinestuckConfig.giveItems);
		buf.writeBoolean(MinestuckConfig.hardMode);

		for(int i = 0; i < MinestuckConfig.deployConfigurations.length; i++)
			buf.writeBoolean(MinestuckConfig.deployConfigurations[i]);
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		overWorldEditRange = buf.readInt();
		landEditRange = buf.readInt();
		windowIdStart = buf.readInt();
		oreMultiplier = buf.readInt();
		giveItems = buf.readBoolean();
		hardMode = buf.readBoolean();

		deployValues = new boolean[MinestuckConfig.deployConfigurations.length];
		for(int i = 0; i < deployValues.length; i++)
			deployValues[i] = buf.readBoolean();
	}

	@Override
	public void execute(EntityPlayer player)
	{
		MinestuckConfig.clientOverworldEditRange = overWorldEditRange;
		MinestuckConfig.clientLandEditRange = landEditRange;
		MinestuckConfig.clientGiveItems = giveItems;
		ContainerHandler.clientWindowIdStart = windowIdStart;
		MinestuckConfig.clientHardMode = hardMode;

		if(!Minestuck.isServerRunning)
		{
			DeployList.applyConfigValues(deployValues);
		}

		if(MinestuckConfig.oreMultiplier != oreMultiplier)
			player.sendMessage(new TextComponentString("[Minestuck] Ore multiplier config doesn't match the server value. (server: "+oreMultiplier+", you: "+MinestuckConfig.oreMultiplier+") Grist costs will likely not match their actual cost!").setStyle(new Style().setColor(TextFormatting.YELLOW)));
	}

	@Override
	public Side toSide() {
		return Side.CLIENT;
	}
}
