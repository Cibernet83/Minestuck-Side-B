package com.mraof.minestuck.event.handler;

import com.mraof.minestuck.potions.*;
import net.minecraftforge.common.MinecraftForge;

public class PotionEventHandler
{
	public static void registerPotionHandlers()
	{
		MinecraftForge.EVENT_BUS.register(PotionConceal.class);
		MinecraftForge.EVENT_BUS.register(PotionMouseSensitivityAdjusterBase.class);
		MinecraftForge.EVENT_BUS.register(PotionTimeStop.class);
		MinecraftForge.EVENT_BUS.register(PotionConfusion.class);
		MinecraftForge.EVENT_BUS.register(PotionComeback.class);
		MinecraftForge.EVENT_BUS.register(PotionDecay.class);
	}
}
