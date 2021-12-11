package com.cibernet.minestuckgodtier.proxy;

import com.cibernet.minestuckgodtier.MinestuckGodTier;
import com.cibernet.minestuckgodtier.api.MSGTTrophySlotsSupport;
import com.cibernet.minestuckgodtier.badges.MSGTBadges;
import com.cibernet.minestuckgodtier.blocks.MSGTBlocks;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.client.gui.MSGTGuiHandler;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.entities.MSGTEntities;
import com.cibernet.minestuckgodtier.events.handlers.*;
import com.cibernet.minestuckgodtier.items.MSGTItems;
import com.cibernet.minestuckgodtier.network.MSGTChannelHandler;
import com.cibernet.minestuckgodtier.potions.MSGTPotions;
import com.cibernet.minestuckgodtier.util.MSGTAlchemyRecipes;
import com.cibernet.minestuckgodtier.util.MSGTPlayerData;
import com.cibernet.minestuckgodtier.world.gen.WorldGenHandler;
import com.cibernet.minestuckgodtier.world.storage.loot.MSGTLoot;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy extends Proxy
{

	@Override
	public void preInit() 
	{
		MinecraftForge.EVENT_BUS.register(MSGTItems.class);
		MinecraftForge.EVENT_BUS.register(MSGTBlocks.class);
		MinecraftForge.EVENT_BUS.register(MSGTPotions.class);
		MinecraftForge.EVENT_BUS.register(GTEventHandler.class);
		MinecraftForge.EVENT_BUS.register(KarmaEventHandler.class);
		MinecraftForge.EVENT_BUS.register(EditModeEventHandler.class);
		MinecraftForge.EVENT_BUS.register(MSGTPlayerData.class);
		MinecraftForge.EVENT_BUS.register(MSGTParticles.class);
		MinecraftForge.EVENT_BUS.register(CommonEventHandler.class);
		MinecraftForge.EVENT_BUS.register(LocalChatEventHandler.class);

		PotionEventHandler.registerPotionEvents();
		MSGTEntities.registerEntities();
		MSGTCapabilities.registerCapabilities();
		BadgeEventHandler.registerBadgeEvents();

		if(MinestuckGodTier.isTrophySlotsLoaded)
			MinecraftForge.EVENT_BUS.register(MSGTTrophySlotsSupport.class);
	}

	@Override
	public void init() 
	{
		MinecraftForge.EVENT_BUS.register(MSGTBadges.class);
		MSGTAlchemyRecipes.registerRecipes();
		MSGTItems.registerDeployList();

		NetworkRegistry.INSTANCE.registerGuiHandler(MinestuckGodTier.instance, new MSGTGuiHandler());

		MSGTChannelHandler.setupChannel();

		GameRegistry.registerWorldGenerator(new WorldGenHandler(), 0);

		MSGTLoot.registerLootClasses();
	}

}
