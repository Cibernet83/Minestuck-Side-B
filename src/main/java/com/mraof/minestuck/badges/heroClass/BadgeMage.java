package com.mraof.minestuck.badges.heroClass;

import com.mraof.minestuck.badges.MinestuckBadges;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageSendParticle;
import com.mraof.minestuck.util.EnumClass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BadgeMage extends BadgeHeroClass
{
	public BadgeMage()
	{
		super(EnumClass.MAGE);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(state == GodKeyStates.KeyState.NONE)
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

		int karma = player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).getTotalKarma();

		int alignmentColor = 0x00FF15;
		int minKarma = player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).isBadgeActive(MinestuckBadges.KARMA) ? 40 : 20;
		if(karma >= minKarma)
			alignmentColor = 0xFFD800;
		else if(karma <= -minKarma)
			alignmentColor = 0xB200FF;

		if (time > 50)
			MinestuckNetwork.sendTo(new MessageSendParticle(MinestuckParticles.ParticleType.AURA, player, alignmentColor, 5), player);
		else badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumClass.MAGE, 1);

		if (time > 100)
			player.sendStatusMessage(new TextComponentTranslation("status.alignmentPrediction." + (karma >= minKarma ? "heroic" : karma <= -minKarma ? "just" : "neutral") + "Self")
					                         .setStyle(new Style().setColor(karma >= minKarma ? TextFormatting.GOLD : karma <= -minKarma ? TextFormatting.DARK_PURPLE : TextFormatting.GREEN)), true);

		return true;
	}
}
