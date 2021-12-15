package com.mraof.minestuck.capabilities.caps;

import com.mraof.minestuck.badges.Badge;
import com.mraof.minestuck.badges.heroAspect.BadgeHeroAspect;
import com.mraof.minestuck.badges.heroAspectUtil.BadgeHeroAspectUtil;
import com.mraof.minestuck.badges.heroClass.BadgeHeroClass;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.api.IGodKeyStates;
import com.mraof.minestuck.capabilities.api.IGodTierData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class GodKeyStates implements IGodKeyStates
{
	private KeyState[] keyStates;
	private int[] keyTimes;

	public GodKeyStates()
	{
		resetKeyStates();
	}

	@Override
	public void updateKeyState(Key key, boolean pressed)
	{
		if (pressed && keyStates[key.ordinal()] != KeyState.HELD)
		{
			keyStates[key.ordinal()] = KeyState.PRESS;
			keyTimes[key.ordinal()] = 0;
		}
		else if (!pressed && keyStates[key.ordinal()] != KeyState.NONE)
			keyStates[key.ordinal()] = KeyState.RELEASED;
	}

	@Override
	public int getKeyTime(Key key)
	{
		return keyTimes[key.ordinal()];
	}

	@Override
	public KeyState getKeyState(Key key)
	{
		return keyStates[key.ordinal()];
	}

	@Override
	public void tickKeyStates()
	{
		for (int i = 0; i < keyStates.length; i++)
		{
			if(keyStates[i] == KeyState.PRESS)
				keyStates[i] = KeyState.HELD;
			else if(keyStates[i] == KeyState.RELEASED)
				keyStates[i] = KeyState.NONE;

			keyTimes[i]++;
		}
	}

	@Override
	public void resetKeyStates() {
		keyStates = new KeyState[Key.values().length];
		keyTimes  = new int[Key.values().length];

		for (int i = 0; i < keyStates.length; i++)
			keyStates[i] = KeyState.NONE;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		for (int i = 0; i < keyStates.length; i++)
		{
			keyStates[i] = KeyState.values()[nbt.getInteger(i + "State")];
			keyTimes[i] = nbt.getInteger(i + "Time");
		}
	}

	@Override
	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		for (int i = 0; i < keyStates.length; i++)
		{
			nbt.setInteger(i + "State", keyStates[i].ordinal());
			nbt.setInteger(i + "Time", keyTimes[i]);
		}
		return nbt;
	}

	public enum Key {
		CLASS,
		ASPECT,
		UTIL
	}

	public enum KeyState
	{
		PRESS,
		HELD,
		RELEASED,
		NONE
	}

	@SubscribeEvent
	public static void onWorldJoin(EntityJoinWorldEvent event)
	{
		if(event.getEntity().hasCapability(MinestuckCapabilities.GOD_KEY_STATES, null))
			event.getEntity().getCapability(MinestuckCapabilities.GOD_KEY_STATES, null).resetKeyStates();
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		if(event.player.world.isRemote || event.phase == TickEvent.Phase.END)
			return;

		IGodTierData data = event.player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null);
		List<Badge> badgeList = data.getAllBadges();

		IBadgeEffects badgeEffects = event.player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null);
		IGodKeyStates keyStates = event.player.getCapability(MinestuckCapabilities.GOD_KEY_STATES, null);

		if(!event.player.isSpectator())
			for(Badge badge : badgeList)
			{
				boolean isActive = false;
				if(data.isBadgeActive(badge))
					if(badge instanceof BadgeHeroClass)
						isActive = ((BadgeHeroClass) badge).onBadgeTick(event.player.world, event.player, badgeEffects, keyStates.getKeyState(Key.CLASS), keyStates.getKeyTime(Key.CLASS));
					else if(badge instanceof BadgeHeroAspect)
						isActive = ((BadgeHeroAspect) badge).onBadgeTick(event.player.world, event.player, badgeEffects, keyStates.getKeyState(Key.ASPECT), keyStates.getKeyTime(Key.ASPECT));
					else if(badge instanceof BadgeHeroAspectUtil)
						isActive = ((BadgeHeroAspectUtil) badge).onBadgeTick(event.player.world, event.player, badgeEffects, keyStates.getKeyState(Key.UTIL), keyStates.getKeyTime(Key.UTIL));

				if (!isActive)
					badgeEffects.stopPowerParticles(badge.getClass());
			}

		keyStates.tickKeyStates();
	}
}
