package com.mraof.minestuck.world.biome;

import com.mraof.minestuck.Minestuck;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class MinestuckBiomes extends Biome
{

	public static Biome mediumOcean, mediumNormal, mediumRough;

	public MinestuckBiomes(BiomeProperties properties)
	{
		super(properties);
	}

	@SubscribeEvent
	public static void registerBiomes(RegistryEvent.Register<Biome> event)
	{
		mediumNormal = new MinestuckBiomes(new BiomeProperties("The Medium")).setRegistryName("medium");
		mediumOcean = new MinestuckBiomes(new BiomeProperties("The Medium (Ocean)").setBaseBiome("medium")).setRegistryName("medium_ocean");
		mediumRough = new MinestuckBiomes(new BiomeProperties("The Medium (Rough)").setBaseBiome("medium")).setRegistryName("medium_rough");
		event.getRegistry().register(mediumNormal);
		event.getRegistry().register(mediumOcean);
		event.getRegistry().register(mediumRough);
	}
}
