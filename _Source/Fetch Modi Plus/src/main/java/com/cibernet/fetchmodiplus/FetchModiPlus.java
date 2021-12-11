package com.cibernet.fetchmodiplus;

import com.cibernet.fetchmodiplus.proxy.CommonProxy;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = FetchModiPlus.MODID, name = FetchModiPlus.NAME, version = FetchModiPlus.VERSION,
		dependencies = "required-after:minestuck@[1.2.300,);required-after:jei@[4.9.1.168,);")
public class FetchModiPlus
{
	public static final String MODID = "fetchmodiplus";
	public static final String NAME = "Fetch Modi Plus";
	public static final String VERSION = "1.1.2";
	
	private static Logger logger;

	public static boolean isMSULoaded;
	public static boolean isMSGTLoaded;
	public static boolean isMekanismLoaded;
	public static boolean isCyclicLoaded;
	public static boolean isBOPLoaded;
	public static boolean isChiselLoaded;
	public static boolean isVampirismLoaded;
	public static boolean isMysticalWorldLoaded;
	public static boolean isIndustrialForegoingLoaded;

	@Mod.Instance(MODID)
	public static FetchModiPlus instance;
	
	@SidedProxy(
			clientSide = "com.cibernet."+MODID+".proxy.ClientProxy",
			serverSide = "com.cibernet."+MODID+".proxy.CommonProxy"
	)
	public static CommonProxy proxy;
	
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();

		isMSULoaded = Loader.isModLoaded("minestuckuniverse");
		isMSGTLoaded = Loader.isModLoaded("minestuckgodtier");
		isMekanismLoaded = Loader.isModLoaded("mekanism");
		isCyclicLoaded = Loader.isModLoaded("cyclicmagic");
		isChiselLoaded = Loader.isModLoaded("chisel");
		isBOPLoaded = Loader.isModLoaded("biomesoplenty");
		isVampirismLoaded = Loader.isModLoaded("vampirism");
		isMysticalWorldLoaded = Loader.isModLoaded("mysticalworld");
		isIndustrialForegoingLoaded = Loader.isModLoaded("industrialforegoing");
		proxy.pre();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.post();
	}
}
