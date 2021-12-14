package com.mraof.minestuck.util;

import com.mraof.minestuck.Minestuck;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class MinestuckSoundHandler
{
	public static final MinestuckSoundHandler instance = new MinestuckSoundHandler();
	
	public static SoundEvent soundEmissaryOfDance;
	public static SoundEvent soundDanceStabDance;
	public static SoundEvent soundRetroBattleTheme;
	public static SoundEvent soundWarhorn;
	public static SoundEvent soundWhispers;
	public static SoundEvent soundScreech;
	public static SoundEvent soundUpcheladder;
	public static SoundEvent soundNakagatorAmbient;
	public static SoundEvent soundNakagatorHurt;
	public static SoundEvent soundNakagatorDeath;
	public static SoundEvent soundSalamanderAmbient;
	public static SoundEvent soundSalamanderHurt;
	public static SoundEvent soundSalamanderDeath;
	public static SoundEvent soundIguanaAmbient;
	public static SoundEvent soundIguanaHurt;
	public static SoundEvent soundIguanaDeath;
	public static SoundEvent soundTurtleHurt;
	public static SoundEvent soundTurtleDeath;
	public static SoundEvent soundFrogAmbient;
	public static SoundEvent soundFrogHurt;
	public static SoundEvent soundFrogDeath;
	public static SoundEvent soundFrogGold;
	public static SoundEvent soundImpAmbient;
	public static SoundEvent soundImpHurt;
	public static SoundEvent soundImpDeath;
	public static SoundEvent soundOgreAmbient;
	public static SoundEvent soundOgreHurt;
	public static SoundEvent soundOgreDeath;
	public static SoundEvent soundBasiliskAmbient;
	public static SoundEvent soundBasiliskHurt;
	public static SoundEvent soundBasiliskDeath;
	public static SoundEvent soundLichAmbient;
	public static SoundEvent soundLichHurt;
	public static SoundEvent soundLichDeath;
	public static SoundEvent soundGiclopsAmbient;
	public static SoundEvent soundGiclopsHurt;
	public static SoundEvent soundGiclopsDeath;

	public static SoundEvent homeRunBat = new SoundEvent(new ResourceLocation(Minestuck.MODID, "item.home_run_bat"));
	public static SoundEvent bada = new SoundEvent(new ResourceLocation(Minestuck.MODID, "item.bada"));
	public static SoundEvent shieldParry = new SoundEvent(new ResourceLocation(Minestuck.MODID, "item.shield_parry"));
	public static SoundEvent shock = new SoundEvent(new ResourceLocation(Minestuck.MODID, "item.shock"));
	public static SoundEvent whipCrack = new SoundEvent(new ResourceLocation(Minestuck.MODID, "item.whip_crack"));
	public static SoundEvent whipCrock = new SoundEvent(new ResourceLocation(Minestuck.MODID, "item.whip_crock"));
	public static SoundEvent gasterBlasterCharge = new SoundEvent(new ResourceLocation(Minestuck.MODID, "item.gaster_blaster.charge"));
	public static SoundEvent gasterBlasterRelease = new SoundEvent(new ResourceLocation(Minestuck.MODID, "item.gaster_blaster.release"));
	
	public static void initSound()
	{
		//Records
		ResourceLocation soundLocation = new ResourceLocation("minestuck", "record.emissary");
		soundEmissaryOfDance = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck", "record.danceStab");
		soundDanceStabDance = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck","record.retroBattle");
		soundRetroBattleTheme = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		
		//Misc.
		soundLocation = new ResourceLocation("minestuck", "warhorn");
		soundWarhorn = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck", "whispers");
		soundWhispers = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck", "screech");
		soundScreech = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck", "upcheladder");
		soundUpcheladder = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		
		//Nakagators
		soundLocation = new ResourceLocation("minestuck","nakagatorAmbient");
		soundNakagatorAmbient = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck","nakagatorHurt");
		soundNakagatorHurt = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck","nakagatorDeath");
		soundNakagatorDeath = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		//Iguanas
		soundLocation = new ResourceLocation("minestuck","iguanaAmbient");
		soundIguanaAmbient = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck","iguanaHurt");
		soundIguanaHurt = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck","iguanaDeath");
		soundIguanaDeath = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		//Salamanders
		soundLocation = new ResourceLocation("minestuck","salamanderAmbient");
		soundSalamanderAmbient = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck","salamanderHurt");
		soundSalamanderHurt = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck","salamanderDeath");
		soundSalamanderDeath = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		//Turtles
		soundLocation = new ResourceLocation("minestuck","turtleHurt");
		soundTurtleHurt = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck","turtleDeath");
		soundTurtleDeath = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		
		//Frogs
		soundLocation = new ResourceLocation("minestuck","frogAmbient");
		soundFrogAmbient = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck","frogHurt");
		soundFrogHurt = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck","frogDeath");
		soundFrogDeath = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck","frogGold");
		soundFrogGold = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		
		//Imps
		soundLocation = new ResourceLocation("minestuck", "impAmbient");
		soundImpAmbient = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck", "impHurt");
		soundImpHurt = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck", "impDeath");
		soundImpDeath = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		//Ogres
		soundLocation = new ResourceLocation("minestuck", "ogreAmbient");
		soundOgreAmbient = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck", "ogreHurt");
		soundOgreHurt = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck", "ogreDeath");
		soundOgreDeath = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		//Basilisks
		soundLocation = new ResourceLocation("minestuck", "basiliskAmbient");
		soundBasiliskAmbient = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck", "basiliskHurt");
		soundBasiliskHurt = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck", "basiliskDeath");
		soundBasiliskDeath = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		//Liches
		soundLocation = new ResourceLocation("minestuck", "lichAmbient");
		soundLichAmbient = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck", "lichHurt");
		soundLichHurt = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck", "lichDeath");
		soundLichDeath = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		//Giclops
		soundLocation = new ResourceLocation("minestuck", "giclopsAmbient");
		soundGiclopsAmbient = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck", "giclopsHurt");
		soundGiclopsHurt = new SoundEvent(soundLocation).setRegistryName(soundLocation);
		soundLocation = new ResourceLocation("minestuck", "giclopsDeath");
		soundGiclopsDeath = new SoundEvent(soundLocation).setRegistryName(soundLocation);
	}
	
	@SubscribeEvent
	public void registerSound(RegistryEvent.Register<SoundEvent> event)
	{
		IForgeRegistry<SoundEvent> registry = event.getRegistry();

		registry.register(soundEmissaryOfDance);
		registry.register(soundDanceStabDance);
		registry.register(soundRetroBattleTheme);
		registry.register(soundWarhorn);
		registry.register(soundWhispers);
		registry.register(soundScreech);
		registry.register(soundUpcheladder);
		
		registry.register(soundNakagatorAmbient);
		registry.register(soundNakagatorHurt);
		registry.register(soundNakagatorDeath);
		registry.register(soundIguanaAmbient);
		registry.register(soundIguanaHurt);
		registry.register(soundIguanaDeath);
		registry.register(soundSalamanderAmbient);
		registry.register(soundSalamanderHurt);
		registry.register(soundSalamanderDeath);
		registry.register(soundTurtleHurt);
		registry.register(soundTurtleDeath);
		
		registry.register(soundFrogAmbient);
		registry.register(soundFrogHurt);
		registry.register(soundFrogDeath);
		registry.register(soundFrogGold);

		registry.register(soundImpAmbient);
		registry.register(soundImpHurt);
		registry.register(soundImpDeath);
		registry.register(soundOgreAmbient);
		registry.register(soundOgreHurt);
		registry.register(soundOgreDeath);
		registry.register(soundBasiliskAmbient);
		registry.register(soundBasiliskHurt);
		registry.register(soundBasiliskDeath);
		registry.register(soundLichAmbient);
		registry.register(soundLichHurt);
		registry.register(soundLichDeath);
		registry.register(soundGiclopsAmbient);
		registry.register(soundGiclopsHurt);
		registry.register(soundGiclopsDeath);

		registry.register(homeRunBat.setRegistryName("item.home_run_bat"));
		registry.register(bada.setRegistryName("item.bada1"));
		registry.register(shieldParry.setRegistryName("item.shield_parry"));
		registry.register(shock.setRegistryName("item.shock"));
		registry.register(whipCrack.setRegistryName("item.whip_crack"));
		registry.register(whipCrock.setRegistryName("item.whip_crock"));
		registry.register(gasterBlasterCharge.setRegistryName("item.gaster_blaster.charge"));
		registry.register(gasterBlasterRelease.setRegistryName("item.gaster_blaster.release"));
	}
}
