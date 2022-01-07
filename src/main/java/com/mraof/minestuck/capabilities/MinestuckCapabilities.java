package com.mraof.minestuck.capabilities;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.capabilities.api.*;
import com.mraof.minestuck.capabilities.caps.*;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageBeamData;
import com.mraof.minestuck.network.message.MessageStrifeData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class MinestuckCapabilities
{
	@CapabilityInject(IStrifeData.class)
	public static final Capability<IStrifeData> STRIFE_DATA = null;
	@CapabilityInject(IBeamData.class)
	public static final Capability<IBeamData> BEAM_DATA = null;
	@CapabilityInject(IGameData.class)
	public static final Capability<IGameData> GAME_DATA = null;
	@CapabilityInject(IBadgeEffects.class)
	public static final Capability<IBadgeEffects> BADGE_EFFECTS = null;
	@CapabilityInject(IGodKeyStates.class)
	public static final Capability<IGodKeyStates> GOD_KEY_STATES = null;
	@CapabilityInject(IGodTierData.class)
	public static final Capability<IGodTierData> GOD_TIER_DATA = null;
	@CapabilityInject(ISylladexData.class)
	public static final Capability<ISylladexData> SYLLADEX_DATA = null;

	public static void registerCapabilities()
	{
		CapabilityManager.INSTANCE.register(IStrifeData.class, new MinestuckCapabilityProvider.Storage<>(), StrifeData::new);
		CapabilityManager.INSTANCE.register(IBeamData.class, new MinestuckCapabilityProvider.Storage<>(), BeamData::new);
		CapabilityManager.INSTANCE.register(IGameData.class, new MinestuckCapabilityProvider.Storage<>(), GameData::new);
		CapabilityManager.INSTANCE.register(IBadgeEffects.class, new MinestuckCapabilityProvider.Storage<>(), BadgeEffects::new);
		CapabilityManager.INSTANCE.register(IGodKeyStates.class, new MinestuckCapabilityProvider.Storage<>(), GodKeyStates::new);
		CapabilityManager.INSTANCE.register(IGodTierData.class, new MinestuckCapabilityProvider.Storage<>(), GodTierData::new);
		CapabilityManager.INSTANCE.register(ISylladexData.class, new MinestuckCapabilityProvider.Storage<>(), SylladexData::new);
	}

	@SubscribeEvent
	public static void attachWorldCap(AttachCapabilitiesEvent<World> event)
	{
		event.addCapability(new ResourceLocation(Minestuck.MODID, "beam_data"), new MinestuckCapabilityProvider<>(BEAM_DATA, event.getObject()));

		if (event.getObject().provider.getDimension() == 0) // FIXME: This prospect is inherently flawed as the client cannot access DIM0 if they aren't in it
			event.addCapability(new ResourceLocation(Minestuck.MODID, "game_data"), new MinestuckCapabilityProvider<>(GAME_DATA, event.getObject()));
	}

	@SubscribeEvent
	public static void attachEntityCap(AttachCapabilitiesEvent<Entity> event)
	{
		if(event.getObject() instanceof EntityPlayer)
		{
			event.addCapability(new ResourceLocation(Minestuck.MODID, "strife_data"), new MinestuckCapabilityProvider<>(STRIFE_DATA, (EntityPlayer) event.getObject()));
			event.addCapability(new ResourceLocation(Minestuck.MODID, "god_key_states"), new MinestuckCapabilityProvider<>(GOD_KEY_STATES, (EntityPlayer) event.getObject()));
			event.addCapability(new ResourceLocation(Minestuck.MODID, "god_tier_data"), new MinestuckCapabilityProvider<>(GOD_TIER_DATA, (EntityPlayer) event.getObject()));
			event.addCapability(new ResourceLocation(Minestuck.MODID, "sylladex_data"), new MinestuckCapabilityProvider<>(SYLLADEX_DATA, (EntityPlayer) event.getObject()));
		}

		if (event.getObject() instanceof EntityLivingBase)
		{
			event.addCapability(new ResourceLocation(Minestuck.MODID, "badge_effects"), new MinestuckCapabilityProvider<>(BADGE_EFFECTS, (EntityLivingBase) event.getObject()));
		}
	}

	@SubscribeEvent
	public static void onPlayerJoinWorld(EntityJoinWorldEvent event)
	{
		if(event.getEntity() instanceof EntityPlayerMP && !(event.getEntity() instanceof FakePlayer))
		{
			IStrifeData cap = event.getEntity().getCapability(STRIFE_DATA, null);
			cap.setStrifeEnabled(true);
			MinestuckNetwork.sendTo(new MessageStrifeData((EntityLivingBase)event.getEntity()), (EntityPlayer)event.getEntity());
			MinestuckNetwork.sendTo(new MessageBeamData(event.getWorld()), (EntityPlayer)event.getEntity());
		}
	}

	@SubscribeEvent
	public static void onWorldTick(TickEvent.WorldTickEvent event)
	{
		event.world.getCapability(MinestuckCapabilities.BEAM_DATA, null).tickBeams();
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event)
	{
		if(Minecraft.getMinecraft().player != null && !Minecraft.getMinecraft().isGamePaused())
			Minecraft.getMinecraft().player.world.getCapability(MinestuckCapabilities.BEAM_DATA, null).tickBeams();
	}
}
