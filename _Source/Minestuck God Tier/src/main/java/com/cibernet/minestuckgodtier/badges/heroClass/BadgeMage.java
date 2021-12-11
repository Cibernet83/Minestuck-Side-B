package com.cibernet.minestuckgodtier.badges.heroClass;

import com.cibernet.minestuckgodtier.badges.MSGTBadges;
import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.network.MSGTChannelHandler;
import com.cibernet.minestuckgodtier.network.MSGTPacket;
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

		int karma = player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).getTotalKarma();

		int alignmentColor = 0x00FF15;
		int minKarma = player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).isBadgeActive(MSGTBadges.KARMA) ? 40 : 20;
		if(karma >= minKarma)
			alignmentColor = 0xFFD800;
		else if(karma <= -minKarma)
			alignmentColor = 0xB200FF;

		if (time > 50)
			MSGTChannelHandler.sendToPlayer(MSGTPacket.makePacket(MSGTPacket.Type.SEND_PARTICLE, MSGTParticles.ParticleType.AURA, alignmentColor, 5, player), player);
		else badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumClass.MAGE, 1);

		if (time > 100)
			player.sendStatusMessage(new TextComponentTranslation("status.alignmentPrediction." + (karma >= minKarma ? "heroic" : karma <= -minKarma ? "just" : "neutral") + "Self")
					                         .setStyle(new Style().setColor(karma >= minKarma ? TextFormatting.GOLD : karma <= -minKarma ? TextFormatting.DARK_PURPLE : TextFormatting.GREEN)), true);

		return true;
	}
}
