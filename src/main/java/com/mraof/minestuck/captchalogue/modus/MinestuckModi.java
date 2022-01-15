package com.mraof.minestuck.captchalogue.modus;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.item.ItemModus;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class MinestuckModi
{
	public static final ArrayList<Modus> modi = new ArrayList<>();

	public static final Modus captchaCard = new ModusCaptchaCard("captchaCard");

	//beta
	public static final Modus stack = new ModusStack("stack");
	public static final Modus queue = new ModusQueue("queue");
	//public static final Modus tree = new ModusTree("tree");
	//public static final Modus hashmap = new ModusHashmap("hashmap");
	public static final Modus hashtable = new ModusArray("hashtable") //TODO just registering this so that we can have pretty captcharoid cards
	{
		@Override
		public ItemModus makeItem()
		{
			return null;
		}
	};
	public static final Modus array = new ModusArray("array");

	//alpha
	//public static final Modus recipe = new ModusMemory("recipe");
	//public static final Modus puzzle = new ModusMemory("puzzle");
	//public static final Modus msgInABottle = new ModusMemory("messageInABottle");
	//public static final Modus techHop = new ModusMemory("techHop");
	//public static final Modus fibonacci = new ModusFibonacci("fibonacci"); //not planned but i'll just leave it here just in case

	//alternian
	//public static final Modus encryption = new ModusEncryption("encryption");
	//public static final Modus ouija = new ModusOuija("ouija");
	//public static final Modus miracle = new ModusMiracle("miracle"); //same as fib, gl trying to implement it if you're feeling it
	//public static final Modus chastity = new ModusChastity("chastity");
	public static final Modus eightBall = new ModusEightBall("eightBall");
	//public static final Modus scratchAndSniff = new ModusEncryption("scratchAndSniff");

	//board game night
	//public static final Modus memory = new ModusMemory("memory");
	//public static final Modus connectFour = new ModusMemory("connectFour");
	//public static final Modus boggle = new ModusMemory("boggle");
	//public static final Modus battleship = new ModusMemory("battleship");

	//remaining canon
	//public static final Modus wallet = new ModusJuju("wallet");
	//public static final Modus juju = new ModusJuju("juju");
	//public static final Modus mspa = new ModusMSPA("mspa"); //yeah, not really planned, but still

	//public static final Modus set = new ModusSet("set");

	//side-b
	public static final Modus cyclone = new ModusCyclone("cyclone");
	//public static final Modus weight = new ModusWeight("weight");
	//public static final Modus wildMagic = new ModusWildMagic("wildMagic");
	//public static final Modus book = new ModusBook("book");
	//public static final Modus deck = new ModusDeck("deck");
	//public static final Modus energy = new ModusEnergy("energy");
	//public static final Modus chat = new ModusChat("chat");
	//public static final Modus hue = new ModusHue("hue");
	//public static final Modus onion = new ModusOnion("onion");
	public static final Modus popTart = new ModusPopTart("popTart");
	//public static final Modus slime = new ModusSlime("slime");
	//public static final Modus monster = new ModusMonster("monster");
	//public static final Modus sacrifice = new ModusSacrifice("sacrifice");
	//public static final Modus operandi = new ModusOperandi("operandi");
	//public static final Modus capitalist = new ModusCapitalist("capitalist");
	//public static final Modus modUs = new ModusCommunist("modUs");
	//public static final Modus alchemodus = new ModusAlchemy("alchemodus");

	//public static final Modus cipher = new ModusCipher("cipher");
	//public static final Modus bundle = new ModusBundle("bundle");
	//public static final Modus cake = new ModusCake("cake");
	//public static final Modus frost = new ModusFrost("frost");
	//public static final Modus fetch = new ModusFetch("fetch");


	@SubscribeEvent
	public static void registerModi(RegistryEvent.Register<Modus> event)
	{
		IForgeRegistry<Modus> registry = event.getRegistry();
		for (Modus modus : modi)
			modus.register(registry);
	}
}
