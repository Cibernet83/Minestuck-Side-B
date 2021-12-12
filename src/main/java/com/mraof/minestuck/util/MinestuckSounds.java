package com.mraof.minestuck.util;

import com.mraof.minestuck.Minestuck;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class MinestuckSounds
{
	public static SoundEvent chatModusPing = new SoundEvent(new ResourceLocation(Minestuck.MODID, "chat_ping"));
	public static SoundEvent cruxtruderGelFill = new SoundEvent(new ResourceLocation(Minestuck.MODID, "cruxtruder_fill_gel"));
	public static SoundEvent eightBallThrow = new SoundEvent(new ResourceLocation(Minestuck.MODID, "eight_ball_throw"));
	public static SoundEvent operandiTaskComplete = new SoundEvent(new ResourceLocation(Minestuck.MODID, "operandi_task_complete"));
	public static SoundEvent chasityLock = new SoundEvent(new ResourceLocation(Minestuck.MODID, "chasity_lock"));
	public static SoundEvent chasityRattle = new SoundEvent(new ResourceLocation(Minestuck.MODID, "chasity_rattle"));
	public static SoundEvent chasityUnlock = new SoundEvent(new ResourceLocation(Minestuck.MODID, "chasity_unlock"));
	
	@SubscribeEvent
	public static void registerSound(RegistryEvent.Register<SoundEvent> event)
	{
		IForgeRegistry<SoundEvent> registry = event.getRegistry();

		registry.register(chatModusPing.setRegistryName("chat_ping"));
		registry.register(cruxtruderGelFill.setRegistryName("cruxtruder_fill_gel"));
		registry.register(eightBallThrow.setRegistryName("eight_ball_throw"));
		registry.register(operandiTaskComplete.setRegistryName("operandi_task_complete"));
		registry.register(chasityLock.setRegistryName("chasity_lock"));
		registry.register(chasityRattle.setRegistryName("chasity_rattle"));
		registry.register(chasityUnlock.setRegistryName("chasity_unlock"));
	}
	
}
