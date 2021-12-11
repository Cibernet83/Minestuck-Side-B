package com.cibernet.minestuckgodtier;

import com.cibernet.minestuckgodtier.commands.GlobalSayCommand;
import com.cibernet.minestuckgodtier.commands.MSGTCommand;
import com.cibernet.minestuckgodtier.proxy.ClientProxy;
import com.cibernet.minestuckgodtier.proxy.CommonProxy;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = MinestuckGodTier.MODID, name = MinestuckGodTier.NAME, version = MinestuckGodTier.VERSION, dependencies = "required-after:minestuck@[1.4.301,);required-after:minestuckuniverse@[1.3.0,);")
public class MinestuckGodTier
{
    public static final String MODID = "minestuckgodtier";
    public static final String NAME = "Minestuck God Tier";
    public static final String VERSION = "@VERSION@";
    
    public static boolean isClientRunning;
    
    @Mod.Instance(MODID)
    public static MinestuckGodTier instance;

    public static boolean isTrophySlotsLoaded;
    public static boolean isLocksLoaded;
    public static boolean isFMPLoaded;

	public static final int SKILL_XP_THRESHOLD = 30;

	@SidedProxy
    (
    		clientSide = "com.cibernet.minestuckgodtier.proxy.ClientProxy",
            serverSide = "com.cibernet.minestuckgodtier.proxy.CommonProxy"
	)
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	isClientRunning = event.getSide().isClient();
        isTrophySlotsLoaded = Loader.isModLoaded("trophyslots");
        isLocksLoaded = Loader.isModLoaded("locks");
        isFMPLoaded = Loader.isModLoaded("fetchmodiplus");

        MSGTConfig.load(event.getSuggestedConfigurationFile(), event.getSide());

    	proxy.preInit();
    }

    @EventHandler
    public void load(FMLInitializationEvent event)
    {
    	//Register textures and renders
		if(isClientRunning)
		{
			ClientProxy.registerRenderers();
		}
    	proxy.init();
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new MSGTCommand());
        event.registerServerCommand(new GlobalSayCommand());
    }
}
