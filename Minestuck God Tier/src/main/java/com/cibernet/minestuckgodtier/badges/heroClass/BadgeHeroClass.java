package com.cibernet.minestuckgodtier.badges.heroClass;

import com.cibernet.minestuckgodtier.MinestuckGodTier;
import com.cibernet.minestuckgodtier.badges.Badge;
import com.cibernet.minestuckgodtier.badges.BadgeLevel;
import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.MSGTKeyHandler;
import com.cibernet.minestuckgodtier.network.MSGTChannelHandler;
import com.cibernet.minestuckgodtier.network.MSGTPacket;
import com.cibernet.minestuckgodtier.potions.MSGTPotions;
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
		return !(player.isPotionActive(MSGTPotions.GOD_TIER_LOCK) && player.getActivePotionEffect(MSGTPotions.GOD_TIER_LOCK).getAmplifier() >= 1);
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
			MSGTChannelHandler.sendToPlayer(MSGTPacket.makePacket(MSGTPacket.Type.ADD_PLAYER_XP, -requiredXp), player);
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
			playerClass = MinestuckPlayerData.title.getHeroClass();
		else playerClass = MinestuckPlayerData.getData(player).title.getHeroClass();

		return heroClass.equals(playerClass);
	}

	public Badge setRegistryName()
	{
		return setRegistryName(MinestuckGodTier.MODID, heroClass.name().toLowerCase() + "_badge");
	}
}
