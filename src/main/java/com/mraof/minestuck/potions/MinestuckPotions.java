package com.mraof.minestuck.potions;

import com.mraof.minestuck.Minestuck;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class MinestuckPotions
{
	public static final ArrayList<MSPotionBase> potions = new ArrayList<>();

	public static final Potion CREATIVE_SHOCK = new PotionBuildInhibit("creativeShock", 0x993030);
	public static final Potion EARTHBOUND = new PotionFlight("earthbound", true, 0xFFCD70);
	public static final Potion SKYHBOUND = new PotionFlight("skybound", false, 0x70FFFF).setBeneficial();
	public static final Potion GOD_TIER_COMEBACK = new PotionComeback("godTierComeback", false, 0x00FF00).setBeneficial();
	public static final Potion GOD_TIER_LOCK = new MSPotionBase("godTierLock", true, 0x808080);
	public static final Potion TIME_STOP = new PotionTimeStop("timeStop", true, 0xFF2106);
	public static final Potion MIND_CONFUSION = new PotionConfusion("mentalCrash", true, 0x3DA35A);
	public static final Potion MIND_FORTITUDE = new PotionCounter("mentalFortitude", false, 458697, MIND_CONFUSION, MobEffects.BLINDNESS, MobEffects.NAUSEA);
	public static final Potion DECAY = new PotionDecay("decay", true, 0x204121);
	public static final Potion DECAYPROOF = new PotionCounter("decayproof", false, 0xFEDA82, DECAY, MobEffects.WITHER, MobEffects.POISON);
	public static final Potion VOID_CONCEAL = new PotionConceal("trueConcealment", false, 9062);
	public static final Potion RAGE_BERSERK = new PotionBerserk("berserk", false, 0x442769);
	public static final Potion BLEEDING = new PotionBleeding("bleeding", true, 0xB71015);

	@SubscribeEvent
	public static void registerEffects(RegistryEvent.Register<Potion> event)
	{
		IForgeRegistry<Potion> registry = event.getRegistry();
		for (MSPotionBase potion : potions)
			potion.register(registry);
	}
}
