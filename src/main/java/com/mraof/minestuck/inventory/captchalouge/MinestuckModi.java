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
	public static final Modus array = new ModusArray("array");
	/*public static final Modus queue = new ModusQueue("queue");
	public static final Modus tree = new ModusTree("tree");
	public static final Modus hashmap = new ModusHashmap("hashmap");
	public static final Modus set = new ModusSet("set");
	public static final Modus hashtable = new ModusHashtable("hashtable");
	public static final Modus cyclone = new ModusCyclone("cyclone");
	public static final Modus wildMagic = new ModusWildMagic("wildMagic");
	public static final Modus capitalist = new ModusCapitalist("capitalist");
	public static final Modus modUs = new ModusCommunist("modUs");
	public static final Modus deck = new ModusDeck("deck");
	public static final Modus popTart = new ModusPopTart("popTart");
	public static final Modus hue = new ModusHue("hue");
	public static final Modus chat = new ModusChat("chat");
	public static final Modus onion = new ModusOnion("onion");
	public static final Modus slime = new ModusSlime("slime");
	public static final Modus scratchAndSniff = new ModusScratchAndSniff("scratchAndSniff");
	public static final Modus eightBall = new ModusEightBall("eightBall");
	public static final Modus juju = new ModusJuju("juju");
	public static final Modus alchemodus = new ModusAlchemy("alchemodus");
	public static final Modus operandi = new ModusOperandi("operandi");
	public static final Modus weight = new ModusWeight("weight");
	public static final Modus book = new ModusBook("book");
	public static final Modus energy = new ModusEnergy("energy");
	public static final Modus chasity = new ModusChasity("chasity");
	public static final Modus monster = new ModusMonster("monster");
	public static final Modus wallet = new ModusWallet("wallet");
	public static final Modus crystalBall = new ModusCrystalBall("crystalBall");
	public static final Modus sacrifice = new ModusSacrifice("sacrifice");*/

	/*
	public static final Modus ouija = new ModusOuija("ouija");
	public static final Modus cipher = new ModusCipher("cipher");
	public static final Modus memory = new ModusMemory("memory");
	public static final Modus puzzle = new ModusMemory("puzzle");
	*/

	@SubscribeEvent
	public static void registerModi(RegistryEvent.Register<Modus> event)
	{
		IForgeRegistry<Modus> registry = event.getRegistry();
		for (Modus modus : modi)
			modus.register(registry);
	}
}
