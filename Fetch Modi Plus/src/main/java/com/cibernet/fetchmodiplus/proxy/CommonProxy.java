package com.cibernet.fetchmodiplus.proxy;

import com.cibernet.fetchmodiplus.FetchModiPlus;
import com.cibernet.fetchmodiplus.event.FMPCommonEventHandler;
import com.cibernet.fetchmodiplus.event.FMPSaveHandler;
import com.cibernet.fetchmodiplus.network.FMPChannelHandler;
import com.cibernet.fetchmodiplus.registries.*;
import com.mraof.minestuck.world.storage.MinestuckSaveHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy
{
	public void pre()
	{
		MinecraftForge.EVENT_BUS.register(FMPItems.class);
		MinecraftForge.EVENT_BUS.register(FMPBlocks.class);
		MinecraftForge.EVENT_BUS.register(FMPSounds.class);
		MinecraftForge.EVENT_BUS.register(FMPCommonEventHandler.class);
		
		FMPEntities.registerEntities();
	}
	
	public void init()
	{
		FMPModi.register();
		FMPChannelHandler.setupChannel();
		
		FMPAlchemy.registerGristConversionRecipes();
		FMPAlchemy.registerCombinationRecipes();
		
		MinecraftForge.EVENT_BUS.register(new FMPSaveHandler());
	}
	
	public void post()
	{
	
	}
}
