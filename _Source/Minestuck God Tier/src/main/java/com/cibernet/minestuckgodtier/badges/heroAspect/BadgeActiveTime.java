package com.cibernet.minestuckgodtier.badges.heroAspect;

import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.util.EnumRole;
import com.cibernet.minestuckgodtier.util.SoulData;
import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Queue;

public class BadgeActiveTime extends BadgeHeroAspect
{
	private static final int ENERGY_USE = 5;
	private static final int RECALL_TICKS = 5 * 20;

	public BadgeActiveTime()
	{
		super(EnumAspect.TIME, EnumRole.ACTIVE, EnumAspect.BREATH);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		Queue<SoulData> soulData = badgeEffects.getTimeSoulData();

		soulData.add(new SoulData(player));
		while (soulData.size() > RECALL_TICKS)
			soulData.remove();

		if(state != GodKeyStates.KeyState.HELD || time > 20)
			return false;

		if (time < 20)
		{
			badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.TIME, 2);
			return true;
		}

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < ENERGY_USE)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		badgeEffects.oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumAspect.TIME, 4);

		soulData.element().apply(player);
		soulData.clear();

		badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.TIME,4);

		if (!player.isCreative())
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - ENERGY_USE);

		return true;
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if(event.getEntity().hasCapability(MSGTCapabilities.BADGE_EFFECTS, null))
			event.getEntity().getCapability(MSGTCapabilities.BADGE_EFFECTS, null).getTimeSoulData().clear();
	}
}
