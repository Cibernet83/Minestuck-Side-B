package com.mraof.minestuck.badges.heroAspectUtil;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.badges.Badge;
import com.mraof.minestuck.badges.BadgeLevel;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.MSGTKeyHandler;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.potions.MinestuckPotions;
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

	public BadgeHeroAspectUtil(EnumAspect heroAspect, ItemStack... requiredItems)
	{
		this(heroAspect, 6, requiredItems);
	}

	public BadgeHeroAspectUtil(EnumAspect heroAspect, int requiredLevel, ItemStack... requiredItems)
	{
		super("util" + heroAspect.name().substring(0, 1).toUpperCase() + heroAspect.name().substring(1).toLowerCase(), requiredLevel);
		this.heroAspect = heroAspect;
		HERO_ASPECT_UTIL_BADGES.add(this);

		this.requiredItems = requiredItems;
	}

	@Override
	public String getDisplayTooltip()
	{
		String keyName = "GOD TIER UTIL";
		if (Side.CLIENT.equals(FMLCommonHandler.instance().getSide()))
			keyName = MSGTKeyHandler.keyBindings[GodKeyStates.Key.UTIL.ordinal()].getDisplayName();

		return I18n.format(getUnlocalizedName() + ".tooltip", keyName);
	}

	@Override
	public boolean canAppearOnList(World world, EntityPlayer player)
	{
		if (!super.canAppearOnList(world, player))
			return false;

		EnumAspect playerAspect;

		if (world.isRemote)
			playerAspect = MinestuckPlayerData.clientData.title.getHeroAspect();
		else
			playerAspect = MinestuckPlayerData.getData(player).title.getHeroAspect();

		return heroAspect.equals(playerAspect);
	}

	@Override
	public boolean canUnlock(World world, EntityPlayer player)
	{
		for (ItemStack stack : requiredItems)
			if (!Badge.findItem(player, stack, false))
				return false;

		if (Badge.findItem(player, new ItemStack(MinestuckItems.heroStoneShards.get(heroAspect), 128), false))
		{
			for (ItemStack stack : requiredItems)
				Badge.findItem(player, stack, true);
			Badge.findItem(player, new ItemStack(MinestuckItems.heroStoneShards.get(heroAspect), 128), true);
			return true;
		}

		return false;
	}

	@Override
	public boolean canUse(World world, EntityPlayer player)
	{
		return !(player.isPotionActive(MinestuckPotions.GOD_TIER_LOCK) && player.getActivePotionEffect(MinestuckPotions.GOD_TIER_LOCK).getAmplifier() >= 1);
	}

	public Badge setRegistryName()
	{

		return setRegistryName(Minestuck.MODID, "util_" + heroAspect.name().toLowerCase() + "_badge");
	}

	public abstract boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time);
}
