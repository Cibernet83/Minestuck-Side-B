package com.mraof.minestuck.badges.heroClass;

import com.mraof.minestuck.badges.MinestuckBadges;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MSGTParticles;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import com.mraof.minestuck.util.EnumClass;
import com.mraof.minestuck.util.MSGTUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BadgeSeer extends BadgeHeroClass
{
	public BadgeSeer()
	{
		super(EnumClass.SEER);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(!state.equals(GodKeyStates.KeyState.HELD))
			return false;

		EntityLivingBase target = MSGTUtils.getTargetEntity(player);

		if(!(target instanceof EntityPlayer))
			return false;

		if(!player.isCreative())
		{
			if(player.getFoodStats().getFoodLevel() <= 0)
			{
				player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
				return false;
			}
			if((time % 30) == 0)
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel()-1);
		}


		int karma = target.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).getTotalKarma();
		int alignmentColor = 0x00FF15;
		int minKarma = target.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).isBadgeActive(MinestuckBadges.KARMA) ? 40 : 20;
		if(karma >= minKarma)
			alignmentColor = 0xFFD800;
		else if(karma <= -minKarma)
			alignmentColor = 0xB200FF;

		MinestuckChannelHandler.sendToPlayer(MinestuckPacket.makePacket(MinestuckPacket.Type.SEND_PARTICLE, MSGTParticles.ParticleType.AURA, time > 15 ? alignmentColor : 0xD670FF, 5, target), player);
		badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumClass.SEER, 1);

		if(time > 200)
			player.sendStatusMessage(new TextComponentTranslation("status.karma", ((EntityPlayer) target).getDisplayNameString(), karma)
					                         .setStyle(new Style().setColor(karma >= minKarma ? TextFormatting.GOLD : karma <= -minKarma ? TextFormatting.DARK_PURPLE : TextFormatting.GREEN)), true);

		return true;
	}
}
