package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.Minestuck;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class MinestuckModi
{
	public static final ArrayList<Modus> modi = new ArrayList<>();

	public static final Modus stack = new ModusStack("stack");
	/*public static final Modus queue = new QueueModus("queue");
	public static final Modus tree = new TreeModus("tree");
	public static final Modus hashmap = new HashmapModus("hashmap");
	public static final Modus set = new SetModus("set");
	public static final Modus array = new ArrayModus("array");
	public static final Modus hashtable = new HashtableModus("hashtable");
	public static final Modus cyclone = new CycloneModus("cyclone");
	public static final Modus wildMagic = new WildMagicModus("wildMagic");
	public static final Modus capitalist = new CapitalistModus("capitalist");
	public static final Modus modUs = new CommunistModus("modUs");
	public static final Modus deck = new DeckModus("deck");
	public static final Modus popTart = new PopTartModus("popTart");
	public static final Modus hue = new HueModus("hue");
	public static final Modus chat = new ChatModus("chat");
	public static final Modus onion = new OnionModus("onion");
	public static final Modus slime = new SlimeModus("slime");
	public static final Modus scratchAndSniff = new ScratchAndSniffModus("scratchAndSniff");
	public static final Modus eightBall = new EightBallModus("eightBall");
	public static final Modus juju = new JujuModus("juju");
	public static final Modus alchemodus = new AlchemyModus("alchemodus");
	public static final Modus operandi = new OperandiModus("operandi");
	public static final Modus weight = new WeightModus("weight");
	public static final Modus book = new BookModus("book");
	public static final Modus energy = new EnergyModus("energy");
	public static final Modus chasity = new ChasityModus("chasity");
	public static final Modus monster = new MonsterModus("monster");
	public static final Modus wallet = new WalletModus("wallet");
	public static final Modus crystalBall = new CrystalBallModus("crystalBall");
	public static final Modus sacrifice = new SacrificeModus("sacrifice");*/

	/*
	public static final Modus ouija = new OuijaModus("ouija");
	public static final Modus cipher = new CipherModus("cipher");
	public static final Modus memory = new MemoryModus("memory");
	public static final Modus puzzle = new MemoryModus("puzzle");
	*/

	@SubscribeEvent
	public static void registerModi(RegistryEvent.Register<Modus> event)
	{
		IForgeRegistry<Modus> registry = event.getRegistry();
		for (Modus modus : modi)
			modus.register(registry);
	}
}
