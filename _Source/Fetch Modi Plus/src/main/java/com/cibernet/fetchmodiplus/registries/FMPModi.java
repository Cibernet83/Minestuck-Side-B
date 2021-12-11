package com.cibernet.fetchmodiplus.registries;

import com.cibernet.fetchmodiplus.FetchModiPlus;
import com.cibernet.fetchmodiplus.captchalogue.*;
import com.mraof.minestuck.inventory.captchalouge.CaptchaDeckHandler;
import com.mraof.minestuck.inventory.captchalouge.Modus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class FMPModi
{
	public static void register()
	{
		registerModus("cyclone_modus", CycloneModus.class, FMPItems.cycloneModus);
		registerModus("wild_magic_modus", WildMagicModus.class, FMPItems.wildMagicModus);
		registerModus("capitalist_modus", CapitalistModus.class, FMPItems.capitalistModus);
		registerModus("mod_us", CommunistModus.class, FMPItems.modUs);
		registerModus("deck_modus", DeckModus.class, FMPItems.deckModus);
		registerModus("pop_tart_modus", PopTartModus.class, FMPItems.popTartModus);
		registerModus("hue_modus", HueModus.class, FMPItems.hueModus);
		registerModus("hue_stack_modus", HueStackModus.class, FMPItems.hueStackModus);
		registerModus("chat_modus", ChatModus.class, FMPItems.chatModus);
		registerModus("onion_modus", OnionModus.class, FMPItems.onionModus);
		registerModus("slime_modus", SlimeModus.class, FMPItems.slimeModus);
		registerModus("scratch_and_sniff_modus", ScratchAndSniffModus.class, FMPItems.scratchAndSniffModus);
		registerModus("eight_ball_modus", EightBallModus.class, FMPItems.eightBallModus);
		registerModus("juju_modus", JujuModus.class, FMPItems.jujuModus);
		registerModus("alchemodus", AlchemyModus.class, FMPItems.alcheModus);
		registerModus("operandi_modus", OperandiModus.class, FMPItems.operandiModus);
		registerModus("weight_modus", WeightModus.class, FMPItems.weightModus);
		registerModus("book_modus", BookModus.class, FMPItems.bookModus);
		registerModus("energy_modus", EnergyModus.class, FMPItems.energyModus);
		registerModus("chasity_modus", ChasityModus.class, FMPItems.chasityModus);


		registerModus("array_modus", ArrayModus.class, FMPItems.arrayModus);
		registerModus("monster_modus", MonsterModus.class, FMPItems.monsterModus);
		registerModus("wallet_modus", WalletModus.class, FMPItems.walletModus);
		registerModus("crystal_ball_modus", CrystalBallModus.class, FMPItems.crystalBallModus);

		/*
		registerModus("ouija_modus", OuijaModus.class, FMPItems.ouijaModus);
		registerModus("cipher_modus", CipherModus.class, FMPItems.cipherModus);
		registerModus("memory_modus", MemoryModus.class, FMPItems.memoryModus);
		*/

		registerModus("hashchat_modus", HashchatModus.class, FMPItems.hashchatModus);
		registerModus("sacrifice_modus", SacrificeModus.class, FMPItems.sacrificeModus);
	}
	
	private static void registerModus(String name, Class<? extends Modus> clazz, ItemStack stack)
	{
		CaptchaDeckHandler.registerModusType(new ResourceLocation(FetchModiPlus.MODID,name), clazz, stack);
	}
	
	private static void registerModus(String name, Class<? extends Modus> clazz, Item item)
	{
		registerModus(name, clazz, new ItemStack(item));
	}
}
