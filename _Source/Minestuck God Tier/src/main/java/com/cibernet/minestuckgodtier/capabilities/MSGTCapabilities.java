package com.cibernet.minestuckgodtier.capabilities;

import com.cibernet.minestuckgodtier.MinestuckGodTier;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.capabilities.api.IGameData;
import com.cibernet.minestuckgodtier.capabilities.api.IGodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.api.IGodTierData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MSGTCapabilities
{
	@CapabilityInject(IBadgeEffects.class)
	public static final Capability<IBadgeEffects> BADGE_EFFECTS = null;

	@CapabilityInject(IGodKeyStates.class)
	public static final Capability<IGodKeyStates> GOD_KEY_STATES = null;

	@CapabilityInject(IGodTierData.class)
	public static final Capability<IGodTierData> GOD_TIER_DATA = null;

	@CapabilityInject(IGameData.class)
	public static final Capability<IGameData> GAME_DATA = null;

	public static void registerCapabilities()
	{
		MinecraftForge.EVENT_BUS.register(MSGTCapabilities.class);
		MinecraftForge.EVENT_BUS.register(BadgeEffects.class);
		MinecraftForge.EVENT_BUS.register(GodKeyStates.class);
		MinecraftForge.EVENT_BUS.register(GodTierData.class);

		CapabilityManager.INSTANCE.register(IBadgeEffects.class, new MSGTCapabilityProvider.Storage<>(), BadgeEffects::new);
		CapabilityManager.INSTANCE.register(IGodKeyStates.class, new MSGTCapabilityProvider.Storage<>(), GodKeyStates::new);
		CapabilityManager.INSTANCE.register(IGodTierData.class, new MSGTCapabilityProvider.Storage<>(), GodTierData::new);
		CapabilityManager.INSTANCE.register(IGameData.class, new MSGTCapabilityProvider.Storage<>(), GameData::new);
	}

	@SubscribeEvent
	public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event)
	{
		if (event.getObject() instanceof EntityLivingBase)
		{
			event.addCapability(new ResourceLocation(MinestuckGodTier.MODID, "badge_effects"), new MSGTCapabilityProvider<>(BADGE_EFFECTS, (EntityLivingBase) event.getObject()));
		}

		if (event.getObject() instanceof EntityPlayer)
		{
			event.addCapability(new ResourceLocation(MinestuckGodTier.MODID, "god_key_states"), new MSGTCapabilityProvider<>(GOD_KEY_STATES, (EntityPlayer) event.getObject()));
			event.addCapability(new ResourceLocation(MinestuckGodTier.MODID, "god_tier_data"), new MSGTCapabilityProvider<>(GOD_TIER_DATA, (EntityPlayer) event.getObject()));
		}
	}

	@SubscribeEvent
	public static void onAttachCapabilitiesWorld(AttachCapabilitiesEvent<World> event)
	{
		if (event.getObject().provider.getDimension() == 0) // FIXME: This prospect is inherently flawed as the client cannot access DIM0 if they aren't in it
		{
			event.addCapability(new ResourceLocation(MinestuckGodTier.MODID, "game_data"), new MSGTCapabilityProvider<>(GAME_DATA, event.getObject()));
		}
	}
}
