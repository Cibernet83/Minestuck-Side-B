package com.mraof.minestuck.badges.heroClass;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.badges.Badge;
import com.mraof.minestuck.badges.MinestuckBadges;
import com.mraof.minestuck.block.IGodTierBlock;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.api.IGodTierData;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.capabilities.caps.GodTierData;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.network.skaianet.SburbConnection;
import com.mraof.minestuck.network.skaianet.SkaianetHandler;
import com.mraof.minestuck.potions.MinestuckPotions;
import com.mraof.minestuck.util.EnumClass;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class BadgeOverlord extends BadgeHeroClass
{
	public static final int REQ_SKILL_LEVEL = 80;
	
	public BadgeOverlord()
	{
		super(EnumClass.LORD, REQ_SKILL_LEVEL, 0);
		setUnlocalizedName("overlord");
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		return false;
	}

	@Override
	public boolean canUnlock(World world, EntityPlayer player) {
		return false;
	}

	@Override
	public boolean isReadable(World world, EntityPlayer player) {
		return false;
	}

	@Override
	public boolean canAppearOnList(World world, EntityPlayer player) {
		return false;
	}

	@Override
	public Badge setRegistryName()
	{
		return setRegistryName(Minestuck.MODID,  "overlord_badge");
	}

	@Override
	public String getReadRequirements() {
		return  I18n.format("badge.secret.read");
	}

	@Override
	public boolean canUse(World world, EntityPlayer player) {
		return !(player.isPotionActive(MinestuckPotions.GOD_TIER_LOCK) && player.getActivePotionEffect(MinestuckPotions.GOD_TIER_LOCK).getAmplifier() >= 2);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingDeath(LivingDeathEvent event)
	{
		if(!event.getEntity().world.isRemote && event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			IdentifierHandler.PlayerIdentifier identifier = IdentifierHandler.encode(player);
			SburbConnection c = SkaianetHandler.getMainConnection(identifier, true);
			MinestuckPlayerData.PlayerData data = MinestuckPlayerData.getData(identifier);

			if(c != null && c.enteredGame() && player.world.getBlockState(new BlockPos(player.posX, player.posY - 0.1, player.posZ)).getBlock() instanceof IGodTierBlock)
			{
				IGodTierBlock block = (IGodTierBlock) player.world.getBlockState(new BlockPos(player.posX, player.posY - 0.1, player.posZ)).getBlock();

				if(data.title.getHeroClass() == EnumClass.LORD && block.canGodTier() && ((block.getAspect() == null || block.getAspect().equals(data.title.getHeroAspect()))))
				{
					IGodTierData gtData = player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null);
					if(gtData.getSkillLevel(GodTierData.SkillType.GENERAL) < REQ_SKILL_LEVEL)
						player.sendStatusMessage(new TextComponentTranslation("status.overlordSkillLevel", REQ_SKILL_LEVEL).setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)), false);
					else if(!(event.getSource().getTrueSource() instanceof EntityPlayer))
						player.sendStatusMessage(new TextComponentTranslation("status.overlordPvpDeath").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)), false);
					else
					{
						player.motionY = 0.8;
						gtData.setMaxBadges(gtData.getMaxBadges() + 2);
						gtData.addBadge(MinestuckBadges.BADGE_OVERLORD, true);
						player.world.getMinecraftServer().getPlayerList().sendMessage(new TextComponentTranslation("status.overlordAscend", player.getDisplayName()).setStyle(new Style().setColor(TextFormatting.DARK_PURPLE)));
						player.addPotionEffect(new PotionEffect(MinestuckPotions.GOD_TIER_COMEBACK, 200, 3));

						player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.AURA, EnumClass.LORD, 25);
						player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_WITHER_SPAWN, player.getSoundCategory(), 1.0F, 1.0F);

						player.setHealth(10);
						event.setCanceled(true);
					}
				}
			}
		}
	}
}
