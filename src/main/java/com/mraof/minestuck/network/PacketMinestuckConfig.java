package com.mraof.minestuck.network;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.editmode.DeployList;
import com.mraof.minestuck.inventory.ContainerHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketMinestuckConfig extends MinestuckPacket
{
	
	private boolean mode;

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
	
	@Override
	public void generatePacket(Object... dat)
	{
		boolean mode = (Boolean) dat[0];
		data.writeBoolean(mode);
		if(mode)	//Values that shouldn't be changed while the game is running, and should therefore only be sent once
		{
			data.writeInt(MinestuckConfig.overworldEditRange);
			data.writeInt(MinestuckConfig.landEditRange);
			data.writeInt(ContainerHandler.windowIdStart);
			data.writeInt(MinestuckConfig.oreMultiplier);
			data.writeBoolean(MinestuckConfig.giveItems);
			data.writeBoolean(MinestuckConfig.hardMode);
			
			for(int i = 0; i < MinestuckConfig.deployConfigurations.length; i++)
				data.writeBoolean(MinestuckConfig.deployConfigurations[i]);
		} else
		{
			data.writeInt(MinestuckConfig.cardCost);
			data.writeInt(MinestuckConfig.alchemiterMaxStacks);
			data.writeBoolean(MinestuckConfig.disableGristWidget);
			data.writeByte(MinestuckConfig.treeModusSetting);
			data.writeBoolean((Boolean) dat[1]);
			data.writeBoolean(MinestuckConfig.preEntryRungLimit <= 0);
		}
		

	}
	
	@Override
	public void consumePacket(ByteBuf data)
	{
		mode = data.readBoolean();
		
		if(mode)
		{
			overWorldEditRange = data.readInt();
			landEditRange = data.readInt();
			windowIdStart = data.readInt();
			oreMultiplier = data.readInt();
			giveItems = data.readBoolean();
			hardMode = data.readBoolean();
			
			deployValues = new boolean[MinestuckConfig.deployConfigurations.length];
			for(int i = 0; i < deployValues.length; i++)
				deployValues[i] = data.readBoolean();
		} else
		{
			cardCost = data.readInt();
			alchemiterStacks = data.readInt();
			disableGristWidget = data.readBoolean();
			treeModusSetting = data.readByte();
			dataChecker = data.readBoolean();
			preEntryEcheladder = data.readBoolean();
		}
		

	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(mode)
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
	public EnumSet<Side> getSenderSide() {
		return EnumSet.of(Side.SERVER);
	}

}
