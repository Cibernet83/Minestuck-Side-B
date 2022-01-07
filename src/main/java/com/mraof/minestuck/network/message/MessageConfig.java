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

public class MessageConfig implements MinestuckMessage
{
	/**
	 * Values that shouldn't be changed while the game is running, and should therefore only be sent once
	 */
	private boolean initialConfigs;

	private int overWorldEditRange;
	private int landEditRange;
	private int cardCost;
	private int alchemiterStacks;
	private int windowIdStart;
	private int oreMultiplier;
	private byte treeModusSetting;

	private boolean giveItems;
	private boolean disableGristWidget;
	private boolean dataChecker;
	private boolean preEntryEcheladder;
	private boolean hardMode;
	private boolean[] deployValues;

	private MessageConfig() { }

	public MessageConfig(boolean initialConfigs, boolean dataChecker)
	{
		this.initialConfigs = initialConfigs;
		this.dataChecker = dataChecker;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(initialConfigs);
		if (initialConfigs)
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
		else
		{
			buf.writeInt(MinestuckConfig.cardCost);
			buf.writeInt(MinestuckConfig.alchemiterMaxStacks);
			buf.writeBoolean(MinestuckConfig.disableGristWidget);
			buf.writeByte(MinestuckConfig.treeModusSetting);
			buf.writeBoolean(dataChecker);
			buf.writeBoolean(MinestuckConfig.preEntryRungLimit <= 0);
		}
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		initialConfigs = buf.readBoolean();
		
		if(initialConfigs)
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
		} else
		{
			cardCost = buf.readInt();
			alchemiterStacks = buf.readInt();
			disableGristWidget = buf.readBoolean();
			treeModusSetting = buf.readByte();
			dataChecker = buf.readBoolean();
			preEntryEcheladder = buf.readBoolean();
		}
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(initialConfigs)
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
		} else
		{
			MinestuckConfig.clientCardCost = cardCost;
			MinestuckConfig.clientAlchemiterStacks = alchemiterStacks;
			MinestuckConfig.clientDisableGristWidget = disableGristWidget;
			MinestuckConfig.clientTreeAutobalance = treeModusSetting;
			MinestuckConfig.dataCheckerAccess = dataChecker;
			MinestuckConfig.preEntryEcheladder = preEntryEcheladder;
		}
	}

	@Override
	public Side toSide() {
		return Side.CLIENT;
	}
}
