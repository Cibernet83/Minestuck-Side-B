package com.mraof.minestuck.badges.heroClass;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.badges.Badge;
import com.mraof.minestuck.badges.BadgeLevel;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.MSGTKeyHandler;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.potions.MinestuckPotions;
import com.mraof.minestuck.util.EnumClass;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BadgeHeroClass extends BadgeLevel
{
	public static final Collection<BadgeHeroClass> HERO_CLASS_BADGES = new ArrayList<>();

	protected final int requiredXp;
	protected final EnumClass heroClass;

	public BadgeHeroClass(EnumClass heroClass, int requiredLevel, int requiredXp) {
		super(heroClass.name().toLowerCase(), requiredLevel);
		this.requiredXp = requiredXp;
		this.heroClass = heroClass;
		HERO_CLASS_BADGES.add(this);
	}

	public BadgeHeroClass(EnumClass heroClass) {
		this(heroClass, 5, 50);
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
		String keyName = "CLASS ACTION";
		if(Side.CLIENT.equals(FMLCommonHandler.instance().getSide()))
			keyName = MSGTKeyHandler.keyBindings[GodKeyStates.Key.CLASS.ordinal()].getDisplayName();

		return I18n.format(getUnlocalizedName()+".tooltip", keyName);
	}

	@Override
	public String getUnlockRequirements() {
		return I18n.format("badge.class.unlock", requiredXp);
	}

	@Override
	public boolean canUnlock(World world, EntityPlayer player)
	{
		if(player.experienceLevel >= requiredXp)
		{
			player.experienceLevel -= requiredXp;
			MinestuckNetwork.sendTo(MinestuckMessage.makePacket(MinestuckMessage.Type.ADD_PLAYER_XP, -requiredXp), player);
			return true;
		}
		return false;
	}

	@Override
	public boolean canAppearOnList(World world, EntityPlayer player)
	{
		if(!super.canAppearOnList(world, player))
			return false;

		EnumClass playerClass;

		if(world.isRemote)
			playerClass = MinestuckPlayerData.clientData.title.getHeroClass();
		else playerClass = MinestuckPlayerData.getData(player).title.getHeroClass();

		return heroClass.equals(playerClass);
	}

	public Badge setRegistryName()
	{
		return setRegistryName(Minestuck.MODID, heroClass.name().toLowerCase() + "_badge");
	}
}
