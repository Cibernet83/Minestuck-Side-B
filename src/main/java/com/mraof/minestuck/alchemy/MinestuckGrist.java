package com.mraof.minestuck.alchemy;

import com.mraof.minestuck.Minestuck;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class MinestuckGrist
{
	public static final ArrayList<Grist> grists = new ArrayList<>();

	public static final Grist amber = new Grist("amber", 0.5F, 1.5F);
	public static final Grist amethyst = new Grist("amethyst", 0.3F, 3);
	public static final Grist artifact = new Grist("artifact", 0.1F, 1);
	public static final Grist build = new Grist("build", 0.0F, 1);
	public static final Grist caulk = new Grist("caulk", 0.5F, 1.5F);
	public static final Grist chalk = new Grist("chalk", 0.5F, 1.5F);
	public static final Grist cobalt = new Grist("cobalt", 0.4F, 2);
	public static final Grist diamond = new Grist("diamond", 0.2F, 5);
	public static final Grist garnet = new Grist("garnet", 0.3F, 3);
	public static final Grist gold = new Grist("gold", 0.2F, 5);
	public static final Grist iodine = new Grist("iodine", 0.5F, 1.5F);
	public static final Grist marble = new Grist("marble", 0.4F, 2);
	public static final Grist mercury = new Grist("mercury", 0.4F, 2);
	public static final Grist quartz = new Grist("quartz", 0.4F, 2);
	public static final Grist ruby = new Grist("ruby", 0.3F, 3);
	public static final Grist rust = new Grist("rust", 0.3F, 3);
	public static final Grist shale = new Grist("shale", 0.5F, 1.5F);
	public static final Grist sulfur = new Grist("sulfur", 0.4F, 2);
	public static final Grist tar = new Grist("tar", 0.5F, 1.5F);
	public static final Grist uranium = new Grist("uranium", 0.2F, 5);
	public static final Grist zillium = new Grist("zillium", 0.0F, 10);

	//Magic Grist (Thaum, Botania, etc.)
	public static final Grist vis = new Grist("vis", 0F, 5.0F)
	{
		@Override
		public void register(IForgeRegistry<Grist> registry)
		{
			if (Minestuck.isThaumLoaded)
				super.register(registry);
		}
	};
	public static final Grist mana = new Grist("mana", 0F, 5.0F)
	{
		@Override
		public void register(IForgeRegistry<Grist> registry)
		{
			if (Minestuck.isBotaniaLoaded)
				super.register(registry);
		}
	};

	@SubscribeEvent
	public static void registerGrists(RegistryEvent.Register<Grist> event)
	{
		IForgeRegistry<Grist> registry = event.getRegistry();
		for (Grist grist : grists)
			grist.register(registry);
	}
}
