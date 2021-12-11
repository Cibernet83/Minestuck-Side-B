package com.cibernet.minestuckgodtier.badges.heroAspectUtil;

import com.cibernet.minestuckgodtier.MinestuckGodTier;
import com.cibernet.minestuckgodtier.badges.Badge;
import com.cibernet.minestuckgodtier.badges.BadgeLevel;
import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.MSGTKeyHandler;
import com.cibernet.minestuckgodtier.items.MSGTItems;
import com.cibernet.minestuckgodtier.potions.MSGTPotions;
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

public abstract class BadgeHeroAspectUtil extends BadgeLevel
{
	public static final Collection<BadgeHeroAspectUtil> HERO_ASPECT_UTIL_BADGES = new ArrayList<>();

	public final EnumAspect heroAspect;

	protected final ItemStack[] requiredItems;

	public BadgeHeroAspectUtil(EnumAspect heroAspect, int requiredLevel, ItemStack... requiredItems)
	{
		super("util" + heroAspect.name().substring(0,1).toUpperCase() + heroAspect.name().substring(1).toLowerCase(), requiredLevel);
		this.heroAspect = heroAspect;
		HERO_ASPECT_UTIL_BADGES.add(this);

		this.requiredItems = requiredItems;
	}

	public BadgeHeroAspectUtil(EnumAspect heroAspect, ItemStack... requiredItems)
	{
		this(heroAspect, 6, requiredItems);
	}


	@Override
	public boolean canUnlock(World world, EntityPlayer player)
	{
		for(ItemStack stack : requiredItems)
			if(!Badge.findItem(player, stack, false))
				return false;

		if(Badge.findItem(player, new ItemStack(MSGTItems.heroStoneShards.get(heroAspect), 128), false))
		{
			for(ItemStack stack : requiredItems)
				Badge.findItem(player, stack, true);
			Badge.findItem(player, new ItemStack(MSGTItems.heroStoneShards.get(heroAspect), 128), true);
			return true;
		}

		return false;
	}

	@Override
	public boolean canUse(World world, EntityPlayer player)
	{
		return !(player.isPotionActive(MSGTPotions.GOD_TIER_LOCK) && player.getActivePotionEffect(MSGTPotions.GOD_TIER_LOCK).getAmplifier() >= 1);
	}

	@Override
	public boolean canAppearOnList(World world, EntityPlayer player)
	{
		if(!super.canAppearOnList(world, player))
			return false;

		EnumAspect playerAspect;

		if(world.isRemote)
			playerAspect = MinestuckPlayerData.title.getHeroAspect();
		else
			playerAspect = MinestuckPlayerData.getData(player).title.getHeroAspect();

		return heroAspect.equals(playerAspect);
	}

	@Override
	public String getDisplayTooltip()
	{
		String keyName = "GOD TIER UTIL";
		if(Side.CLIENT.equals(FMLCommonHandler.instance().getSide()))
			keyName = MSGTKeyHandler.keyBindings[GodKeyStates.Key.UTIL.ordinal()].getDisplayName();

		return I18n.format(getUnlocalizedName()+".tooltip", keyName);
	}

	public Badge setRegistryName()
	{

		return setRegistryName(MinestuckGodTier.MODID, "util_" + heroAspect.name().toLowerCase() + "_badge");
	}

	public abstract boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time);
}
