package com.cibernet.minestuckgodtier.badges.heroAspect;

import com.cibernet.minestuckgodtier.MSGTConfig;
import com.cibernet.minestuckgodtier.MinestuckGodTier;
import com.cibernet.minestuckgodtier.badges.Badge;
import com.cibernet.minestuckgodtier.badges.BadgeLevel;
import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.MSGTKeyHandler;
import com.cibernet.minestuckgodtier.items.MSGTItems;
import com.cibernet.minestuckgodtier.network.MSGTChannelHandler;
import com.cibernet.minestuckgodtier.network.MSGTPacket;
import com.cibernet.minestuckgodtier.potions.MSGTPotions;
import com.cibernet.minestuckgodtier.util.EnumRole;
import com.mraof.minestuck.util.EnumAspect;
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
		return !(player.isPotionActive(MSGTPotions.GOD_TIER_LOCK) && player.getActivePotionEffect(MSGTPotions.GOD_TIER_LOCK).getAmplifier() >= 1);
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
		if(MSGTConfig.multiAspectUnlocks && auxAxpects.length > 0)
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
			MSGTChannelHandler.sendToPlayer(MSGTPacket.makePacket(MSGTPacket.Type.ADD_PLAYER_XP, -requiredXp), player);
			return true;
		}
		return false;
	}

	protected boolean findShards(EntityPlayer player, boolean decr)
	{
		int auxShardAmount = 32/Math.max(1, auxAxpects.length);

		if(!Badge.findItem(player, new ItemStack(MSGTItems.heroStoneShards.get(heroAspect), auxAxpects.length > 0 ? 64 : 96), decr))
			return false;

		if(MSGTConfig.multiAspectUnlocks)
			for(EnumAspect aspect : auxAxpects)
				if(!Badge.findItem(player, new ItemStack(MSGTItems.heroStoneShards.get(aspect), auxShardAmount), decr))
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
			playerAspect = MinestuckPlayerData.title.getHeroAspect();
			playerRole = EnumRole.getRoleFromClass(MinestuckPlayerData.title.getHeroClass());
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
		return setRegistryName(MinestuckGodTier.MODID, heroRole.name().toLowerCase() + "_" + heroAspect.name().toLowerCase() + "_badge");
	}
}
