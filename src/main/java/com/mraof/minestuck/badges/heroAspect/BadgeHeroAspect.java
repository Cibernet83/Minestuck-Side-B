package com.mraof.minestuck.badges.heroAspect;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.badges.Badge;
import com.mraof.minestuck.badges.BadgeLevel;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.MSGTKeyHandler;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import com.mraof.minestuck.potions.MinestuckPotions;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumRole;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BadgeHeroAspect extends BadgeLevel
{
	public static final Collection<BadgeHeroAspect> HERO_ASPECT_BADGES = new ArrayList<>();

	protected final EnumAspect heroAspect;
	protected final EnumRole heroRole;
	protected final EnumAspect[] auxAxpects; // i refuse to believe this is a typo
	protected final int requiredXp;

	public BadgeHeroAspect(EnumAspect heroAspect, EnumRole heroRole, int requiredLevel, int requiredXp, EnumAspect... auxAspects)
	{
		super(heroRole.name().toLowerCase() + heroAspect.name().substring(0,1).toUpperCase() + heroAspect.name().substring(1).toLowerCase(), requiredLevel);
		this.heroAspect = heroAspect;
		this.heroRole = heroRole;
		this.auxAxpects = auxAspects;
		this.requiredXp = requiredXp;
		HERO_ASPECT_BADGES.add(this);
	}

	public BadgeHeroAspect(EnumAspect heroAspect, EnumRole heroRole, EnumAspect... auxAspects) {
		this(heroAspect, heroRole, 6, 40, auxAspects);
	}

	public abstract boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time);

	@Override
	public boolean canUse(World world, EntityPlayer player)
	{
		return !(player.isPotionActive(MinestuckPotions.GOD_TIER_LOCK) && player.getActivePotionEffect(MinestuckPotions.GOD_TIER_LOCK).getAmplifier() >= 1);
	}

	@Override
	public String getDisplayTooltip()
	{
		String keyName = "GOD TIER ACTION";
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT)
			keyName = MSGTKeyHandler.keyBindings[GodKeyStates.Key.ASPECT.ordinal()].getDisplayName();

		return I18n.format(getUnlocalizedName() + ".tooltip", keyName);
	}

	@Override
	public String getUnlockRequirements()
	{
		if(MinestuckConfig.multiAspectUnlocks && auxAxpects.length > 0)
		{
			int auxShardAmount = 32/Math.max(1, auxAxpects.length);

			String entries = "";
			for(int i = 0; i < auxAxpects.length-1; i++)
				entries += I18n.format("badge.aspect.unlock.aux.entry", auxShardAmount, auxAxpects[i].getDisplayName());
			return I18n.format("badge.aspect.unlock.aux", heroAspect.getDisplayName(), entries, auxShardAmount, auxAxpects[auxAxpects.length-1].getDisplayName());
		}
		else return I18n.format("badge.aspect.unlock", heroAspect.getDisplayName());
	}

	@Override
	public boolean canUnlock(World world, EntityPlayer player)
	{
		if(player.experienceLevel >= requiredXp && findShards(player, false))
		{
			findShards(player, true);
			player.experienceLevel -= requiredXp;
			MinestuckChannelHandler.sendToPlayer(MinestuckPacket.makePacket(MinestuckPacket.Type.ADD_PLAYER_XP, -requiredXp), player);
			return true;
		}
		return false;
	}

	protected boolean findShards(EntityPlayer player, boolean decr)
	{
		int auxShardAmount = 32/Math.max(1, auxAxpects.length);

		if(!Badge.findItem(player, new ItemStack(MinestuckItems.heroStoneShards.get(heroAspect), auxAxpects.length > 0 ? 64 : 96), decr))
			return false;

		if(MinestuckConfig.multiAspectUnlocks)
			for(EnumAspect aspect : auxAxpects)
				if(!Badge.findItem(player, new ItemStack(MinestuckItems.heroStoneShards.get(aspect), auxShardAmount), decr))
					return false;

		return true;
	}

	@Override
	public boolean canAppearOnList(World world, EntityPlayer player)
	{
		if(!super.canAppearOnList(world, player))
			return false;

		EnumAspect playerAspect;
		EnumRole playerRole;

		if(world.isRemote)
		{
			playerAspect = MinestuckPlayerData.clientData.title.getHeroAspect();
			playerRole = EnumRole.getRoleFromClass(MinestuckPlayerData.clientData.title.getHeroClass());
		}
		else
		{
			playerAspect = MinestuckPlayerData.getData(player).title.getHeroAspect();
			playerRole = EnumRole.getRoleFromClass(MinestuckPlayerData.getData(player).title.getHeroClass());
		}

		return heroAspect.equals(playerAspect) && heroRole.equals(playerRole);
	}

	public Badge setRegistryName()
	{
		return setRegistryName(Minestuck.MODID, heroRole.name().toLowerCase() + "_" + heroAspect.name().toLowerCase() + "_badge");
	}
}
